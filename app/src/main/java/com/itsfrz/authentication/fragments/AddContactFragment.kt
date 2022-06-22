package com.itsfrz.authentication.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.itsfrz.authentication.AuthenticationCommunicator
import com.itsfrz.authentication.R
import com.itsfrz.authentication.adapters.PhoneTypeArrayAdapter
import com.itsfrz.authentication.model.PhoneTypes

class AddContactFragment : Fragment() {


    private lateinit var communicator: AuthenticationCommunicator
    private val GALLERY_REQUEST_CODE = 0

    private lateinit var personImageHolder : ImageView
    private lateinit var personName: EditText
    private lateinit var personEmailAddress: EditText
    private lateinit var personAddress: EditText
    private lateinit var personNumber: EditText
    private lateinit var spinner: Spinner
    private lateinit var addContactButton : Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        hideTaskBar()
        val view = inflater.inflate(R.layout.add_contact_layout,container,false);
        communicator = activity as AuthenticationCommunicator
        initViews(view);
        setupPhoneTypeSpinner()
        openGallery()
        onContactAddClick();

        return view
    }

    private fun hideTaskBar() {

    }

    private fun onContactAddClick() {
        addContactButton.setOnClickListener {
            if(validateInput(personName, personAddress, personEmailAddress, personNumber)){
                Toast.makeText(requireContext(), "Hello World", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initViews(view : View) {
        personImageHolder = view.findViewById(R.id.personContactImage)
        personName = view.findViewById(R.id.personName)
        personNumber = view.findViewById(R.id.personPhoneNumber)
        personEmailAddress = view.findViewById(R.id.personEmailAddress)
        personAddress = view.findViewById(R.id.personAddress)
        spinner = view.findViewById(R.id.phoneTypeSpinner)
        addContactButton = view.findViewById(R.id.addNewContactButton)
    }


    private fun validateInput(personName: EditText, personAddress: EditText, personEmailAddress: EditText, personNumber: EditText): Boolean {
        val personNameString : String = personName.text.toString().trim()
        val personAddressString : String = personAddress.text.toString().trim()
        val personEmailAddressString : String = personEmailAddress.text.toString().trim()
        val personNumberString : String = personNumber.text.toString().trim()

        if (personNameString.length <= 2){
            personName.setError("Person Name should be in 3 or more character")
            return false
        }
        if (personAddressString.length <= 9){
            personAddress.setError("Person Address should be in 10 or more character")
            return false
        }
        if (personEmailAddressString.length <= 5){
            personEmailAddress.setError("Person Email should be in 6 or more character")
            return false
        }

        if (personNumberString.length <= 9){
            personNumber.setError("Person Numder should be 10 or more character")
            return false
        }
        return true
    }

    private fun openGallery() {
        personImageHolder.setOnClickListener {
            val iGallery = Intent(Intent.ACTION_PICK)
            iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(iGallery,GALLERY_REQUEST_CODE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == GALLERY_REQUEST_CODE){
                personImageHolder.setImageURI(data?.data)
            }


        }
    }

    private fun setupPhoneTypeSpinner() {
        val phoneTypeAdapter = PhoneTypeArrayAdapter(requireContext(), PhoneTypes.phoneTypeList!!)
        spinner.adapter = phoneTypeAdapter
    }
}