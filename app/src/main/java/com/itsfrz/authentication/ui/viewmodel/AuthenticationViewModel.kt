package com.itsfrz.authentication.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsfrz.authentication.data.repository.UserPreferenceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthenticationViewModel(
    private val userPreferenceRepository: UserPreferenceRepository
) : ViewModel() {

    private val _isLogginSuccess = mutableStateOf(false)
    val isLogginSuccess: State<Boolean> = _isLogginSuccess

    private val _userName = mutableStateOf("")
    val userName = _userName

    private val _loggedInDate = mutableStateOf("")
    val loggedInDate = _loggedInDate

    private val _sortingOrder = mutableStateOf("")
    val sortingOrder = _sortingOrder

    fun fetchLoggedInStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            userPreferenceRepository.getUserPref().collect { userPreference ->
                _isLogginSuccess.value = userPreference.isLoggedIn
                _userName.value = userPreference.username
                _loggedInDate.value = userPreference.loggedInDate
                _sortingOrder.value = userPreference.contactSortingType
            }
        }
    }

}