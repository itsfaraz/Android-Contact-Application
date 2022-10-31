package com.itsfrz.authentication.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsfrz.authentication.data.entities.ContactModel
import com.itsfrz.authentication.data.entities.UserPreferences
import com.itsfrz.authentication.data.repository.ContactModelRepository
import com.itsfrz.authentication.data.repository.UserPreferenceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactViewModel(
    val userPreferenceRepository: UserPreferenceRepository,
    val contactModelRepository: ContactModelRepository
) : ViewModel() {

    private val TAG: String = "TAG"

    private val _username = mutableStateOf("")

    private val _contactList = mutableStateListOf<ContactModel>()
    val contactList: List<ContactModel> = _contactList

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _showSearchBar = mutableStateOf(false)
    val showSearchBar = _showSearchBar

    private var _filteredList = mutableStateOf<ArrayList<ContactModel>>(arrayListOf())
    val filteredList: State<List<ContactModel>> = _filteredList

    fun setupPreferences(userPreferences: UserPreferences) {
        _username.value = userPreferences.username
    }

    fun fetchContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            if (_username.value.isNotEmpty()) {
                _contactList.addAll(contactModelRepository.allContacts(_username.value))
            }
        }
    }

    public fun reloadContactList() {
        _contactList.clear()
        fetchContacts()
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
            val temp = arrayListOf<ContactModel>()
            temp.addAll(filteredContactList)
            _filteredList.value = temp
        } else {
            _filteredList.value = arrayListOf()
        }
    }

    fun toggleSearchBar(value: Boolean) {
        _showSearchBar.value = value
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            val userPreferences = UserPreferences("", "", "", "", false)
            userPreferenceRepository.saveUserPref(userPreferences)
        }
    }

    fun updateContact(index: Int) {

    }

    fun removeContact(index: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            if (searchQuery.value.isNotEmpty() && filteredList.value.isNotEmpty()){
                val removedContact = _filteredList.value.removeAt(index)
                contactModelRepository.deleteContact(removedContact)
            }else{
                val removedContact = _contactList.removeAt(index)
                contactModelRepository.deleteContact(removedContact)
            }
        }
    }




}
