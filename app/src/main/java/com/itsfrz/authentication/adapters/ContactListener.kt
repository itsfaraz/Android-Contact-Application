package com.itsfrz.authentication.adapters

import com.itsfrz.authentication.data.indatabase.model.Contact

interface ContactListener {
    public fun onContactChange(list : List<Contact>) : Unit
}