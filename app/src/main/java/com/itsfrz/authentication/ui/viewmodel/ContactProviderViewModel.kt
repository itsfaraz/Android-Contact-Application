package com.itsfrz.authentication.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.itsfrz.authentication.data.indatabase.model.Contact
import com.itsfrz.authentication.data.indatabase.repository.ContactProviderI
import com.itsfrz.authentication.data.repository.ContactProviderRepository
import com.itsfrz.authentication.provider.ContactProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactProviderViewModel(application : Application) : AndroidViewModel(application){


    private val CPVM = "CPVM";

    private var contactList  = MutableLiveData<List<Contact>>()
    private val contactProviderRepository : ContactProviderRepository
    init {

        Log.d(CPVM, ": INIT CALLED")
        contactProviderRepository = ContactProviderRepository(application.applicationContext,
            ContactProvider
        )
        fetchContactsFromProvider()

    }

    public fun getContactList() : LiveData<List<Contact>> = contactList

    private fun fetchContactsFromProvider() {
        viewModelScope.launch {
            val response = liveData(Dispatchers.IO) {
                emit(contactProviderRepository.getContactFromProvider())
            }
            response?.let {
                contactList = it as MutableLiveData<List<Contact>>
            }
            Log.d(CPVM, "fetchContactsFromProvider: Contacts Found ${contactList}")



        }

    }





}