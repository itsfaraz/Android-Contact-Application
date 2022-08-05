package com.itsfrz.authentication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.findNavController
import com.itsfrz.authentication.ui.views.activity.AuthenticationCommunicator
import com.itsfrz.authentication.model.database.PreferenceRespository
import com.itsfrz.authentication.R
import com.itsfrz.authentication.ui.views.compose.components.PermissionScreen

class PermissionFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
           setContent {
               PermissionScreen(navController = findNavController())
           }
        }
    }




}