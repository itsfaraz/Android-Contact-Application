package com.itsfrz.authentication.data.repository

import androidx.lifecycle.LiveData
import com.itsfrz.authentication.model.database.room.dao.ContactDao
import com.itsfrz.authentication.model.database.room.model.ContactModel

class ContactModelRepository(val contactDao : ContactDao) {

    fun allContacts(username : String) : LiveData<List<ContactModel>> = contactDao.getContact(username)

    suspend fun deleteContact(contactModel: ContactModel) = contactDao.deleteContact(contactModel)

    suspend fun insertContact(contactModel: ContactModel) = contactDao.insertContact(contactModel)

    suspend fun updateContact(contactModel: ContactModel) = contactDao.updateContact(contactModel)


}