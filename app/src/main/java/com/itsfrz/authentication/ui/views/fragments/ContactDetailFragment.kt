package com.itsfrz.authentication.ui.views.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.itsfrz.authentication.R
import com.itsfrz.authentication.databinding.FragmentContactDetailBinding

class ContactDetailFragment : Fragment() {


    private lateinit var contactDetailDataBinding : FragmentContactDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        contactDetailDataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_contact_detail,container,false)
        val personName : String = arguments?.getString("ContactName") ?: ""
        val personNumber : String = arguments?.getString("ContactNumber") ?: ""
        val personImage : String = arguments?.getString("ContactImage") ?: ""
        val personEmailID : String = arguments?.getString("ContactEmailId") ?: ""
        val personAddress : String = arguments?.getString("ContactAddress") ?: ""


        contactDetailDataBinding.also {
            it.personNameDetail.setText(personName)
            it.personImageDetail.setImageURI(Uri.parse(personImage))
            it.personPhoneNumberDetail.setText(personNumber)
            it.personAddressDetail.setText(personAddress)
            it.personEmailAddressDetail.setText(personEmailID)

        }

        return contactDetailDataBinding.root
    }



}