package com.itsfrz.authentication.data.indatabase.repository

import android.content.Context
import com.itsfrz.authentication.data.entities.ContactModel


interface ContactProviderI {

    suspend fun getContactListFromProvider(context: Context,username : String) : List<ContactModel>

    suspend fun insertContactInProvider(context: Context, contact: ContactModel)

    suspend fun updateContactInProvider(context: Context,contact: ContactModel)

    suspend fun deleteContactInProvider(context: Context,contact: ContactModel)


}