package com.itsfrz.authentication.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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


class ContactImportFragment : Fragment(){




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
                var contactList = if(searchQuery.isEmpty()) contactImportViewModel.contactList.value else contactImportViewModel.filteredList.value
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
                        isDeleteAllMenuItem = false,
                        isSelectAllMenuItem = false,
                        importClickEvent = {},
                        userInfoClickEvent = {},
                        deleteAllClickEvent = {},
                        logoutClickEvent = {},
                        selectAllClickEvent = {},
                        getSearchQuery = { query -> contactImportViewModel.searchRequest(query) },
                        searchQuery = searchQuery,
                        totalContactsFound = 0
                    )
                }) {

                    ImportContact(contactList = contactList)

                }

            }
        }
    }

    @Composable
    fun ImportContact(
        contactList: List<ContactModel>,
    ) {
        if (!contactImportViewModel.isProgress.value) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                items(contactList.size) { index ->
                    val contact: ContactModel = contactList.get(index)
                    Column(
                        modifier = Modifier.clickable {
                        }
                    ) {
                        ImportListItemRow(
                            modifier = Modifier,
                            contact,
                            false
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
    }


    override fun onResume() {
        super.onResume()
        contactImportViewModel.fetchContacts()
    }

}