//package com.itsfrz.authentication.ui.views.fragments
//
//import android.os.Bundle
//import android.util.Log
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.databinding.DataBindingUtil
//import androidx.lifecycle.ViewModelProvider
//import com.itsfrz.authentication.ui.views.activity.AuthenticationCommunicator
//import com.itsfrz.authentication.R
//import com.itsfrz.authentication.databinding.FragmentLoginBinding
//import com.itsfrz.authentication.ui.viewmodel.LoginViewModel
//
//
//class LoginFragment : Fragment() {
//
//
//
//    private lateinit var communicator: AuthenticationCommunicator
//
//
//    private val loginViewModel by lazy {
//        ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(LoginViewModel::class.java)
//    }
//
//
//    private lateinit var loginBinding: FragmentLoginBinding
//
//    private val LOGIN_FRAGMENT = "LOGIN_FRAGMENT"
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        loginBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_login,container,false)
//        communicator = activity as AuthenticationCommunicator
//
//
//        loginButtonInvoke()
//        signUpButtonInvoke()
//
//        return loginBinding.root
//    }
//
//    private fun signUpButtonInvoke() {
//        loginBinding.signUpButon.setOnClickListener {
//            communicator.routeToSignUp()
//        }
//    }
//
//    private fun loginButtonInvoke() {
//        loginBinding.loginButton.setOnClickListener {
//            val username : String = loginBinding.loginUsernameInput.text.toString()
//            val password : String = loginBinding.loginPasswordInput.text.toString()
//            if(loginViewModel.loginUser(username,password))
//            {
//                saveUserSession(username)
//                communicator.routerFromLoginToContactPage()
//            }else{
//                Toast.makeText(requireContext(), "${username} not exists, Please register yourself", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    private fun saveUserSession(username : String) = loginViewModel.persistMyPreferences(username)
//
//
//
//}