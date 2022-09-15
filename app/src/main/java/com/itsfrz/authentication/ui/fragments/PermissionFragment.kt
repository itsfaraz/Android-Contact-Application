package com.itsfrz.authentication.ui.fragments

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.itsfrz.authentication.R
import com.itsfrz.authentication.ui.utils.Helper
import com.itsfrz.authentication.ui.viewmodel.PermissionViewModel
import com.itsfrz.authentication.ui.views.Permission
import com.itsfrz.authentication.ui.views.compose.components.PermissionScreen

class PermissionFragment : Fragment() {

    private lateinit var permissionViewModel: PermissionViewModel

    private lateinit var permission: Permission

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionViewModel = ViewModelProvider(
            this
        ).get(PermissionViewModel::class.java)

        permission = Permission()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                val permissionStatus = permission.requestRunTimePermission(
                    requireActivity(),
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    Helper.READ_CONTACT_RQ,
                )
                val isUserLoggedIn : Boolean = arguments?.getBoolean("isLoggedIn") ?: false

                val isPermissionGranted = permissionViewModel.permissionState
                PermissionScreen(
                    isPermissionGranted.value,
                    permissionScreenButton = {
                        if (!isPermissionGranted.value) {
                            val permissionResult = permission.requestRunTimePermission(
                                requireActivity(),
                                arrayOf(Manifest.permission.READ_CONTACTS),
                                Helper.READ_CONTACT_RQ,
                            )
                            isPermissionGranted.value = permissionResult
                        } else {
                            if (isUserLoggedIn){
                                    findNavController().navigate(
                                        resId = R.id.contactFragment,
                                        args = null,
                                        navOptions = Helper.navOptions
                                    )
                            }else{
                                findNavController().navigate(
                                    resId = R.id.authenticationFragment,
                                    args = null,
                                    navOptions = Helper.navOptions
                                )
                            }
                        }
                    }
                )

            }
        }
    }

}
