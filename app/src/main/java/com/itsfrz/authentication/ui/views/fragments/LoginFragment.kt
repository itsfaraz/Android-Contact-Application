package com.itsfrz.authentication.ui.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.itsfrz.authentication.ui.views.activity.AuthenticationCommunicator
import com.itsfrz.authentication.model.database.PreferenceRespository
import com.itsfrz.authentication.R
import com.itsfrz.authentication.model.database.room.AppDatabase
import com.itsfrz.authentication.model.database.room.model.UserModel
import kotlinx.coroutines.*
import java.util.*


class LoginFragment : Fragment() {

    private lateinit var communicator: AuthenticationCommunicator
    private val preferenceRespository by lazy {
        PreferenceRespository(requireContext())
    }

    private val db by lazy {
        AppDatabase.getDatabase(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val usernameInput : EditText = view.findViewById<EditText>(R.id.loginUsernameInput)
        val passwordInput = view.findViewById<EditText>(R.id.loginPasswordInput)
        val loginButton = view.findViewById<Button>(R.id.loginButton)
        val signUpButton = view.findViewById<Button>(R.id.signUpButon)
        communicator = activity as AuthenticationCommunicator

        viaSignUp(usernameInput)
        loginButton.setOnClickListener {
            if(validateInputs(usernameInput,passwordInput))
            {
                runBlocking {

                    val user : UserModel? = getUserInfoFromDatabase(usernameInput.text.toString(),passwordInput.text.toString())
                    if (user!=null) {
                        persistMyPreferences(usernameInput.text.toString(),user.emailId.toString());
                        communicator.routerFromLoginToContactPage()
                    }else{
                        Toast.makeText(requireContext(), "Invalid Credentials!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        signUpButton.setOnClickListener {
            communicator.routeToSignUp()
        }

        return view
    }

    private fun persistMyPreferences(username: String,emailId : String) {
        preferenceRespository.setCurrentUser(username)
        preferenceRespository.setCurrentEmail(emailId)
        preferenceRespository.setLoggedIn(true)
        preferenceRespository.setLoggedInDate(Calendar.getInstance().time.toString())
    }

    private suspend fun getUserInfoFromDatabase(username: String, password: String): UserModel? {
            val userJob = CoroutineScope(Dispatchers.IO).async {
                db.userDao().loginUser(username, password)
            }
        if (userJob != null)
            return userJob.await()
        else
            return null

    }

    private fun viaSignUp(usernameInput : EditText) {
        val data : String? = arguments?.getString("username").toString() ?: ""
        if (data.equals("null"))
            usernameInput.setText("")
        else
            usernameInput.setText(data)

    }


    private fun validateInputs(usernameInput: EditText?, passwordInput: EditText?): Boolean {
        if (usernameInput?.text.toString().length <= 0)
        {
            usernameInput?.setError("Username field should not be empty")
            return false
        }

        if (passwordInput?.text.toString().length <= 0)
        {
            passwordInput?.setError("Passowrd field should not be empty")
            return false
        }
        return true
    }
}