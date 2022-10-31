package com.itsfrz.authentication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.itsfrz.authentication.R
import com.itsfrz.authentication.data.repository.UserPreferenceRepository
import com.itsfrz.authentication.ui.compose.components.AuthenticationScreen
import com.itsfrz.authentication.ui.compose.components.CircularLoader
import com.itsfrz.authentication.ui.utils.Helper
import com.itsfrz.authentication.ui.viewmodel.AuthenticationViewModel
import com.itsfrz.authentication.ui.viewmodel.factory.AuthenticationViewModelFactory


class AuthenticationFragment : Fragment() {

    private lateinit var viewModel : AuthenticationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userPreferenceRepository = UserPreferenceRepository(requireContext())
        val viewModelFactory = AuthenticationViewModelFactory(userPreferenceRepository)
        viewModel = ViewModelProvider(this,viewModelFactory)[AuthenticationViewModel::class.java]

        viewModel.checkLoggedInStatus()
        viewModel.userPreferences.value
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val isProgressBar = viewModel.isProgressBar.value
                val isLoggedIn = viewModel.isLoggedIn.value
                val username = viewModel.username.value
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                    if (isProgressBar){
                        CircularLoader()
                    }

                    if (isLoggedIn && username.isNotEmpty()){
                        findNavController().navigate(
                            resId = R.id.contactFragment,
                            args = bundleOf(
                                 Helper.USER_PREF to viewModel.userPreferences.value
                            ),
                            navOptions = Helper.navOptions
                        )
                    }else{
                        AuthenticationScreen(navController = findNavController())
                    }

                    BackHandler(true) {
                        requireActivity().finish()
                    }

                }
            }

        }

    }




}