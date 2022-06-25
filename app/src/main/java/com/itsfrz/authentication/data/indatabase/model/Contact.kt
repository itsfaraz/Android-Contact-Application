package com.itsfrz.authentication.data.indatabase.model

data class Contact(
    var contactId : String,
    var contactName : String,
    var contactNumber : String,
    var hasContactImage : Boolean,
    var contactImage : String,
    var contactAddress : String,
    var contactEmailId : String,
    var contactCountry : String,
    var contactPostCode : String

)