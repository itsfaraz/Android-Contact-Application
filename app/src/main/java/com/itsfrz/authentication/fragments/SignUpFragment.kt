package com.itsfrz.authentication.fragments

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.itsfrz.authentication.AuthenticationCommunicator
import com.itsfrz.authentication.database.LocalDatabaseHelper
import com.itsfrz.authentication.database.UserTable
import com.itsfrz.authentication.model.User
import com.itsfrz.authentication.R
import com.itsfrz.authentication.database.room.AppDatabase
import com.itsfrz.authentication.database.room.model.UserModel
import kotlinx.coroutines.*


class SignUpFragment : Fragment() {

    private lateinit var communicator: AuthenticationCommunicator
    private val db by lazy{
        AppDatabase.getDatabase(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)

        // View Initilization
        val usernameInput = view.findViewById<EditText>(R.id.registerUsernameInput)
        val passwordInput = view.findViewById<EditText>(R.id.registerPasswordInput)
        val passwordReInput = view.findViewById<EditText>(R.id.registerAgainPasswordInput)
        val emailInput = view.findViewById<EditText>(R.id.registerEmailInput)
        val registerButton = view.findViewById<Button>(R.id.registerButton)


        // Communicator Interface set
        communicator = activity as AuthenticationCommunicator
        registerButton.setOnClickListener {
            if(checkValidate(usernameInput,passwordInput,passwordReInput,emailInput) && checkCrossPassword(passwordInput,passwordReInput))
            {
                runBlocking {
                    val isSaveSuccessfull = saveToDatabase(usernameInput.text.toString(),passwordInput.text.toString(),emailInput.text.toString())
                    if (isSaveSuccessfull){
                        communicator.routeToLogin(usernameInput.text.toString())
                    }else
                        Toast.makeText(requireContext(), "Username And EmailId Already Exists", Toast.LENGTH_SHORT).show()
                }
            }
        }



        return view
    }

    private suspend fun saveToDatabase(username: String, password: String, email : String) : Boolean {

        val isUserExistsJob = CoroutineScope(Dispatchers.IO).async {
            db.userDao().isUserExists(username,email)
        }
        if (isUserExistsJob.await().size == 0){
            db.userDao().insertUser(
                UserModel(
                    username,email,password
                )
            )
            return true
        }

       return false
    }

    private fun checkCrossPassword(passwordInput: EditText, passwordReInput: EditText): Boolean {
        if (passwordInput.text.toString().equals(passwordReInput.text.toString()))
            return true
        passwordInput.setError("Password field should be same Re-Password")
        passwordReInput.setError("Re-Password field should be same as Password")
        return false
    }

    private fun checkValidate(usernameInput: EditText, passwordInput: EditText, passwordReInput: EditText,emailInput : EditText): Boolean {
        if (emailInput.text.toString().length <= 0) {
            emailInput.setError("Email field should not be empty")
            return false
        }
        if (usernameInput.text.toString().length <= 0) {
            usernameInput.setError("Username field should not be empty")
            return false
        }
        if (passwordInput.text.toString().length <= 0) {
            passwordInput.setError("Password field should not be empty")
            return false
        }
        if (passwordReInput.text.toString().length <= 0) {
            passwordReInput.setError("Re-Password field should not be empty")
            return false
        }
        return true
    }


}