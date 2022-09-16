package com.itsfrz.authentication.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.itsfrz.authentication.data.entities.ContactModel
import com.itsfrz.authentication.data.repository.ContactModelRepository
import com.itsfrz.authentication.data.repository.ContactProviderRepository
import com.itsfrz.authentication.model.database.room.AppDatabase
import com.itsfrz.authentication.ui.viewmodel.ContactImportViewModel
import com.itsfrz.authentication.ui.viewmodel.factory.ContactImportViewModelFactory
import com.itsfrz.authentication.ui.views.compose.components.*
import com.itsfrz.authentication.ui.views.compose.ui.theme.Blue100
import com.itsfrz.authentication.ui.views.compose.ui.theme.DangerRed100
import com.itsfrz.authentication.ui.views.compose.utils.Loader
import kotlin.properties.Delegates


class ContactImportFragment : Fragment() {


    private lateinit var contactImportViewModel: ContactImportViewModel
    private lateinit var navController: NavController
    private lateinit var username : String
    private lateinit var loggedInDate : String
    private var isLoggedIn : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = findNavController()
        val contactProviderRepository = ContactProviderRepository(requireContext())
        val contactDao = AppDatabase.getDatabase(requireContext()).contactDao()
        val contactModelRepository = ContactModelRepository(contactDao)
        val contactImportViewModelFactory = ContactImportViewModelFactory(contactProviderRepository,contactModelRepository)
        contactImportViewModel = ViewModelProvider(
            this,
            contactImportViewModelFactory
        ).get(ContactImportViewModel::class.java)

        username = arguments?.getString("username") ?: ""
        isLoggedIn = arguments?.getBoolean("isLoggedIn") ?: false
        loggedInDate = arguments?.getString("loggedInDate") ?: ""
    }

    @OptIn(ExperimentalMaterialApi::class)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                val searchBar = contactImportViewModel.showSearchBar.value
                val searchQuery = contactImportViewModel.searchQuery.value
                var contactList =
                    if (searchQuery.isEmpty()) contactImportViewModel.contactList  else contactImportViewModel.filteredList.value
                val operationQueue = contactImportViewModel.operationQueue
                val rotationState = contactImportViewModel.rotationState
                val showEmptyListMessage = contactImportViewModel.showEmptyListMessage.value
                val selectedContacts = contactImportViewModel.selectedContacts.reversed()
                val dialogBoxState = contactImportViewModel.alertDialogState.value

                val rotateClockWise: Float by animateFloatAsState(
                    targetValue = if (rotationState.value) 360F else 0F,
                    animationSpec = tween(
                        durationMillis = 250,
                        easing = LinearOutSlowInEasing
                    )
                )
                Scaffold(topBar = {
                    NavBarLayout(
                        navController = navController,
                        title = "Import Contact",
                        icon = Icons.Default.ArrowBack,
                        iconClickEvent = {
                            navController.navigateUp()
                        },
                        isActionMenuPresent = true,
                        isImportMenuItem = false,
                        isSearchBarMenuItem = true,
                        toggleSearchBar = { toggle -> contactImportViewModel.toggleSearchBar(toggle)},
                        showSearchBar = searchBar,
                        isUserInfoMenuItem = false,
                        isLogoutMenuItem = false,
                        isDeleteSelectedMenuItem = contactImportViewModel.selectedContacts.isNotEmpty(),
                        isSelectAllMenuItem = true,
                        importClickEvent = {},
                        userInfoClickEvent = {},
                        deleteSelectedClickEvent = {
                            contactImportViewModel.toggleAlertDialog()
                        },
                        logoutClickEvent = {},
                        selectAllClickEvent = {
                              contactImportViewModel.selectAllContacts()
                        },
                        getSearchQuery = { query -> contactImportViewModel.searchRequest(query) },
                        searchQuery = searchQuery,
                        totalContactsFound = 0
                    )
                }) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (!contactImportViewModel.isProgress.value) {
                           Column(
                               modifier = Modifier
                                   .fillMaxSize()
                           ) {
                               ChipLayout(contacts = selectedContacts, removeContact = { removeIndex ->
                                   contactImportViewModel.removeContactSelection(removeIndex)
                               })
                               Divider(
                                   modifier = Modifier
                                       .fillMaxWidth()
                                       .padding(vertical = 2.dp),
                                   color = Blue100,
                                   thickness = 0.3.dp
                               )
                               LazyColumn(
                                   modifier = Modifier
                                       .fillMaxSize()
                                       .padding(top = 2.dp),
                                   horizontalAlignment = Alignment.CenterHorizontally,
                                   verticalArrangement = if (showEmptyListMessage) Arrangement.Center else Arrangement.Top
                               ) {
                                   if (showEmptyListMessage){
                                       item {
                                           Text(
                                               text = "Hmm, No Result Found :(",
                                               textAlign = TextAlign.Center
                                           )
                                       }
                                   }else{
                                       items(
                                           count = contactList.size,
                                           key = { index: Int -> contactList[index].hashCode() + index }
                                       ) { index ->
                                           val dismissState = rememberDismissState(
                                               confirmStateChange = {
                                                   if (it == DismissValue.DismissedToStart){
                                                       contactImportViewModel.deleteContact(index)
                                                   }
                                                   if (it == DismissValue.DismissedToEnd){
                                                       contactImportViewModel.updateContact(index)
                                                   }
                                                   true
                                               }
                                           )
                                           SwipeToDismiss(
                                               modifier = Modifier,
                                               state = dismissState,
                                               directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
                                               dismissThresholds = {FractionalThreshold(0.2F)},
                                               background = {
                                                   val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
                                                   val color by animateColorAsState(
                                                       targetValue = when (dismissState.targetValue) {
                                                           DismissValue.Default -> Color.LightGray
                                                           DismissValue.DismissedToStart -> DangerRed100
                                                           DismissValue.DismissedToEnd -> Blue100
                                                       }
                                                   )
                                                   val icon = when (direction) {
                                                       DismissDirection.EndToStart -> Icons.Default.Delete
                                                       DismissDirection.StartToEnd -> Icons.Default.Done
                                                   }
                                                   val scale by animateFloatAsState(targetValue = if (dismissState.targetValue == DismissValue.Default) 0.8F else 1.2F)

                                                   val alignment = when(direction){
                                                       DismissDirection.StartToEnd -> Alignment.CenterStart
                                                       DismissDirection.EndToStart -> Alignment.CenterEnd
                                                   }

                                                   Box(
                                                       modifier = Modifier
                                                           .fillMaxSize()
                                                           .background(color)
                                                           .padding(start = 12.dp, end = 12.dp),
                                                       contentAlignment = alignment
                                                   )
                                                   {
                                                       Row(
                                                           modifier = Modifier.wrapContentWidth(),
                                                           verticalAlignment = Alignment.CenterVertically
                                                       ) {
                                                           var operationText = if (direction == DismissDirection.EndToStart) " -- Delete" else " -- Update"
                                                           Icon(imageVector = icon, contentDescription = "Icon", modifier = Modifier.scale(scale), tint = Color.White)
                                                           Text(text = operationText, color = Color.White, fontSize = 12.sp)
                                                       }
                                                   }
                                               },
                                               dismissContent = {
                                                   val contact: ContactModel = contactList[index]
                                                   Box(
                                                       modifier = Modifier
                                                           .clickable {
                                                               contactImportViewModel.addContactSelection(contactList[index])
                                                           },
                                                       contentAlignment = Alignment.Center
                                                   ) {
                                                       ImportListItemRow(
                                                           contact = contact
                                                       )
                                                   }
                                               }
                                           )
                                           Divider(
                                               modifier = Modifier
                                                   .fillMaxWidth(.96F)
                                                   .padding(2.dp),
                                               color = Blue100,
                                               thickness = 0.3.dp
                                           )
                                       }
                                   }
                               }
                           }



                        } else {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Loader(loaderMessage = "Contact Syncing ...")
                            }
                        }
                        if (!contactImportViewModel.isProgress.value) {
                            FloatingActionButton(
                                onClick = {
                                    if (operationQueue.value){
                                        contactImportViewModel.importSelectedContacts()
                                        findNavController().navigateUp()
                                    }else{

                                    }
                                },
                                modifier = Modifier
                                    .rotate(rotateClockWise)
                                    .align(Alignment.BottomEnd)
                                    .padding(20.dp),
                                backgroundColor = Blue100
                            ) {
                                if (operationQueue.value) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Check Out Contacts",
                                        tint = Color.White
                                    )
                                } else Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Insert Contact",
                                    tint = Color.White
                                )
                            }
                        }

                        if (dialogBoxState){
                            AlertBox(
                                title = "Delete",
                                content = "Are you sure, You want to delete selected contact ?",
                                buttonOneText = "Just Kidding",
                                buttonTwoText = "Yup",
                                buttonClickResponse = { response ->
                                    if(response)
                                    {
                                        contactImportViewModel.deleteContactsSelection()
                                    }
                                    contactImportViewModel.toggleAlertDialog()
                                },
                                onCloseEvent = {
                                    contactImportViewModel.toggleAlertDialog()
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        contactImportViewModel.fetchContacts()
    }

}