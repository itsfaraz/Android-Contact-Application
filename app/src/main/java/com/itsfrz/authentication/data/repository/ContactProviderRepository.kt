package com.itsfrz.authentication.data.repository

import android.content.Context
import com.itsfrz.support.Contact
import com.itsfrz.support.provider.ContactProvider


class ContactProviderRepository(val context: Context) {


    suspend fun getContactFromProvider() : List<Contact> = ContactProvider.getContacts(context)

    suspend fun insertContactInProvider(contact: Contact) = ContactProvider.insertContact(context, contact)

    suspend fun updateContactInProvider(contact: Contact) = ContactProvider.updateContact(context, contact)

    suspend fun deleteContactInProvider(contact: Contact) : Boolean = ContactProvider.deleteContact(context, contact)


}