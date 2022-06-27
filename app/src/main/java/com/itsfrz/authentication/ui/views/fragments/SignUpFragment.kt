package com.itsfrz.authentication.ui.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.itsfrz.authentication.R
import com.itsfrz.authentication.databinding.FragmentSignUpBinding
import com.itsfrz.authentication.model.database.room.AppDatabase
import com.itsfrz.authentication.model.database.room.model.UserModel
import com.itsfrz.authentication.ui.viewmodel.SignUpViewModel
import com.itsfrz.authentication.ui.views.activity.AuthenticationCommunicator


class SignUpFragment : Fragment() {

    private val SIGNUP_FRAG = "SIGNUP_FRAG"
    private lateinit var communicator: AuthenticationCommunicator


    private val signUpViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(SignUpViewModel::class.java)
    }

    private lateinit var signUpFragmentBinding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        signUpFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);
        val view = signUpFragmentBinding.root;

        // Communicator Interface set
        communicator = activity as AuthenticationCommunicator



        insertNewUser()
        return view
    }

    private fun insertNewUser() {
        signUpFragmentBinding.registerButton.setOnClickListener {
            val email : String = signUpFragmentBinding.registerEmailInput.text.toString()
            val password : String = signUpFragmentBinding.registerPasswordInput.text.toString()
            val againPassword : String = signUpFragmentBinding.registerAgainPasswordInput.text.toString()
            val username : String = signUpFragmentBinding.registerUsernameInput.text.toString()
            val isFieldEmpty: Boolean = signUpViewModel.checkIsFieldEmpty(email, password, againPassword, username)
            val isPasswordEqual: Boolean = signUpViewModel.checkIsPasswordEqual(password,againPassword);
            if (isFieldEmpty && isPasswordEqual) {
                signUpFragmentBinding.also {
                    signUpViewModel.signUpUser(
                        UserModel(
                            it.registerUsernameInput.text.toString(),
                            it.registerEmailInput.text.toString(),
                            it.registerPasswordInput.text.toString()
                        )
                    )
                    Toast.makeText(
                        requireContext(),
                        "${it.registerUsernameInput.text.toString()} is inserted Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d(SIGNUP_FRAG, "onCreateView: Inserted Successfully")
                    communicator.routeToLogin(it.registerUsernameInput.text.toString())
                }
            }
        }
    }

}