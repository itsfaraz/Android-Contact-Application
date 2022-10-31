package com.itsfrz.authentication.model.database.room.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.itsfrz.authentication.data.entities.ContactModel

@Dao
interface ContactDao {

    @Query("SELECT * FROM CONTACTMODEL WHERE username=:username")
    fun getContact(username: String): List<ContactModel>

    @Insert(onConflict = REPLACE)
    suspend fun insertContact(contact: ContactModel)

    @Transaction
    @Update
    suspend fun updateContact(contact: ContactModel)

    @Delete
    suspend fun deleteContact(contactModel: ContactModel)

    @Transaction
    @Insert(onConflict = REPLACE)
    suspend fun insertAll(contacts: List<ContactModel>)

    @Transaction
    @Query("DELETE FROM ContactModel WHERE username=:username")
    suspend fun deleteAllContacts(username: String)

    @Query("DELETE FROM ContactModel WHERE (username=:currentUser) AND (contactNumber=:contactNumber)")
    suspend fun deleteSpecificUserContact(currentUser: String, contactNumber: String)


}