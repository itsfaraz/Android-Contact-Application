package com.itsfrz.authentication.database.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ContactModel(

    var contactName : String,
    var contactNumber : String,
    var hasContactImage : Boolean,
    var contactImage : String,
    var contactAddress : String,
    var contactEmailId : String,
    var contactCountry : String,
    var contactPostCode : String,
    @PrimaryKey
    var username : String
)