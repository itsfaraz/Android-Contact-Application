package com.itsfrz.authentication.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsfrz.authentication.data.entities.ContactModel
import com.itsfrz.authentication.data.repository.ContactProviderRepository
import com.itsfrz.authentication.data.utils.ContactLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactImportViewModel
    (private var contactProviderRepository: ContactProviderRepository) : ViewModel() {

    private val _contactList = mutableStateOf<List<ContactModel>>(listOf())
    var contactList: State<List<ContactModel>> = _contactList

    private val _filteredList = mutableStateOf<List<ContactModel>>(listOf())
    val filteredList: State<List<ContactModel>> = _filteredList

    private val _isProgress = mutableStateOf(true)
    val isProgress: State<Boolean> = _isProgress

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _selectedContactList = mutableStateOf<ArrayList<ContactModel>>(arrayListOf())

    private val _showSearchBar = mutableStateOf(false)
    val showSearchBar = _showSearchBar

    fun fetchContacts() {
        try {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
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

    fun toggleSearchBar(value: Boolean) {
        _showSearchBar.value = value
    }

    fun searchRequest(query: String) {
        _searchQuery.value = query
        var filteredContactList = emptyList<ContactModel>()
        if (query.isNotBlank()) {
            if (query.get(0).isDigit() || query.get(0).equals("+")) {
                filteredContactList =  _contactList.value.filter { contact ->
                    val number = contact.contactNumber.toString()
                    number.contains(query)
                }
            }
            if (!query.get(0).isDigit()) {
                filteredContactList  =  _contactList.value.filter { contact ->
                    val name = contact.contactName.toString().lowercase()
                    name.contains(query.lowercase())
                }
            }
            _filteredList.value = filteredContactList
            Log.d("FILTERED_LIST", "searchRequest: ${_filteredList.value}")
        }
    }

}