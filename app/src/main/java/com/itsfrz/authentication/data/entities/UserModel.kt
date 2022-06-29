package com.itsfrz.authentication.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserModel(
    @PrimaryKey
    val username : String,
    val emailId : String,
    val password : String,


)