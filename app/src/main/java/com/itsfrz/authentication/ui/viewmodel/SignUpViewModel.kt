package com.itsfrz.authentication.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.itsfrz.authentication.data.repository.UserModelRepository
import com.itsfrz.authentication.model.database.room.AppDatabase
import com.itsfrz.authentication.model.database.room.dao.UserDao
import com.itsfrz.authentication.data.entities.UserModel
import com.itsfrz.authentication.ui.viewmodel.utils.RegexValidation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.sign

//
//class SignUpViewModel(application: Application) : AndroidViewModel(application) {
//
//    private val userModelRepository: UserModelRepository
//
//    init {
//        val userDao: UserDao = AppDatabase.getDatabase(application.applicationContext).userDao()
//        userModelRepository = UserModelRepository(userDao)
//    }
//
//
//    fun signUpUser(userModel: UserModel) {
//        CoroutineScope(Dispatchers.IO).launch {
//            userModelRepository.insert(userModel)
//        }
//    }
//
//
//    public fun checkIsPasswordEqual(password: String, againPassword: String): Boolean {
//        return password.equals(againPassword)
//    }
//
//    public fun checkIsFieldEmpty(
//        email: String,
//        password: String,
//        againPassword: String,
//        username: String
//    ): Boolean {
//        if (email.length <= 0)
//            return false
//        if (password.length <= 0)
//            return false
//        if (againPassword.length <= 0)
//            return false
//        if (username.length <= 0)
//            return false
//
//        return true
//    }
//
//}

class SignUpViewModel : ViewModel(){

    private val regex : RegexValidation
    private var usernameState = mutableStateOf(false)
    private var passwordState = mutableStateOf(false)
    private var emailState = mutableStateOf(false)

    init {
        regex = RegexValidation()
    }

    fun validateUsername(username : String) : Boolean{
        if (username.isNotBlank()){
            usernameState.value = !regex.validateUsername(username)
        }
        return usernameState.value
    }

    fun validateEmailId(emailId : String) : Boolean{
        if (emailId.isNotBlank()){
            emailState.value = !regex.validateEmail(emailId)
        }
        return emailState.value
    }

    fun validatePassword(password : String) : Boolean{
        if (password.isNotBlank()){
            passwordState.value = !regex.validatePassword(password)
        }
        return passwordState.value
    }



}