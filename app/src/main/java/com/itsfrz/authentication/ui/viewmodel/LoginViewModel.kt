package com.itsfrz.authentication.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsfrz.authentication.data.entities.UserModel
import com.itsfrz.authentication.data.entities.UserPreferences
import com.itsfrz.authentication.data.repository.UserModelRepository
import com.itsfrz.authentication.data.repository.UserPreferenceRepository
import com.itsfrz.authentication.ui.utils.RegexValidation
import kotlinx.coroutines.*

class LoginViewModel(
    private val userPreferenceRepository: UserPreferenceRepository,
    private val regexValidation: RegexValidation,
    private val userModelRepository: UserModelRepository
) : ViewModel() {

    private val _username = mutableStateOf("")
    val username: State<String> = _username

    private val _password = mutableStateOf("")
    val password: State<String> = _password


    private val _hasUsernameError = mutableStateOf(false)
    val hasUsernameError: State<Boolean> = _hasUsernameError

    private val _hasPasswordError = mutableStateOf(false)
    val hasPasswordError: State<Boolean> = _hasPasswordError


    private val _usernameErrorMessage =
        mutableStateOf("Username should be alphanumeric value in range (3-20)")
    val usernameErrorMessage: State<String> = _usernameErrorMessage

    private val _passwordErrorMessage =
        mutableStateOf("Password should be more than 4 digit of numeric value")
    val passwordErrorMessage: State<String> = _passwordErrorMessage

    private val _isProgressBar = mutableStateOf(false)
    val isProgressBar: State<Boolean> = _isProgressBar


    private val _userPreference = mutableStateOf(UserPreferences())
    val userPreference: State<UserPreferences> = _userPreference

    private val _checkCredentials = mutableStateOf(false)
    val checkCredentials: State<Boolean> = _checkCredentials

    private val isLoginSuccess = mutableStateOf(false)

    fun setUserName(username: String) {
        _username.value = username
        _hasUsernameError.value = !regexValidation.validateUsername(username)
    }

    fun setPassword(password: String) {
        _password.value = password
        _hasPasswordError.value = !regexValidation.validatePassword(password)
    }

    fun login(){
        if (!hasUsernameError.value && !hasPasswordError.value) {
            _isProgressBar.value = true
            val job = viewModelScope.launch(Dispatchers.IO){
                val userModel: UserModel? = userModelRepository.loginUser(
                    username.value,
                    password.value
                )
                userModel?.let {
                    if (it.username == username.value && it.password == password.value) {
                        _userPreference.value =  UserPreferences(
                            username = _username.value,
                            loggedInDate = System.currentTimeMillis().toString(),
                            contactSortingType = "ASC",
                            isLoggedIn = true,
                            appTheme = "DEFAULT"
                        )
                        async(Dispatchers.IO) {  saveUserPref() }.await()
                        isLoginSuccess.value = true
                        _checkCredentials.value = true
                        Log.d("Login", "login: Inside CHECK ${isLoginSuccess}")
                    }
                }
            }
            runBlocking { job.join() }
            _isProgressBar.value = false
        }
    }

    private suspend fun saveUserPref() {
        userPreferenceRepository.saveUserPref(
            _userPreference.value
        )
    }


}