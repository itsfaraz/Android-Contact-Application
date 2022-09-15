package com.itsfrz.authentication.ui.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itsfrz.authentication.data.repository.UserModelRepository
import com.itsfrz.authentication.ui.utils.RegexValidation
import com.itsfrz.authentication.ui.viewmodel.SignUpViewModel

class SignUpViewModelFactory(
    private val regexValidation: RegexValidation,
    private val userModelRepository: UserModelRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignUpViewModel(regexValidation,userModelRepository) as T
    }
}