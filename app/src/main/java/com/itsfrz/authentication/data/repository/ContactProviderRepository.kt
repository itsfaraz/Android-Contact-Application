package com.itsfrz.authentication.data.repository

import android.content.Context
import com.itsfrz.authentication.data.entities.ContactModel
import com.itsfrz.authentication.data.indatabase.repository.ContactProviderI

class ContactProviderRepository(val context: Context,private val contactProviderI: ContactProviderI) {


    suspend fun getContactFromProvider(username : String) : List<ContactModel> = contactProviderI.getContactListFromProvider(context,username)

    suspend fun insertContactInProvider(contact: ContactModel) = contactProviderI.insertContactInProvider(context,contact)

    suspend fun updateContactInProvider(contact: ContactModel) = contactProviderI.updateContactInProvider(context,contact)

    suspend fun deleteContactInProvider(contact: ContactModel) = contactProviderI.deleteContactInProvider(context,contact)


}