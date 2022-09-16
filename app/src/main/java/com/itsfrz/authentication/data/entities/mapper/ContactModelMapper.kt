package com.itsfrz.authentication.data.entities.mapper

import androidx.room.Transaction
import com.itsfrz.authentication.data.entities.ContactModel
import com.itsfrz.support.Contact


object ContactModelMapper{

    var username : String = ""

    @Transaction
    fun transformContactToContactModel(contact : Contact) : ContactModel{
        return ContactModel(
            contactId = contact.contactId,
            contactName = contact.contactName,
            contactNumber = contact.contactNumber,
            hasContactImage = getImageBoolean(contact.contactThumbnailImage),
            contactImage = contact.contactImage,
            contactAddress = contact.contactAddress,
            contactEmailId = contact.contactEmailId,
            contactCountry = contact.contactCountry,
            contactPostCode = contact.contactPostalCode,
            username = username,
        )
    }


    private fun getImageBoolean(contactThumbnailImage: String): Boolean =
        contactThumbnailImage.isNotBlank() && contactThumbnailImage.isNotEmpty()


    @Transaction
    fun transformContactModelToContact(contact : ContactModel) : Contact{
        return Contact(
            contactId = contact.contactId,
            contactName = contact.contactName,
            contactNumber = contact.contactNumber,
            contactImage = "",
            contactThumbnailImage = contact.contactImage,
            contactOrganization = "",
            contactJobTitle = "",
            contactAddress = contact.contactAddress,
            contactWebAddress = "",
            contactEmailId = contact.contactEmailId,
            contactCountry = contact.contactCountry,
            contactPostalCode = contact.contactPostCode
        )
    }




}