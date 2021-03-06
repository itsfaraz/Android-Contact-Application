package com.itsfrz.authentication.ui.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.itsfrz.authentication.data.repository.ContactModelRepository
import com.itsfrz.authentication.model.database.room.AppDatabase
import com.itsfrz.authentication.data.entities.ContactModel
import com.itsfrz.authentication.model.database.PreferenceRespository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactViewModel(application : Application) : AndroidViewModel(application){

    private val CVM = "CVM"

    private var allContact = MutableLiveData<List<ContactModel>>()
    private val contactRepository : ContactModelRepository
    private val preferenceRespository by lazy {
        PreferenceRespository(application.applicationContext)
    }




    init {
        val contacDao = AppDatabase.getDatabase(application.applicationContext).contactDao()
        contactRepository = ContactModelRepository(contacDao)
        Log.d(CVM, "Constructor: ${preferenceRespository.getCurrentUser()}")
        getAllContacts(preferenceRespository.getCurrentUser()!!)


    }

    fun getContactListObserver() : LiveData<List<ContactModel>> = allContact

    private fun getAllContacts(username : String) {

        viewModelScope.launch{
            val response = liveData(Dispatchers.IO){
                emitSource(contactRepository.allContacts(username))
            }
            response?.let {
                allContact = it as MutableLiveData<List<ContactModel>>
            }
            Log.d(CVM, "getAllContacts: ${allContact.value}")
        }

    }


    public fun clearUser(){
        preferenceRespository.clearUser()
    }

    public fun getCurrentSession() : String = preferenceRespository.getCurrentUser() ?: ""
    fun deleteMyContacts() {
        try{
            viewModelScope.launch(Dispatchers.IO) {
                contactRepository.deleteAllContacts(preferenceRespository.getCurrentUser()!!)
            }
        }catch (e : Exception){
            Log.d(CVM, "deleteMyContacts: Failed To Delete Contacts")
        }
    }

    public fun deleteMyContactItem(contact: ContactModel) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                contactRepository.deleteSpecificUserContact(preferenceRespository.getCurrentUser()!!,contact.contactNumber)
            Log.d(CVM, "deleteMyContactItem: Deleted Contact")
            }
        }catch (e : Exception){
            Log.d(CVM, "deleteMyContactItem:${e}")
        }
    }

    fun updateUserContact(contactModel: ContactModel) {
        try {
            contactModel.username = preferenceRespository.getCurrentUser()!!
            viewModelScope.launch(Dispatchers.IO) {
                contactRepository.updateContact(contactModel)
            }
        }catch (e : Exception){
            Log.d(CVM, "updateUserContact: $e")
        }
    }


}