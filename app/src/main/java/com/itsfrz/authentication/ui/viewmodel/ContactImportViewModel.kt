package com.itsfrz.authentication.ui.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.itsfrz.authentication.data.entities.ContactModel
import com.itsfrz.authentication.data.repository.ContactModelRepository
import com.itsfrz.authentication.data.repository.ContactProviderRepository
import com.itsfrz.authentication.data.utils.ContactLog
import com.itsfrz.authentication.model.database.PreferenceRespository
import com.itsfrz.authentication.model.database.room.AppDatabase
import com.itsfrz.authentication.model.database.room.dao.ContactDao
import com.itsfrz.authentication.provider.ContactProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//class ContactImportViewModel(application : Application) : AndroidViewModel(application){
//
//
//    private val CPVM : String = "CPVM";
//
//    private var contactList  = MutableLiveData<List<ContactModel>>()
//    private val contactProviderRepository : ContactProviderRepository
//    private val contactModelRepository : ContactModelRepository
//    private val preferenceRespository by lazy {
//        PreferenceRespository(application.applicationContext)
//    }
//    private var username : String = ""
//
//    init {
//
//        Log.d(CPVM, ": INIT CALLED")
//        contactProviderRepository = ContactProviderRepository(application.applicationContext,
//            ContactProvider
//        )
//        val contactDao : ContactDao = AppDatabase.getDatabase(application.applicationContext).contactDao()
//        contactModelRepository = ContactModelRepository(contactDao)
//        fetchContactsFromProvider()
//
//
//    }
//
//    public fun getContactList() : LiveData<List<ContactModel>>  {
//        this.username = preferenceRespository.getCurrentUser()!!
//        return contactList
//    }
//
//    private fun fetchContactsFromProvider() {
//        viewModelScope.launch {
//            val response = liveData(Dispatchers.IO) {
//                emit(contactProviderRepository.getContactFromProvider(username))
//            }
//            response?.let {
//                contactList = it as MutableLiveData<List<ContactModel>>
//            }
//            Log.d(CPVM, "fetchContactsFromProvider: Contacts Found ${contactList}, ${username}")
//
//
//
//        }
//
//    }
//
//    fun insertContactsInDB(contactList : List<ContactModel>){
//        viewModelScope.launch(Dispatchers.IO) {
//            contactModelRepository.insertAll(contactList)
//        }
//    }
//
//}

class ContactImportViewModel(private var contactProviderRepository : ContactProviderRepository) : ViewModel(){

    private var contactList  = mutableStateOf<List<ContactModel>>(emptyList())

    private var loaderState = mutableStateOf(true)

    fun intiliazeProvider(context: Context){
        contactProviderRepository = ContactProviderRepository(context,ContactProvider)
    }


    private fun intializeList(){
       try {
           viewModelScope.launch {
               withContext(Dispatchers.IO){
                   contactList.value = contactProviderRepository.getContactFromProvider("")
                   ContactLog.debugLog("LIST","initializeList : ${contactList.value}")
//                   withContext(Dispatchers.Main){
//                       if (contactList.value.size > 0)
//                           loaderState.value = false
//                   }
               }
           }
       }catch (e : Exception){
           ContactLog.errorLog("ERROR","intializeList: $e")
       }
    }



    public fun getContactList() : List<ContactModel>{
        intializeList()
        return contactList.value
    }
    public fun loaderVisibility() = loaderState.value


}