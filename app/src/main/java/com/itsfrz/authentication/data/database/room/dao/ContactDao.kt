package com.itsfrz.authentication.model.database.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.itsfrz.authentication.model.database.room.model.ContactModel

@Dao
interface ContactDao {

    @Query("SELECT * FROM CONTACTMODEL WHERE username=:username")
    fun getContact(username : String) : LiveData<List<ContactModel>>

    @Insert
    suspend fun insertContact(contact : ContactModel)

    @Update
    suspend fun updateContact(contact: ContactModel)

    @Delete
    suspend fun deleteContact(contactModel: ContactModel)

    @Transaction
    @Insert
    suspend fun insertAll(contacts : List<ContactModel>)



}