package com.itsfrz.authentication.ui.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itsfrz.authentication.data.repository.ContactProviderRepository
import com.itsfrz.authentication.ui.viewmodel.ContactImportViewModel

class ContactImportViewModelFactory(private val contactProviderRepository : ContactProviderRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ContactImportViewModel(contactProviderRepository) as T
    }
}