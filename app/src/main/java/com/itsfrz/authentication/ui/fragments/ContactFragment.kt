package com.itsfrz.authentication.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.itsfrz.authentication.R
import com.itsfrz.authentication.data.repository.UserPreferenceRepository
import com.itsfrz.authentication.ui.utils.Helper
import com.itsfrz.authentication.ui.viewmodel.ContactViewModel
import com.itsfrz.authentication.ui.viewmodelfactory.ContactViewModelFactory
import com.itsfrz.authentication.ui.views.compose.components.ContactListLayout
import com.itsfrz.authentication.ui.views.compose.components.NavBarLayout

class ContactFragment : Fragment() {


    private lateinit var contactViewModel : ContactViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
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
                            showSearchBar = false,
                            toggleSearchBar = {},
                            isUserInfoMenuItem = true,
                            isSelectAllMenuItem = false,
                            iconClickEvent = {},
                            importClickEvent = {
                                findNavController().navigate(
                                    resId = R.id.contactImportFragment,
                                    args = null,
                                    navOptions = Helper.navOptions
                                )
                            },
                            userInfoClickEvent = {},
                            deleteSelectedClickEvent = {},
                            logoutClickEvent = {

                            },
                            selectAllClickEvent = {},
                            getSearchQuery = {},
                            searchQuery = "",
                            totalContactsFound = 0

                        )
                    },
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        ContactListLayout()
                    }
                }
                BackHandler(true) {
                    requireActivity().finish()
                }
            }
        }

    }


}