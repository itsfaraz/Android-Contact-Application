package com.itsfrz.authentication.ui.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itsfrz.authentication.data.repository.UserPreferenceRepository
import com.itsfrz.authentication.ui.viewmodel.ContactViewModel

class ContactViewModelFactory(private val userPreferenceRepository: UserPreferenceRepository)
    : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ContactViewModel(userPreferenceRepository) as T
    }
}