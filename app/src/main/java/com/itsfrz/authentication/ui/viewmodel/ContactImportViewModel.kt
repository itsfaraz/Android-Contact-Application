package com.itsfrz.authentication.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsfrz.authentication.data.entities.ContactModel
import com.itsfrz.authentication.data.entities.mapper.ContactModelMapper
import com.itsfrz.authentication.data.repository.ContactProviderRepository
import com.itsfrz.authentication.data.utils.ContactLog
import com.itsfrz.support.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactImportViewModel
    (private var contactProviderRepository: ContactProviderRepository) : ViewModel() {


    private val _contactList = mutableStateListOf<ContactModel>()
    var contactList: List<ContactModel> = _contactList

    private val _filteredList = mutableStateOf<List<ContactModel>>(listOf())
    val filteredList: State<List<ContactModel>> = _filteredList

    private val _isProgress = mutableStateOf(true)
    val isProgress: State<Boolean> = _isProgress

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _selectedContactList = mutableStateListOf<Int>()

    val operationQueue = mutableStateOf(false)

    private val _showSearchBar = mutableStateOf(false)
    val showSearchBar = _showSearchBar

    private val _isAllSelected = mutableStateOf(false)

    private val _rotationState = mutableStateOf(false)
    val rotationState = _rotationState

    private val _showEmptyListMessage = mutableStateOf(false)
    val showEmptyListMessage: State<Boolean> = _showEmptyListMessage

    private val _contactsToBeImport: List<ContactModel>
        get() {
            val contacts = arrayListOf<ContactModel>()
            _selectedContactList.forEach { index ->
                contacts.add(_contactList[index])
            }
            return contacts
        }

    fun fetchContacts() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                _isProgress.value = true
                if (_contactList.isEmpty()) {
                    var contactList = contactProviderRepository.getContactFromProvider()
                    ContactLog.debugLog("LIST", "intializeList : ${_contactList}")
                    updateContacts(contactList)
                }
                _isProgress.value = false
                ContactLog.debugLog("LIST", "initializeList : ${_contactList}")
            }
        } catch (e: Exception) {
            ContactLog.errorLog("ERROR", "intializeList: $e")
        }
    }

    private fun updateContacts(
        contactList: List<Contact>
    ) {
        contactList.forEach {
            val contactModel = ContactModelMapper.transformContactToContactModel(it)
            _contactList.add(contactModel)
        }
        _showEmptyListMessage.value = _contactList.isEmpty()
    }

    fun toggleSearchBar(value: Boolean) {
        _showSearchBar.value = value
    }

    fun searchRequest(query: String) {
        _searchQuery.value = query
        var filteredContactList = emptyList<ContactModel>()
        if (query.isNotBlank()) {
            if (query.get(0).isDigit() || query.get(0).equals("+")) {
                filteredContactList = _contactList.filter { contact ->
                    val number = contact.contactNumber.toString()
                    number.contains(query)
                }
            }
            if (!query.get(0).isDigit()) {
                filteredContactList = _contactList.filter { contact ->
                    val name = contact.contactName.toString().lowercase()
                    name.contains(query.lowercase())
                }
            }
            _filteredList.value = filteredContactList
            _showEmptyListMessage.value = _filteredList.value.isEmpty()
        } else {
            _filteredList.value = emptyList<ContactModel>()
        }
    }

    fun addSelectedContact(index: Int) {
        if (_selectedContactList.contains(index)) {
            _selectedContactList.remove(index)
        } else _selectedContactList.add(index)
        operationQueueUpdate()
        handleRotationState()
    }

    fun checkIsPresent(index: Int): Boolean = _selectedContactList.contains(index)

    fun addAllSelectedContact() {
        if (!_isAllSelected.value) {
            _selectedContactList.addAll(0.._contactList.lastIndex)
            _isAllSelected.value = true
        } else {
            _selectedContactList.clear()
            _isAllSelected.value = false
        }
        operationQueueUpdate()
        handleRotationState()
    }

    private fun operationQueueUpdate() {
        operationQueue.value = _selectedContactList.size > 0
    }

    private fun handleRotationState() {
        if (_selectedContactList.size > 0) {
            _rotationState.value = true
        }
        if (_selectedContactList.size == 0)
            _rotationState.value = false
    }

    public fun updateContact(index: Int) {
        // For time being just add demo items
        _contactList.add(
            ContactModel("", "Demo ${(index + 100)}", "123456789", false, "", "", "", "", "", "")
        )
        Log.d("SWIPE", "updateContact: Update Contact $index")
    }


    public fun deleteContact(index: Int) {
//        Case 1 Delete from filtered list
        var deleteObject: ContactModel? = null
        if (_filteredList.value.isNotEmpty()) {
            deleteObject = _filteredList.value.get(index)
            val newList = arrayListOf<ContactModel>()
            _filteredList.value.forEach {
                if (_filteredList.value[index] != it)
                    newList.add(it)
            }
            _filteredList.value = newList
        }
        if (deleteObject == null)
            deleteObject = _contactList[index]
        //        Case 2 Delete from contact list

        viewModelScope.launch(Dispatchers.IO) {
            val result = contactProviderRepository.deleteContactInProvider(ContactModelMapper.transformContactModelToContact(deleteObject))
            _contactList.remove(deleteObject)
            if (result)
                Log.d("CONTACT_DELETE", "deleteContact: ${deleteObject.contactName} is deleted successfully")
        }

    }
}