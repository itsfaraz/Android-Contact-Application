package com.itsfrz.authentication.data.repository

import android.content.Context
import com.itsfrz.authentication.data.indatabase.model.Contact
import com.itsfrz.authentication.data.indatabase.repository.ContactProviderI

class ContactProviderRepository(val context: Context,private val contactProviderI: ContactProviderI) {


    suspend fun getContactFromProvider() : List<Contact> = contactProviderI.getContactListFromProvider(context)

    suspend fun insertContactInProvider(contact: Contact) = contactProviderI.insertContactInProvider(context,contact)

    suspend fun updateContactInProvider(contact: Contact) = contactProviderI.updateContactInProvider(context,contact)

    suspend fun deleteContactInProvider(contact: Contact) = contactProviderI.deleteContactInProvider(context,contact)


}