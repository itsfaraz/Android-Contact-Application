package com.itsfrz.authentication.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itsfrz.authentication.data.repository.ContactModelRepository
import com.itsfrz.authentication.data.repository.UserPreferenceRepository
import com.itsfrz.authentication.ui.viewmodel.ContactViewModel

class ContactViewModelFactory(
    private val userPreferenceRepository: UserPreferenceRepository,
    private val userContactModelRepository: ContactModelRepository

)
    : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ContactViewModel(userPreferenceRepository,userContactModelRepository) as T
    }
}