package com.itsfrz.authentication.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsfrz.authentication.data.entities.UserModel
import com.itsfrz.authentication.data.repository.UserModelRepository
import com.itsfrz.authentication.ui.utils.RegexValidation
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SignUpViewModel(
    private val regexValidation: RegexValidation,
    private val userModelRepository: UserModelRepository
) : ViewModel() {

    private val _username = mutableStateOf("")
    val username: State<String> = _username

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _hasUsernameError = mutableStateOf(false)
    val hasUsernameError: State<Boolean> = _hasUsernameError

    private val _hasPasswordError = mutableStateOf(false)
    val hasPasswordError: State<Boolean> = _hasPasswordError

    private val _hasEmailError = mutableStateOf(false)
    val hasEmailError: State<Boolean> = _hasEmailError

    private val _usernameErrorMessage =
        mutableStateOf("Username should be alphanumeric value in range (3-20)")
    val usernameErrorMessage: State<String> = _usernameErrorMessage

    private val _passwordErrorMessage =
        mutableStateOf("Password should be more than 4 digit of numeric value")
    val passwordErrorMessage: State<String> = _passwordErrorMessage

    private val _emailErrorMessage = mutableStateOf("Email-id expression is not valid")
    val emailErrorMessage: State<String> = _emailErrorMessage

    private val _isSignUpSuccess = mutableStateOf(false)

    private val _isProgressBar = mutableStateOf(false)
    val isProgressBar: State<Boolean> = _isProgressBar

    fun setUsername(username: String) {
        _username.value = username
        _hasUsernameError.value = !regexValidation.validateUsername(username)
    }

    fun setPassword(password: String) {
        _password.value = password
        _hasPasswordError.value = !regexValidation.validatePassword(password)
    }

    fun setEmail(email: String) {
        _email.value = email
        _hasEmailError.value = !regexValidation.validateEmail(email)
    }

    fun signup(): Boolean {
        if (_username.value.isEmpty() || _password.value.isEmpty() || _email.value.isEmpty())
            return false

        if (!_hasEmailError.value && !_hasPasswordError.value && !_hasUsernameError.value) {

            _isProgressBar.value = true
            var doesUserExist: Boolean = false
            val response: List<UserModel>?
            runBlocking {
               response = async {
                    userModelRepository.isUserExists(
                        _username.value,
                        email.value
                    )
                }.await()
            }
            response?.let { response ->
                doesUserExist = !response.isEmpty()
            }
            if (!doesUserExist) {
                _isSignUpSuccess.value = true
               viewModelScope.launch {
                   userModelRepository.insert(
                       UserModel(
                           username = _username.value,
                           emailId = _email.value,
                           password = _password.value
                       )
                   )
               }

            }
            _isProgressBar.value = false
        }
        return _isSignUpSuccess.value
    }

}