package com.itsfrz.authentication.provider

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import com.itsfrz.authentication.data.entities.ContactModel
import com.itsfrz.authentication.data.indatabase.repository.ContactProviderI
import com.itsfrz.authentication.data.utils.ContactLog

object ContactProvider : ContactProviderI {


    private val EXCEPTION: String = "EXCEPTION"
    private val DEBUG: String = "DEBUG"

    private val mColumnProjectionsForPostal = arrayOf<String>(
        ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS,
        ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE,
        ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY
    )

    private val mColumnProjections = arrayOf<String>(
        ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
        ContactsContract.CommonDataKinds.Photo.PHOTO_THUMBNAIL_URI,
        ContactsContract.CommonDataKinds.Email.ADDRESS,

        )


    override suspend fun getContactListFromProvider(
        context: Context,
        username: String
    ): List<ContactModel> {

        val contentResolver: ContentResolver = context.contentResolver
        val contactList = ArrayList<ContactModel>()


        val cursor: Cursor? = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            mColumnProjections,
            null,
            null,
            null
        )

        try {
            cursor?.let {
                if (it.count > 0) {
                    while (it.moveToNext()) {
                        val contact: ContactModel =
                            ContactModel("", "", "", false, "", "", "", "", "", username)
                        val contactId: String = it.getString(0)
                        val contactName = it.getString(1)
                        val contactNumber = it.getString(2)
                        val contactPhotoUri = it.getString(3)


                        if (contactPhotoUri == null) {
                            contact.apply {
                                this.contactId = contactId
                                this.contactImage = ""
                                this.contactNumber = contactNumber
                                this.hasContactImage = false
                                this.contactName = contactName
                            }

                        } else {
                            contact.apply {
                                this.contactId = contactId
                                this.contactImage = ""
                                this.contactImage = contactPhotoUri
                                this.contactNumber = contactNumber
                                this.hasContactImage = true
                                this.contactName = contactName
                            }

                        }

                        val emailContact: ContactModel =
                            getEmailId(context, contact, contactId, contactName)

                        if (emailContact != null) {
                            contact.contactEmailId = emailContact.contactEmailId
                        }
                        val addressContact: ContactModel =
                            getAddress(context, contact, contactId, contactName)
                        if (addressContact != null) {
                            contact.contactAddress = addressContact.contactAddress
                            contact.contactCountry = addressContact.contactCountry
                            contact.contactPostCode = addressContact.contactPostCode
                        }

                        contactList.add(contact)
                    }
                    it.close()
                }
            }
        } catch (e: Exception) {
            ContactLog.errorLog(EXCEPTION, "getContactList: $e")
        } finally {
            if (cursor != null) {
                cursor.close()
            }
        }



        return contactList
    }

    private fun getAddress(
        context: Context,
        contact: ContactModel,
        contactId: String,
        contactName: String
    ): ContactModel {
        val whereClause =
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + "=? and " + ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?"
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
            mColumnProjectionsForPostal,
            whereClause,
            arrayOf<String>(contactName, contactId),
            null
        )

        var address: String = ""
        var pincode: String = ""
        var country: String = ""
        if (cursor != null && cursor.count > 0) {
            try {
                while (cursor.moveToNext()) {
                    address = cursor.getString(0)
                    pincode = cursor.getString(1) ?: ""
                    country = cursor.getString(2)
                }
            } catch (e: Exception) {
                ContactLog.errorLog(EXCEPTION, "getAddress: ${e}")
            } finally {
                if (cursor != null)
                    cursor.close()
            }
        }

        contact.contactAddress = address
        contact.contactCountry = country
        contact.contactPostCode = pincode
//        Log.d("ADDRESS", "getAddress: $contact")
        return contact
    }

    private fun getEmailId(
        context: Context,
        contact: ContactModel,
        contactId: String,
        contactName: String
    ): ContactModel {
        val whereClause =
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + "=? and " + ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?"

        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
            arrayOf<String>(ContactsContract.CommonDataKinds.Email.ADDRESS),
            whereClause,
            arrayOf<String>(contactName, contactId),
            null
        )

        var emailId: String = ""
        if (cursor != null && cursor.count > 0) {
            try {
                while (cursor.moveToNext()) {
                    emailId = cursor.getString(0)
                }
            } catch (e: Exception) {
                ContactLog.errorLog(EXCEPTION, "getEmailId: $e")
            } finally {
                if (cursor != null) {
                    cursor.close()
                }
            }
        }
        contact.contactEmailId = emailId

        return contact
    }


    override suspend fun insertContactInProvider(context: Context, contact: ContactModel) {
        TODO("Not yet implemented")
    }

    override suspend fun updateContactInProvider(context: Context, contact: ContactModel) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteContactInProvider(context: Context, contact: ContactModel) {
        TODO("Not yet implemented")
    }


/*  Will Update this code very soon */

//
//    fun updateContact(context: Context, contactName : String, updatedContactName : String) {
//
//        if(contactName.length > 0){
//            val whereClause = ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY +"=?";
//            val params = arrayOf<String>(contactName)
//            val contentResolver = context.contentResolver
//            val contentValues = ContentValues()
//            contentValues.put(ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY,updatedContactName)
//            contentResolver.update(ContactsContract.RawContacts.CONTENT_URI,contentValues,whereClause,params)
//        }
//
//
//
//    }
//
//    fun deleteContact(context : Context, position: Int, contactName: String) {
//        val whereClause = ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY+"='"+contactName+"'"
//        context.contentResolver.delete(ContactsContract.RawContacts.CONTENT_URI,whereClause,null)
//        Toast.makeText(context, "$contactName has been deleted", Toast.LENGTH_SHORT).show()
//    }
//
//
//    fun createContact(context: Context, name: String, number : String) {
//        val contentValues = ContentValues()
//        val uri : Uri = context.contentResolver.insert(ContactsContract.RawContacts.CONTENT_URI,contentValues)!!
//        val id = ContentUris.parseId(uri)
//
//        val nameContentValues = ContentValues()
//        nameContentValues.put(ContactsContract.Data.MIMETYPE,ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
//        nameContentValues.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,name)
//        nameContentValues.put(ContactsContract.Data.RAW_CONTACT_ID,id)
//        context.contentResolver.insert(ContactsContract.Data.CONTENT_URI,nameContentValues)
//
//        val numberContentValue = ContentValues()
//        numberContentValue.put(ContactsContract.Data.MIMETYPE,ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
//        numberContentValue.put(ContactsContract.CommonDataKinds.Phone.NUMBER,number)
//        numberContentValue.put(ContactsContract.Data.RAW_CONTACT_ID,id)
//        context.contentResolver.insert(ContactsContract.Data.CONTENT_URI,numberContentValue)
//
//        Toast.makeText(context, "${name} is added in contacts", Toast.LENGTH_SHORT).show()
//
//    }


}