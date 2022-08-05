package com.itsfrz.authentication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.itsfrz.authentication.ui.views.compose.components.AuthenticationScreen


class AuthenticationFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        return ComposeView(requireContext()).apply {
            setContent {
                AuthenticationScreen(navController = findNavController())
            }
        }
    }

}