package com.itsfrz.authentication.data.entities


enum class ContactSorting {
    BY_NAME_ASC,
    BY_NAME_DESC,
    BY_NUMBER_ASC,
    BY_NUMBER_DESC,
}

data class UserPreferences(
    val username: String,
    val loggedInDate: String,
    val contactSortingType: String,
    val appTheme: String,
    val isLoggedIn : Boolean
)