package com.itsfrz.authentication.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.itsfrz.authentication.R

class ContactDetailFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contact_detail, container, false)
        val textNameView = view.findViewById<EditText>(R.id.personNameDetail)
        val imagePerson = view.findViewById<ImageView>(R.id.personImageDetail)
        val textNumberView = view.findViewById<EditText>(R.id.personPhoneNumberDetail)
        val textAddressView = view.findViewById<EditText>(R.id.personAddressDetail)
        val textEmailAddressView = view.findViewById<EditText>(R.id.personEmailAddressDetail)
        val personName : String = arguments?.getString("ContactName") ?: ""
        val personNumber : String = arguments?.getString("ContactNumber") ?: ""
        val personImage : String = arguments?.getString("ContactImage") ?: ""
        val personEmailID : String = arguments?.getString("ContactEmailId") ?: ""
        val personAddress : String = arguments?.getString("ContactAddress") ?: ""

        textNameView.setText(personName)
        imagePerson.setImageURI(Uri.parse(personImage))
        textNumberView.setText(personNumber)
        textAddressView.setText(personAddress)
        textEmailAddressView.setText(personEmailID)

        return view
    }


}