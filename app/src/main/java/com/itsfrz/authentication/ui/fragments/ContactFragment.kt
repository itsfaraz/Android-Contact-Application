package com.itsfrz.authentication.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.itsfrz.authentication.R
import com.itsfrz.authentication.data.entities.UserPreferences
import com.itsfrz.authentication.data.repository.ContactModelRepository
import com.itsfrz.authentication.data.repository.UserPreferenceRepository
import com.itsfrz.authentication.model.database.room.AppDatabase
import com.itsfrz.authentication.ui.compose.components.ImportListItemRow
import com.itsfrz.authentication.ui.compose.components.NavBarLayout
import com.itsfrz.authentication.ui.compose.enums.ContactOperation
import com.itsfrz.authentication.ui.compose.ui.theme.Blue100
import com.itsfrz.authentication.ui.compose.ui.theme.DangerRed100
import com.itsfrz.authentication.ui.utils.Helper
import com.itsfrz.authentication.ui.viewmodel.ContactViewModel
import com.itsfrz.authentication.ui.viewmodel.factory.ContactViewModelFactory

class ContactFragment : Fragment() {


    private val TAG: String = "CONTACT_FRAGMENT_TAG"
    private lateinit var viewModel: ContactViewModel
    private var userPreferences: UserPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userPreferenceRepository = UserPreferenceRepository(requireContext())
        val contactDao = AppDatabase.getDatabase(requireContext()).contactDao()
        val contactModelRepository = ContactModelRepository(contactDao)
        val contactViewModelFactory =
            ContactViewModelFactory(userPreferenceRepository, contactModelRepository)
        viewModel =
            ViewModelProvider(this, contactViewModelFactory)[ContactViewModel::class.java]

        userPreferences = arguments?.getParcelable(Helper.USER_PREF)
        userPreferences?.let {
            viewModel.setupPreferences(it)
        }
        viewModel.fetchContacts()
        Log.d(TAG, "onCreate: Contact Fragment :: ${userPreferences.toString()}")
        println("Contact Fragment :: ${userPreferences.toString()}")
    }

    @OptIn(ExperimentalMaterialApi::class)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                val searchBar = viewModel.showSearchBar.value
                val searchQuery = viewModel.searchQuery.value
                val contactList =  if (searchQuery.isEmpty()) viewModel.contactList  else viewModel.filteredList.value

                Scaffold(
                    topBar = {
                        NavBarLayout(
                            findNavController(),
                            title = "Home",
                            isActionMenuPresent = true,
                            isImportMenuItem = true,
                            isDeleteSelectedMenuItem = true,
                            isLogoutMenuItem = true,
                            isSearchBarMenuItem = true,
                            showSearchBar = searchBar,
                            toggleSearchBar = {toggle -> viewModel.toggleSearchBar(toggle)},
                            isUserInfoMenuItem = true,
                            isSelectAllMenuItem = false,
                            iconClickEvent = {},
                            importClickEvent = {
                                findNavController().navigate(
                                    resId = R.id.contactImportFragment,
                                    args = bundleOf(
                                        Helper.USER_PREF to userPreferences
                                    ),
                                    navOptions = Helper.navOptions
                                )
                            },
                            userInfoClickEvent = {},
                            deleteSelectedClickEvent = {},
                            logoutClickEvent = {
                                findNavController().navigate(
                                    resId = R.id.authenticationFragment,
                                    args = null,
                                    navOptions = Helper.navOptionsWithPopStack(R.id.contactFragment, true)
                                )
                                viewModel.logout()
                            },
                            selectAllClickEvent = {},
                            getSearchQuery = {query -> viewModel.searchRequest(query) },
                            searchQuery = searchQuery,
                            totalContactsFound = contactList.size

                        )
                    },
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            LazyColumn(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top
                            ) {
                                items(
                                    count = contactList.size,
                                    key = { index: Int -> contactList[index].hashCode() + index }
                                ) { index ->
                                    val contact = contactList[index]

                                    val dismissState = rememberDismissState(
                                        confirmStateChange = {
                                            when (it) {
                                                DismissValue.DismissedToStart -> {
                                                    viewModel.removeContact(index)
                                                }
                                                DismissValue.DismissedToEnd -> {
                                                    findNavController().navigate(
                                                        resId = R.id.addContactFragment,
                                                        args = bundleOf(
                                                            "contact" to contact,
                                                            "operation" to ContactOperation.CONTACT_UPDATE.name,
                                                            Helper.USER_PREF to userPreferences
                                                        ),
                                                        navOptions = Helper.navOptions
                                                    )
                                                }
                                            }
                                            true
                                        }
                                    )
                                    SwipeToDismiss(
                                        modifier = Modifier,
                                        state = dismissState,
                                        dismissThresholds = { FractionalThreshold(0.3F) },
                                        directions = setOf(
                                            DismissDirection.StartToEnd,
                                            DismissDirection.EndToStart
                                        ),
                                        background = {
                                            val direction =
                                                dismissState.dismissDirection
                                                    ?: return@SwipeToDismiss
                                            val color by animateColorAsState(
                                                targetValue = when (dismissState.targetValue) {
                                                    DismissValue.Default -> Color.Transparent
                                                    DismissValue.DismissedToStart -> DangerRed100
                                                    DismissValue.DismissedToEnd -> Blue100
                                                }
                                            )
                                            val icon = when (direction) {
                                                DismissDirection.EndToStart -> Icons.Default.Delete
                                                DismissDirection.StartToEnd -> Icons.Default.Edit
                                            }
                                            val scale by animateFloatAsState(targetValue = if (dismissState.targetValue == DismissValue.Default) 0.8F else 1.2F)

                                            val alignment = when (direction) {
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
                                                    var operationText =
                                                        if (direction == DismissDirection.EndToStart) " -- Delete" else " -- Update"
                                                    Icon(
                                                        imageVector = icon,
                                                        contentDescription = "Icon",
                                                        modifier = Modifier.scale(scale),
                                                        tint = Color.White
                                                    )
                                                    Text(
                                                        text = operationText,
                                                        color = Color.White,
                                                        fontSize = 12.sp
                                                    )
                                                }
                                            }
                                        },
                                        dismissContent = {
                                            ImportListItemRow(contact = contact)
                                        }
                                    )
                                    Divider(
                                        modifier = Modifier
                                            .fillMaxWidth(.96F)
                                            .padding(2.dp),
                                        color = Blue100,
                                        thickness = .5.dp
                                    )
                                }


                            }
                        }
                        FloatingActionButton(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(20.dp),
                            backgroundColor = Blue100,
                            onClick = {
                                findNavController().navigate(
                                    resId = R.id.addContactFragment,
                                    args = bundleOf(
                                        "operation" to ContactOperation.CONTACT_ADD.name,
                                        Helper.USER_PREF to userPreferences
                                    ),
                                    navOptions = Helper.navOptions
                                )
                            }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Check Out Contacts",
                                tint = Color.White
                            )
                        }
                    }
                }
                BackHandler(true) {
                    requireActivity().finish()
                }
            }
        }

    }


    override fun onResume() {
        super.onResume()
        viewModel.reloadContactList();
    }

}