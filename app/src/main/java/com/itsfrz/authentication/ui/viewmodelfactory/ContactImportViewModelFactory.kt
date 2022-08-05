package com.itsfrz.authentication.ui.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itsfrz.authentication.data.repository.ContactProviderRepository

class ContactImportViewModelFactory(val contactProviderRepository : ContactProviderRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ContactImportViewModelFactory(contactProviderRepository) as T
    }
}