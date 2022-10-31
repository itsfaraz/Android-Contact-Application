package com.itsfrz.authentication.data.entities

import android.os.Parcel
import android.os.Parcelable


enum class ContactSorting {
    BY_NAME_ASC,
    BY_NAME_DESC,
    BY_NUMBER_ASC,
    BY_NUMBER_DESC,
}

data class UserPreferences(
    val username: String = "",
    val loggedInDate: String = "",
    val contactSortingType: String = "",
    val appTheme: String = "",
    val isLoggedIn : Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(username)
        parcel.writeString(loggedInDate)
        parcel.writeString(contactSortingType)
        parcel.writeString(appTheme)
        parcel.writeByte(if (isLoggedIn) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserPreferences> {
        override fun createFromParcel(parcel: Parcel): UserPreferences {
            return UserPreferences(parcel)
        }

        override fun newArray(size: Int): Array<UserPreferences?> {
            return arrayOfNulls(size)
        }
    }
}