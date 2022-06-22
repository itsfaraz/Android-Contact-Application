package com.itsfrz.authentication.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

object LocalDatabaseConfiguration{

    val DB_NAME = "User.db"
    val DB_VERSION = 1
}

class LocalDatabaseHelper(val context: Context) : SQLiteOpenHelper(
    context,
    LocalDatabaseConfiguration.DB_NAME,
    null,
    LocalDatabaseConfiguration.DB_VERSION

) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(UserTable.CMD_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}