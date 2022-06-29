package com.itsfrz.authentication.model.database.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.itsfrz.authentication.data.entities.ContactModel

@Dao
interface ContactDao {

    @Query("SELECT * FROM CONTACTMODEL WHERE username=:username")
    fun getContact(username : String) : LiveData<List<ContactModel>>

    @Insert(onConflict = REPLACE)
    suspend fun insertContact(contact : ContactModel)

    @Update
    suspend fun updateContact(contact: ContactModel)

    @Delete
    suspend fun deleteContact(contactModel: ContactModel)

    @Transaction
    @Insert(onConflict = REPLACE)
    suspend fun insertAll(contacts : List<ContactModel>)



}