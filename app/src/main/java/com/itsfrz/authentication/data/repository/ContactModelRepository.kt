package com.itsfrz.authentication.data.repository

import androidx.lifecycle.LiveData
import com.itsfrz.authentication.model.database.room.dao.ContactDao
import com.itsfrz.authentication.data.entities.ContactModel

class ContactModelRepository(val contactDao : ContactDao) {

    fun allContacts(username : String) : LiveData<List<ContactModel>> = contactDao.getContact(username)

    suspend fun deleteContact(contactModel: ContactModel) = contactDao.deleteContact(contactModel)

    suspend fun insertContact(contactModel: ContactModel) = contactDao.insertContact(contactModel)

    suspend fun updateContact(contactModel: ContactModel) = contactDao.updateContact(contactModel)

    suspend fun insertAll(contactList : List<ContactModel>) = contactDao.insertAll(contactList)

    suspend fun deleteAllContacts(username : String) = contactDao.deleteAllContacts(username)

    suspend fun deleteSpecificUserContact(currentUser: String, contact: String) = contactDao.deleteSpecificUserContact(currentUser,contact)

}