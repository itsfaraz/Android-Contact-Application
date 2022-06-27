package com.itsfrz.authentication.ui.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.itsfrz.authentication.data.repository.UserModelRepository
import com.itsfrz.authentication.model.database.PreferenceRespository
import com.itsfrz.authentication.model.database.room.AppDatabase
import com.itsfrz.authentication.model.database.room.model.UserModel
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log

class LoginViewModel(application: Application) : AndroidViewModel(application) {


    private val LVM : String = "LVM"

    private val userModelRepository : UserModelRepository
    private val preferenceRespository by lazy {
        PreferenceRespository(application.applicationContext)
    }


    init {

        val userDao = AppDatabase.getDatabase(application.applicationContext).userDao()
        userModelRepository = UserModelRepository(userDao)

    }

    public fun loginUser(username: String,password: String) : UserModel{
        var user : UserModel? = null

        runBlocking {
            CoroutineScope(Dispatchers.IO).async {
                user =  userModelRepository.loginUser(username,password)!!
                Log.d(LVM, "login User: ${user}")
            }.await()
        }

        if (user != null)
            return user!!
        return UserModel("","","")
    }

    public fun validateInputs(username: String, password: String): Boolean {
        if (username.length <= 0)
        {
            return false
        }

        if (password.length <= 0)
        {
            return false
        }
        return true
    }


    public fun persistMyPreferences(username: String,emailId : String) {
        preferenceRespository.setCurrentUser(username)
        preferenceRespository.setCurrentEmail(emailId)
        preferenceRespository.setLoggedIn(true)
        preferenceRespository.setLoggedInDate(Calendar.getInstance().time.toString())
    }

}