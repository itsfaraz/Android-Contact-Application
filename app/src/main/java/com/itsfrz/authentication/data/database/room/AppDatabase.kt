package com.itsfrz.authentication.model.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.itsfrz.authentication.model.database.room.dao.ContactDao
import com.itsfrz.authentication.model.database.room.dao.UserDao
import com.itsfrz.authentication.data.entities.ContactModel
import com.itsfrz.authentication.data.entities.UserModel

private const val DB_NAME = "Contact.db"

@Database(entities = [UserModel::class, ContactModel::class], version = 1)
abstract class AppDatabase : RoomDatabase(){

    abstract fun userDao(): UserDao
    abstract fun contactDao(): ContactDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null)
                return tempInstance
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context, AppDatabase::class.java, DB_NAME
                ).build()
                INSTANCE = instance
                return instance
            }

        }

    }


}