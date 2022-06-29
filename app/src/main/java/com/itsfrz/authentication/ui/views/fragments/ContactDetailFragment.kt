package com.itsfrz.authentication.ui.views.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.itsfrz.authentication.R
import com.itsfrz.authentication.data.entities.ContactModel
import com.itsfrz.authentication.databinding.FragmentContactDetailBinding
import com.itsfrz.authentication.ui.viewmodel.ContactViewModel

class ContactDetailFragment : Fragment(){


    private lateinit var contactDetailDataBinding : FragmentContactDetailBinding

    private val contactViewModel by lazy {
        ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(ContactViewModel::class.java)
    }

    private lateinit var personUniqueId : String
    private lateinit var personCountry : String
    private lateinit var personPostalCode : String
    private var personHasContactImage : Boolean = false


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
        personUniqueId = arguments?.getString("ContactId") ?: ""
        personCountry = arguments?.getString("ContactCountry") ?: ""
        personPostalCode = arguments?.getString("ContactPostCode") ?: ""
        personHasContactImage = arguments?.getBoolean("hasContactImage") ?: false
        contactDetailDataBinding.also {
            it.personNameDetail.setText(personName)
            it.personImageDetail.setImageURI(Uri.parse(personImage))
            it.personPhoneNumberDetail.setText(personNumber)
            it.personAddressDetail.setText(personAddress)
            it.personEmailAddressDetail.setText(personEmailID)


        }

        updateContact()

        return contactDetailDataBinding.root
    }

    private fun updateContact() {
        contactDetailDataBinding.updateDetailContactButton.setOnClickListener{
            val personNameDetail : String = contactDetailDataBinding.personNameDetail.text.toString()
            val personImageDetail : String = contactDetailDataBinding.personImageDetail.toString()
            val personPhoneNumberDetail : String = contactDetailDataBinding.personPhoneNumberDetail.text.toString()
            val personAddressDetail : String = contactDetailDataBinding.personAddressDetail.text.toString()
            val personEmailId : String = contactDetailDataBinding.personEmailAddressDetail.text.toString()

            contactViewModel.updateUserContact(ContactModel(
                personUniqueId,
                personNameDetail,
                personPhoneNumberDetail,
                personHasContactImage,
                personImageDetail,
                personAddressDetail,
                personEmailId,
                personCountry,
                personPostalCode,
                ""
            ))
            backToPreviousFragment()
        }

    }

    private fun backToPreviousFragment() {
        val fragmenManager = requireActivity().supportFragmentManager
        fragmenManager.popBackStack()
    }


}