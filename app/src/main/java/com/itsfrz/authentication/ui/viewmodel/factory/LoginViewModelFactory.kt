package com.itsfrz.authentication.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itsfrz.authentication.data.repository.UserModelRepository
import com.itsfrz.authentication.data.repository.UserPreferenceRepository
import com.itsfrz.authentication.ui.utils.RegexValidation
import com.itsfrz.authentication.ui.viewmodel.LoginViewModel

class LoginViewModelFactory(
    private val userPreferenceRepository: UserPreferenceRepository,
    private val regexValidation: RegexValidation,
    private val userModelRepository: UserModelRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(
            userModelRepository = userModelRepository,
            regexValidation = regexValidation,
            userPreferenceRepository = userPreferenceRepository
        ) as T
    }
}