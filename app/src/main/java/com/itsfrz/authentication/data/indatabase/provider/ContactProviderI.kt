package com.itsfrz.authentication.data.indatabase.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.itsfrz.authentication.data.indatabase.model.Contact


interface ContactProviderI {

    suspend fun getContactListFromProvider(context: Context) : List<Contact>

    suspend fun insertContactInProvider(context: Context, contact: Contact)

    suspend fun updateContactInProvider(context: Context,contact: Contact)

    suspend fun deleteContactInProvider(context: Context,contact: Contact)


}