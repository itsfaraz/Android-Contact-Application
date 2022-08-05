package com.itsfrz.authentication.ui.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.*
import com.itsfrz.authentication.R
import com.itsfrz.authentication.data.repository.ContactModelRepository
import com.itsfrz.authentication.model.database.room.AppDatabase
import com.itsfrz.authentication.data.entities.ContactModel
import com.itsfrz.authentication.model.database.PreferenceRespository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
//
//class ContactViewModel(application : Application) : AndroidViewModel(application){
//
//    private val CVM = "CVM"
//
//    private var allContact = MutableLiveData<List<ContactModel>>()
//    private val contactRepository : ContactModelRepository
//    private val preferenceRespository by lazy {
//        PreferenceRespository(application.applicationContext)
//    }
//
//
//
//
//    init {
//        val contacDao = AppDatabase.getDatabase(application.applicationContext).contactDao()
//        contactRepository = ContactModelRepository(contacDao)
//        Log.d(CVM, "Constructor: ${preferenceRespository.getCurrentUser()}")
//        getAllContacts(preferenceRespository.getCurrentUser()!!)
//
//
//    }
//
//    fun getContactListObserver() : LiveData<List<ContactModel>> = allContact
//
//    private fun getAllContacts(username : String) {
//
//        viewModelScope.launch{
//            val response = liveData(Dispatchers.IO){
//                emitSource(contactRepository.allContacts(username))
//            }
//            response?.let {
//                allContact = it as MutableLiveData<List<ContactModel>>
//            }
//            Log.d(CVM, "getAllContacts: ${allContact.value}")
//        }
//
//    }
//
//
//    public fun clearUser(){
//        preferenceRespository.clearUser()
//    }
//
//    public fun getCurrentSession() : String = preferenceRespository.getCurrentUser() ?: ""
//    fun deleteMyContacts() {
//        try{
//            viewModelScope.launch(Dispatchers.IO) {
//                contactRepository.deleteAllContacts(preferenceRespository.getCurrentUser()!!)
//            }
//        }catch (e : Exception){
//            Log.d(CVM, "deleteMyContacts: Failed To Delete Contacts")
//        }
//    }
//
//    public fun deleteMyContactItem(contact: ContactModel) {
//        try {
//            viewModelScope.launch(Dispatchers.IO) {
//                contactRepository.deleteSpecificUserContact(preferenceRespository.getCurrentUser()!!,contact.contactNumber)
//            Log.d(CVM, "deleteMyContactItem: Deleted Contact")
//            }
//        }catch (e : Exception){
//            Log.d(CVM, "deleteMyContactItem:${e}")
//        }
//    }
//
//    fun updateUserContact(contactModel: ContactModel) {
//        try {
//            contactModel.username = preferenceRespository.getCurrentUser()!!
//            viewModelScope.launch(Dispatchers.IO) {
//                contactRepository.updateContact(contactModel)
//            }
//        }catch (e : Exception){
//            Log.d(CVM, "updateUserContact: $e")
//        }
//    }
//
//
//}

class ContactViewModel : ViewModel(){

    data class Contact(
        val contactImage : Int,
        val contactName : String
    )

    val listOfDemoContacts = mutableStateListOf<Contact>()
    private val selectedItemIndex = mutableListOf<Int>()
    val selectedItemList = mutableListOf<Contact>()

    init {
        listOfDemoContacts.addAll(
            listOf(
            Contact(R.drawable.profile,"Faraz Sheikh"),
            Contact(R.drawable.profile,"Faisal Sheikh"),
            Contact(R.drawable.profile,"Hasnain Sheikh"),
            Contact(R.drawable.profile,"Deveesh Nantha"),
            Contact(R.drawable.profile,"Robert Downey Jr"),
            Contact(R.drawable.profile,"Chris Evans"),)
        )

//        selectedItemIndex.addAll(listOf(0,3,4))
    }

    fun getContacts() = listOfDemoContacts

    fun addIndexInList(index: Int){
        if (index !in selectedItemIndex){
            selectedItemIndex.add(index)
        }
    }

    fun checkIndexIsInList(index: Int) : Boolean = if (index in selectedItemIndex) true else false

    fun getSelectedIndex() = selectedItemIndex

    fun removeContact(index : Int){
        listOfDemoContacts.removeAt(index)
    }

    fun addContact(contact: Contact){
        listOfDemoContacts.add(contact)
    }
}
