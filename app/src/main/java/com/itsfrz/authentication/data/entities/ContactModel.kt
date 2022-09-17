package com.itsfrz.authentication.data.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ContactModel(
    @PrimaryKey
    var contactId: String,
    var contactName: String,
    var contactNumber: String,
    var hasContactImage: Boolean,
    var contactImage: String,
    var contactAddress: String,
    var contactEmailId: String,
    var contactCountry: String,
    var contactPostCode: String,
    var username: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(contactId)
        parcel.writeString(contactName)
        parcel.writeString(contactNumber)
        parcel.writeByte(if (hasContactImage) 1 else 0)
        parcel.writeString(contactImage)
        parcel.writeString(contactAddress)
        parcel.writeString(contactEmailId)
        parcel.writeString(contactCountry)
        parcel.writeString(contactPostCode)
        parcel.writeString(username)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ContactModel> {
        override fun createFromParcel(parcel: Parcel): ContactModel {
            return ContactModel(parcel)
        }

        override fun newArray(size: Int): Array<ContactModel?> {
            return arrayOfNulls(size)
        }
    }
}