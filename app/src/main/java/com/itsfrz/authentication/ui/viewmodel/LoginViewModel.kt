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
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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

    private val isLoginSuccess = mutableStateOf(false)

    fun setUserName(username: String) {
        _username.value = username
        _hasUsernameError.value = !regexValidation.validateUsername(username)
    }

    fun setPassword(password: String) {
        _password.value = password
        _hasPasswordError.value = !regexValidation.validatePassword(password)
    }

    fun login(): Boolean {
        if (!hasUsernameError.value && !hasPasswordError.value) {
            _isProgressBar.value = true
            val userModel: UserModel?
            runBlocking {
               userModel = async {
                    userModelRepository.loginUser(
                        username.value,
                        password.value
                    )
                }.await()
            }
            userModel?.let {
                if (it.username == username.value && it.password == password.value) {
                    isLoginSuccess.value = true
                    viewModelScope.launch {
                        userPreferenceRepository.saveUserPref(
                            UserPreferences(
                                username = _username.value,
                                loggedInDate = System.currentTimeMillis().toString(),
                                contactSortingType = "ASC",
                                isLoggedIn = true,
                                appTheme = "DEFAULT"
                            )
                        )
                    }
                    Log.d("Login", "login: Inside CHECK ${isLoginSuccess}")
                }
            }
            _isProgressBar.value = false

        }
        return isLoginSuccess.value
    }


}