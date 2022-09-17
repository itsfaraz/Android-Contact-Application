package com.itsfrz.authentication.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsfrz.authentication.data.entities.ContactModel
import com.itsfrz.authentication.data.entities.mapper.ContactModelMapper
import com.itsfrz.authentication.data.repository.ContactModelRepository
import com.itsfrz.authentication.data.repository.ContactProviderRepository
import com.itsfrz.authentication.data.utils.ContactLog
import com.itsfrz.support.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactImportViewModel
    (
    private var contactProviderRepository: ContactProviderRepository,
    private var contactModelRepository: ContactModelRepository
    ) : ViewModel() {

    private var _contactList = mutableStateListOf<ContactModel>()
    var contactList: List<ContactModel> = _contactList

    private var _filteredList = mutableStateOf<List<ContactModel>>(listOf())
    val filteredList: State<List<ContactModel>> = _filteredList

    private val _isProgress = mutableStateOf(true)
    val isProgress: State<Boolean> = _isProgress

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery


    val operationQueue = mutableStateOf(false)

    private val _showSearchBar = mutableStateOf(false)
    val showSearchBar = _showSearchBar


    private val _rotationState = mutableStateOf(false)
    val rotationState = _rotationState

    private val _showEmptyListMessage = mutableStateOf(false)
    val showEmptyListMessage: State<Boolean> = _showEmptyListMessage

    private var _selectedContacts = mutableStateListOf<ContactModel>()
    val selectedContacts: List<ContactModel> = _selectedContacts

    private val _alertDialogState = mutableStateOf(false)
    val alertDialogState: State<Boolean> = _alertDialogState

    fun toggleAlertDialog() {
        _alertDialogState.value = !_alertDialogState.value
    }

    fun selectAllContacts() {
        if (_selectedContacts.isEmpty()) {
            contactList.forEach {
                addContactSelection(it)
            }
        } else if (_selectedContacts.size < _contactList.size && _selectedContacts.isNotEmpty()) {
            _selectedContacts.clear()
            contactList.forEach {
                addContactSelection(it)
            }
        } else
            _selectedContacts.clear()

        operationQueue.value = _selectedContacts.isNotEmpty()
        rotationState.value = _selectedContacts.isNotEmpty()

    }

    fun addContactSelection(
        contactModel: ContactModel
    ) {
        if (!_selectedContacts.contains(contactModel)) {
            _selectedContacts.add(contactModel)
        }

        operationQueue.value = _selectedContacts.isNotEmpty()
        rotationState.value = _selectedContacts.isNotEmpty()
    }

    fun removeContactSelection(
        index: Int
    ) {

        val convertedIndex = ((_selectedContacts.size-index)-1);
        ContactLog.debugLog("CHECK_INDEX", "removeContactSelection: Reversed List Index :- ${index}, Converted Index :- ${convertedIndex}, List Size :- ${_selectedContacts.size}")

        _selectedContacts.removeAt(convertedIndex)
        operationQueue.value = _selectedContacts.isNotEmpty()
        rotationState.value = _selectedContacts.isNotEmpty()
    }

    fun deleteContactsSelection() {
        val deletionObjects = arrayListOf<Contact>()
        if (_selectedContacts.isNotEmpty()) {
            _selectedContacts.forEach { contactModel ->
                deletionObjects.add(ContactModelMapper.transformContactModelToContact(contactModel))
            }
            deleteBatchContacts(deletionObjects)
        }
        _rotationState.value = _selectedContacts.isEmpty()
        operationQueue.value = _selectedContacts.isEmpty()
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
            _showEmptyListMessage.value = false
        }
    }


    public fun deleteContact(index: Int) {
        val deleteObject = _contactList[index]
        deleteContact(deleteObject)
    }


    private fun deleteBatchContacts(contacts : List<Contact>){
        viewModelScope.launch(Dispatchers.IO) {
            _isProgress.value = true
            val result = contactProviderRepository.deleteContactsInProvider(contacts)
            Log.d("DELETE_CONTACT_LIST", "deleteBatchContacts: Result :: ${result}")
            contacts.forEach { contact ->
                val deleteContact = ContactModelMapper.transformContactToContactModel(contact)
                _selectedContacts.remove(deleteContact)
                _contactList.remove(deleteContact)
                _filteredList.value = emptyList()
            }

            _isProgress.value = false

        }
    }

    private fun deleteContact(deleteObject: ContactModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = contactProviderRepository.deleteContactInProvider(
                ContactModelMapper.transformContactModelToContact(deleteObject)
            )
            _contactList.remove(deleteObject)
            if (result)
                Log.d(
                    "CONTACT_DELETE",
                    "deleteContact: ${deleteObject.contactName} is deleted successfully"
                )
        }
    }


    fun importSelectedContacts(){
        viewModelScope.launch{
            contactModelRepository.insertAll(_selectedContacts)
        }
    }

}