package com.itsfrz.authentication.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.itsfrz.authentication.data.entities.ContactModel
import com.itsfrz.authentication.data.repository.ContactProviderRepository
import com.itsfrz.authentication.ui.viewmodel.ContactImportViewModel
import com.itsfrz.authentication.ui.viewmodelfactory.ContactImportViewModelFactory
import com.itsfrz.authentication.ui.views.compose.components.ImportListItemRow
import com.itsfrz.authentication.ui.views.compose.components.NavBarLayout
import com.itsfrz.authentication.ui.views.compose.ui.theme.Blue100
import com.itsfrz.authentication.ui.views.compose.utils.Loader


class ContactImportFragment : Fragment() {


    private lateinit var contactImportViewModel: ContactImportViewModel
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = findNavController()
        val contactProviderRepository = ContactProviderRepository(requireContext())
        val contactImportViewModelFactory = ContactImportViewModelFactory(contactProviderRepository)
        contactImportViewModel = ViewModelProvider(
            this,
            contactImportViewModelFactory
        ).get(ContactImportViewModel::class.java)
    }

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
                    if (searchQuery.isEmpty()) contactImportViewModel.contactList else contactImportViewModel.filteredList.value
                val operationQueue = contactImportViewModel.operationQueue
                val rotationState = contactImportViewModel.rotationState


                val rotateClockWise : Float by animateFloatAsState(
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
                        toggleSearchBar = { toggle -> contactImportViewModel.toggleSearchBar(toggle) },
                        showSearchBar = searchBar,
                        isUserInfoMenuItem = false,
                        isLogoutMenuItem = false,
                        isDeleteAllMenuItem = false,
                        isSelectAllMenuItem = true,
                        importClickEvent = {},
                        userInfoClickEvent = {},
                        deleteAllClickEvent = {},
                        logoutClickEvent = {},
                        selectAllClickEvent = {
                            contactImportViewModel.addAllSelectedContact()
                        },
                        getSearchQuery = { query -> contactImportViewModel.searchRequest(query) },
                        searchQuery = searchQuery,
                        totalContactsFound = 0
                    )
                }) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        if (!contactImportViewModel.isProgress.value) {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top
                            ) {
                                items(
                                    contactList.size,

                                ) { index ->
                                    val contact: ContactModel = contactList.get(index)
                                    Box(
                                        modifier = Modifier
                                            .clickable {
                                                contactImportViewModel.addSelectedContact(index)
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        ImportListItemRow(
                                            contact = contact,
                                            isSelected = contactImportViewModel.checkIsPresent(index)
                                        )
                                    }
                                    Divider(
                                        modifier = Modifier
                                            .fillMaxWidth(.96F)
                                            .padding(2.dp),
                                        color = Blue100,
                                        thickness = 0.3.dp
                                    )
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
                        if(!contactImportViewModel.isProgress.value){
                            FloatingActionButton(
                                onClick = {

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