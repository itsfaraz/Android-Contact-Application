package com.itsfrz.authentication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.itsfrz.authentication.data.repository.UserPreferenceRepository
import com.itsfrz.authentication.ui.viewmodel.AuthenticationViewModel
import com.itsfrz.authentication.ui.viewmodel.CommonViewModel
import com.itsfrz.authentication.ui.viewmodel.factory.AuthenticationViewModelFactory
import com.itsfrz.authentication.ui.views.compose.components.AuthenticationScreen


class AuthenticationFragment : Fragment() {

    private lateinit var authViewModel: AuthenticationViewModel
    private val commonViewModel : CommonViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var userPreferenceRepository = UserPreferenceRepository(requireContext())
        val viewModelFactory = AuthenticationViewModelFactory(userPreferenceRepository)
        authViewModel =
            ViewModelProvider(this, viewModelFactory).get(AuthenticationViewModel::class.java)
        authViewModel.fetchLoggedInStatus()


    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

//                val isUserLoggedIn =  authViewModel.isLogginSuccess.value
//                commonViewModel.setLoginStatus(isUserLoggedIn)
                AuthenticationScreen(navController = findNavController())
                BackHandler(true) {
                    requireActivity().finish()
                }
            }

        }

    }




}