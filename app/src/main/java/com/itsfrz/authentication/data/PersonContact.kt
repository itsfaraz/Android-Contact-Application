package com.itsfrz.authentication.data

import android.widget.ImageView

data class PersonContact(
    val personImage : ImageView,
    val personName : String,
    val personNumber : String,
    val personEmailId : String,
    val personAddress : String
)