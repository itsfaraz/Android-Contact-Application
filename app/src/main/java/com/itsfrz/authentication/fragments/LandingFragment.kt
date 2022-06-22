package com.itsfrz.authentication.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.itsfrz.authentication.AuthenticationCommunicator
import com.itsfrz.authentication.database.PreferenceRespository
import com.itsfrz.authentication.MainActivity
import com.itsfrz.authentication.R

class LandingFragment : Fragment() {

    private lateinit var communicator: AuthenticationCommunicator
    private val preferenceRespository by lazy {
        PreferenceRespository(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_landing, container, false)
        val outputUsername : TextView = view.findViewById(R.id.username)
        val outputDate : TextView = view.findViewById(R.id.loggedInDate)
        val outputLoggedStatus : TextView = view.findViewById(R.id.loggedStatus)
        val back : Button = view.findViewById(R.id.back)

        communicator = activity as AuthenticationCommunicator
        val username = arguments?.getString("AuthenticatedUser") ?: ""
//        communicator.routerFromLoginToContactPage(username)
        populateUserDetails(outputUsername,outputDate,outputLoggedStatus)
        onBack(back);
        return view
    }

    private fun onBack(back : View) {
        back.setOnClickListener {
            communicator.routerFromLandingToContactPage()
        }

    }



    private fun populateUserDetails(outputUsername: TextView, outputDate: TextView, outputLoggedStatus: TextView) {
        outputUsername.setText("Username ${preferenceRespository.getCurrentUser()}")
        outputDate.setText("Logged In Date ${preferenceRespository.getLoggedInDate()}")
        outputLoggedStatus.setText("User Login Status is Active")
    }


}