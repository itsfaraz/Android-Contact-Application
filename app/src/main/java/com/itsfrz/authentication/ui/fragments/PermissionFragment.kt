package com.itsfrz.authentication.ui.fragments

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.itsfrz.authentication.R
import com.itsfrz.authentication.ui.Permission
import com.itsfrz.authentication.ui.compose.components.PermissionScreen
import com.itsfrz.authentication.ui.utils.Helper
import com.itsfrz.authentication.ui.viewmodel.PermissionViewModel
import com.itsfrz.authentication.ui.viewmodel.factory.PermissionViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class PermissionFragment : Fragment() {

    private val TAG: String = "TAG"
    private lateinit var viewModel: PermissionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelFactory = PermissionViewModelFactory(Permission())
        viewModel = ViewModelProvider(this, viewModelFactory)[PermissionViewModel::class.java]
        viewModel.checkReadPermissionStatus(requireContext())
        checkAndNavigate(viewModel.readPermissionState.value)

    }

    private fun runDynamicPermission() {
        Permission().requestRunTimePermission(
            requireActivity(),
            arrayOf(Manifest.permission.WRITE_CONTACTS),
            Helper.WRITE_CONTACT_RQ,
        )

        Permission().requestRunTimePermission(
            requireActivity(),
            arrayOf(Manifest.permission.READ_CONTACTS),
            Helper.READ_CONTACT_RQ,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
//                runDynamicPermission()
                val readPermission = viewModel.readPermissionState.value

                PermissionScreen(
                    readPermission,
                    permissionScreenButton = {
                        checkAndNavigate(readPermission)
                    }
                )
                BackHandler(true) {
                    requireActivity().finish()
                }
            }
        }
    }


    private fun checkAndNavigate(readPermission : Boolean) {
        if (!readPermission) {

            Log.d(TAG, "checkAndNavigate: Before")
            val result = Permission().requestRunTimePermission(
                    requireActivity(),
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    Helper.READ_CONTACT_RQ,
                )
            Log.d(TAG, "checkAndNavigate: After")
            if (result) {
                navigateToAuthenticationScreen()
            }

        } else {
            navigateToAuthenticationScreen()
        }
    }

    private fun navigateToAuthenticationScreen() {
        findNavController().navigate(
            resId = R.id.authenticationFragment,
            args = null,
            navOptions = Helper.navOptions
        )
    }


}


