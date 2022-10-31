package com.itsfrz.authentication.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsfrz.authentication.data.entities.UserPreferences
import com.itsfrz.authentication.data.repository.UserPreferenceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AuthenticationViewModel(
    private val userPreferenceRepository: UserPreferenceRepository
) : ViewModel() {
    private val  AVM_DEBUG = "AVM_DEBUG"

    private val _userPreference = mutableStateOf<UserPreferences>(UserPreferences())
    val userPreferences  : State<UserPreferences> = _userPreference

    private val _username = mutableStateOf("")
    val username : MutableState<String> = _username

    private val _isLoggedIn = mutableStateOf(false)
    val isLoggedIn : MutableState<Boolean> = _isLoggedIn

    private val _isProgressBar = mutableStateOf(true)
    val isProgressBar : MutableState<Boolean> = _isProgressBar



    fun checkLoggedInStatus() {
        _isProgressBar.value = true
        try {
            runBlocking {
                viewModelScope.launch(Dispatchers.IO){
                    _userPreference.value = userPreferenceRepository.getUserPreference()
                    _username.value = _userPreference.value.username
                    _isLoggedIn.value = _userPreference.value.isLoggedIn
                    _isProgressBar.value = false
                }
            }

        }catch (e : Exception){
            Log.d(AVM_DEBUG, "checkLoggedInStatus: $e")
        }finally {
            _isProgressBar.value = false
        }
    }


}