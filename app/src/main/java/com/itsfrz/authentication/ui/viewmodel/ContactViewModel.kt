package com.itsfrz.authentication.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsfrz.authentication.data.repository.ContactModelRepository
import com.itsfrz.authentication.model.database.room.AppDatabase
import com.itsfrz.authentication.model.database.room.dao.ContactDao
import com.itsfrz.authentication.model.database.room.model.ContactModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactViewModel(application : Application) : ViewModel(){

    private val allContact = MutableLiveData<List<ContactModel>>()
    private val contactRepository : ContactModelRepository
    private val username : String= getLoggedUser()

    private fun getLoggedUser(): String {
        return "itsfrz" // make it dynamic
    }


    init {

        val contacDao = AppDatabase.getDatabase(application.applicationContext).contactDao()
        contactRepository = ContactModelRepository(contacDao)


    }

    fun getContactListObserver() : MutableLiveData<List<ContactModel>> = allContact

    fun getAllContacts(username : String) {
       viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                contactRepository.allContacts(username)
            }
            if (response != null){
                allContact.postValue(response.value)
            }
       }
    }




}