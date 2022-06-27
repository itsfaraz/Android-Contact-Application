package com.itsfrz.authentication.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.itsfrz.authentication.data.repository.UserModelRepository
import com.itsfrz.authentication.model.database.room.AppDatabase
import com.itsfrz.authentication.model.database.room.dao.UserDao
import com.itsfrz.authentication.model.database.room.model.UserModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel(application: Application) : AndroidViewModel(application) {

    private val userModelRepository: UserModelRepository

    init {
        val userDao: UserDao = AppDatabase.getDatabase(application.applicationContext).userDao()
        userModelRepository = UserModelRepository(userDao)
    }


    fun signUpUser(userModel: UserModel) {
        CoroutineScope(Dispatchers.IO).launch {
            userModelRepository.insert(userModel)
        }
    }


    public fun checkIsPasswordEqual(password: String, againPassword: String): Boolean {
        return password.equals(againPassword)
    }

    public fun checkIsFieldEmpty(
        email: String,
        password: String,
        againPassword: String,
        username: String
    ): Boolean {
        if (email.length <= 0)
            return false
        if (password.length <= 0)
            return false
        if (againPassword.length <= 0)
            return false
        if (username.length <= 0)
            return false

        return true
    }

}