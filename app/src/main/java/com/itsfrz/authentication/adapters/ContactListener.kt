package com.itsfrz.authentication.adapters

import com.itsfrz.authentication.data.entities.ContactModel

interface ContactListener {
    public fun onContactChange(list : List<ContactModel>) : Unit
}