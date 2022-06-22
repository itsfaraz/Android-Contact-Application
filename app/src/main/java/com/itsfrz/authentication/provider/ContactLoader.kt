package com.itsfrz.authentication.provider

import android.content.Context
import androidx.lifecycle.LiveData
import com.itsfrz.authentication.model.Contact

class ContactLoader() {
     fun fetchContacts(requireContext: Context): List<Contact> {
        return ContactProvider.getContactList(requireContext)
    }
}