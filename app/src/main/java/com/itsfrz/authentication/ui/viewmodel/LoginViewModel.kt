package com.itsfrz.authentication.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsfrz.authentication.data.repository.UserModelRepository
import com.itsfrz.authentication.model.database.PreferenceRespository
import com.itsfrz.authentication.model.database.room.AppDatabase
import com.itsfrz.authentication.data.entities.UserModel
import com.itsfrz.authentication.ui.viewmodel.utils.RegexValidation
import kotlinx.coroutines.*
import java.util.*

//class LoginViewModel(application: Application) : AndroidViewModel(application) {


//    private val LVM : String = "LVM"
//
//    private val userModelRepository : UserModelRepository
//    private val preferenceRespository by lazy {
//        PreferenceRespository(application.applicationContext)
//    }
//
//
//    init {
//
//        val userDao = AppDatabase.getDatabase(application.applicationContext).userDao()
//        userModelRepository = UserModelRepository(userDao)
//
//    }
//
//    public  fun loginUser(username: String,password: String) : Boolean {
//        var user : UserModel? = null
//
//        if (validateInputs(username, password)){
//
//            runBlocking {
//                user = async { userModelRepository.loginUser(username,password) }.await()
//            }
//        }
//
//        val responseUserName : String = user?.username ?: ""
//        if (username.equals(responseUserName))
//            return true
//        return false
//    }
//
//
//
//    public fun validateInputs(username: String, password: String): Boolean {
//        if (username.length <= 0)
//        {
//            return false
//        }
//
//        if (password.length <= 0)
//        {
//            return false
//        }
//        return true
//    }
//
//
//    public fun persistMyPreferences(username: String) {
//        preferenceRespository.setCurrentUser(username)
//        preferenceRespository.setLoggedIn(true)
//        preferenceRespository.setLoggedInDate(Calendar.getInstance().time.toString())
//    }

//}

class LoginViewModel : ViewModel(){

    private lateinit var regex : RegexValidation
    private var usernameState = mutableStateOf(false)
    private var passwordState = mutableStateOf(false)
    init {
        regex = RegexValidation()
    }

    fun validateUsername(username : String) : Boolean {
        if (username.isNotBlank()){
            usernameState.value = !regex.validateUsername(username)
        }
        Log.d("USERNAME", "validateUsername: ${usernameState.value}")
        return usernameState.value
    }
    fun validatePassword(password : String) : Boolean {
        if (password.isNotBlank()){
            passwordState.value = !regex.validateUsername(password)
        }
        return passwordState.value
    }




}