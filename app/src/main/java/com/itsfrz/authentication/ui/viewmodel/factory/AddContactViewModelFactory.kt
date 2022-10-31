package com.itsfrz.authentication.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.itsfrz.authentication.data.repository.ContactModelRepository
import com.itsfrz.authentication.data.repository.ContactProviderRepository
import com.itsfrz.authentication.ui.viewmodel.AddContactViewModel

class AddContactViewModelFactory(
    private val contactModelRepository: ContactModelRepository,
    private val contactProviderRepository: ContactProviderRepository
) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return AddContactViewModel(contactModelRepository,contactProviderRepository) as T
    }
}