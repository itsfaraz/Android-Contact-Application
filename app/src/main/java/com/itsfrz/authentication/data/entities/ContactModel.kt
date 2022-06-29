package com.itsfrz.authentication.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ContactModel(
    @PrimaryKey
    var contactId : String,
    var contactName : String,
    var contactNumber : String,
    var hasContactImage : Boolean,
    var contactImage : String,
    var contactAddress : String,
    var contactEmailId : String,
    var contactCountry : String,
    var contactPostCode : String,
    var username : String
)