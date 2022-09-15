package com.itsfrz.authentication.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CommonViewModel : ViewModel() {

    private val _loginStatus = mutableStateOf(false)
    val loginStatus : State<Boolean> = _loginStatus


    private val _currentUser = mutableStateOf("")
    val currentUser : State<String> = _currentUser


    private val _loginDate = mutableStateOf("")
    val loginDate : State<String> = _loginDate


    fun setLoginStatus(value : Boolean){
        _loginStatus.value = value
    }

}