package com.itsfrz.authentication.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsfrz.authentication.data.entities.ContactModel
import com.itsfrz.authentication.data.entities.mapper.ContactModelMapper
import com.itsfrz.authentication.data.repository.ContactModelRepository
import com.itsfrz.authentication.data.repository.ContactProviderRepository
import com.itsfrz.support.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddContactViewModel(
    private val contactModelRepository: ContactModelRepository,
    private val contactProviderRepository: ContactProviderRepository
) : ViewModel() {

    private val _contactImage = mutableStateOf("")
    val contactImage : State<String> = _contactImage

    private val _contactName = mutableStateOf("")
    val contactName : State<String> = _contactName


    private val _contactNumber = mutableStateOf("")
    val contactNumber : State<String> = _contactNumber

    private val _contactEmail = mutableStateOf("")
    val contactEmail : State<String> = _contactEmail

    private val _contactAddress = mutableStateOf("")
    val contactAddress : State<String> = _contactAddress

    private val _operationType = mutableStateOf("Add")
    val operationType : State<String> = _operationType

    private val _username = mutableStateOf("")
    val username : State<String> = _username


    fun updateUpcomingInfo(
        contactPhoto : String,
        contactName : String,
        contactNumber : String,
        contactAddress : String,
        contactEmail : String
    ){
      setContactAddress(contactAddress)
      setContactPhoto(contactPhoto)
      setContactName(contactName)
      setContactEmail(contactEmail)
      setContactNumber(contactNumber)

    }


    fun setContactName(contactName: String){
        _contactName.value = contactName
    }


    fun setContactPhoto(contactPhoto: String){
        _contactImage.value = contactPhoto
    }

    fun setContactNumber(contactNumber: String){
        _contactNumber.value = contactNumber
    }

    fun setContactAddress(contactAddress: String){
        _contactAddress.value = contactAddress
    }

    fun setContactEmail(contactEmail: String){
        _contactEmail.value = contactEmail
    }

    fun setOperationType(operationType: String){
        _operationType.value = operationType
    }

    fun setUsername(username: String){
        _username.value = username
    }


    fun insertContact(){
        viewModelScope.launch(Dispatchers.IO) {
            contactModelRepository.insertContact(
                ContactModel(
                    contactId = "",
                    contactName = contactName.value,
                    contactNumber = contactNumber.value,
                    contactAddress = contactAddress.value,
                    contactEmailId = contactEmail.value,
                    hasContactImage = false,
                    contactImage = "",
                    contactCountry = "",
                    contactPostCode = "",
                    username = _username.value
                )
            )
        }
    }

    fun updateContact(){
        viewModelScope.launch(Dispatchers.IO) {
            val contact = ContactModel(
                contactId = "",
                contactName = _contactName.value,
                contactNumber = _contactNumber.value,
                contactAddress = _contactAddress.value,
                contactEmailId = _contactEmail.value,
                hasContactImage = false,
                contactImage = "",
                contactCountry = "",
                contactPostCode = "",
                username = _username.value
            )
            contactModelRepository.updateContact(contact)
        }
    }

    fun insertContactInProvider(){
        viewModelScope.launch(Dispatchers.IO) {
            val contact =  Contact(
                contactId = "",
                contactName = _contactName.value,
                contactNumber = _contactNumber.value,
                contactImage = "",
                contactThumbnailImage = "",
                contactOrganization = "",
                contactJobTitle = "",
                contactAddress = _contactAddress.value,
                contactWebAddress = "",
                contactEmailId = _contactEmail.value,
                contactCountry = "",
                contactPostalCode = ""
            )
        }
    }

    fun updateContactInProvider(){
        viewModelScope.launch(Dispatchers.IO) {
            contactProviderRepository.updateContactInProvider(
                Contact(
                    contactId = "",
                    contactName = _contactName.value,
                    contactNumber = _contactNumber.value,
                    contactImage = "",
                    contactThumbnailImage = "",
                    contactOrganization = "",
                    contactJobTitle = "",
                    contactAddress = _contactAddress.value,
                    contactWebAddress = "",
                    contactEmailId = _contactEmail.value,
                    contactCountry = "",
                    contactPostalCode = ""
                )
            )
        }
    }


}