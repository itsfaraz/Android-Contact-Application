package com.itsfrz.authentication.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsfrz.authentication.data.entities.ContactModel
import com.itsfrz.authentication.data.repository.ContactProviderRepository
import com.itsfrz.authentication.data.utils.ContactLog
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

class ContactImportViewModel(private var contactProviderRepository: ContactProviderRepository) :
    ViewModel() {

    private val _contactList = mutableStateOf<List<ContactModel>>(listOf())
    val contactList: State<List<ContactModel>> = _contactList

    private val _isProgress = mutableStateOf(true)
    val isProgress: State<Boolean> = _isProgress


    private val _selectedContactList = mutableStateOf<ArrayList<ContactModel>>(arrayListOf())




    init {
        intializeList()
    }

    private fun intializeList() {
        try {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    _isProgress.value = true
                    _contactList.value = contactProviderRepository.getContactFromProvider("")
                    ContactLog.debugLog("LIST", "intializeList : ${_contactList.value}")
                    _isProgress.value = false
                }
                ContactLog.debugLog("LIST", "initializeList : ${_contactList.value}")
            }
        } catch (e: Exception) {
            ContactLog.errorLog("ERROR", "intializeList: $e")
        }
    }

    public fun addContact(contactModel: ContactModel) = _selectedContactList.value.add(contactModel)
}