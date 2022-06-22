package com.itsfrz.authentication

import android.Manifest
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import com.itsfrz.authentication.database.PreferenceRespository
import com.itsfrz.authentication.fragments.*
import com.itsfrz.authentication.model.Contact

class MainActivity : BaseActivity() ,AuthenticationCommunicator {
    private val preferenceRespository by lazy {
        PreferenceRespository(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FF70C0FF")))
        actionBar?.hide()


        val permission = requestRunTimePermission(this, arrayOf(Manifest.permission.READ_CONTACTS),READ_CONTACT_RQ)
        if(permission){
            initFragment()
        }

        val permissionButton = findViewById<Button>(R.id.permissionButton)
        permissionButton.setOnClickListener {
            if(requestRunTimePermission(this, arrayOf(Manifest.permission.READ_CONTACTS),READ_CONTACT_RQ))
                initFragment()
        }



    }


    private fun initFragment() {

        lateinit var initFragment : Fragment
        if (preferenceRespository.getLoggedIn()){
            initFragment = ContactFragment()
//            initFragment = ContactImportFragment()
        }else{
            initFragment = LoginFragment()
        }
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer,initFragment)
        fragmentTransaction.commit()
    }

    override fun routeToLogin(username: String) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val loginFragment = LoginFragment()
        val bundle = Bundle()
        bundle.putString("username",username)
        loginFragment.arguments = bundle
        fragmentTransaction.replace(R.id.fragmentContainer,loginFragment)
        fragmentTransaction.commit()
    }

    override fun routeToSignUp() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val signUpFragment = SignUpFragment()
        fragmentTransaction.replace(R.id.fragmentContainer,signUpFragment)
        fragmentTransaction.commit()
    }

    override fun routeFromLoginToLandingPage() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val landingFragment = LandingFragment()
        fragmentTransaction.replace(R.id.fragmentContainer,landingFragment)
        fragmentTransaction.commit()
    }

    override fun routerFromLoginToContactPage() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val contactFragment = ContactFragment()
        fragmentTransaction.replace(R.id.fragmentContainer,contactFragment)
        fragmentTransaction.commit()
    }

    override fun routerFromLoginToContactImportPage() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val contactImportFragment = ContactImportFragment()
        fragmentTransaction.replace(R.id.fragmentContainer,contactImportFragment)
        fragmentTransaction.commit()
    }

    override fun routeFromContactToLandingPage() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val landingFragment = LandingFragment()
        fragmentTransaction.replace(R.id.fragmentContainer,landingFragment)
        fragmentTransaction.commit()
    }

    override fun routerFromLandingToContactPage() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val contactFragment = ContactFragment()
        fragmentTransaction.replace(R.id.fragmentContainer,contactFragment)
        fragmentTransaction.commit()

    }

    override fun routeFromContactToAddContact() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val addContactFragment = AddContactFragment()
        fragmentTransaction.add(R.id.fragmentContainer,addContactFragment)
        fragmentTransaction.addToBackStack("Add Contact Fragment")
        fragmentTransaction.commit()


    }

    override fun routeFromContactToContactDetail(contact: Contact) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val contactDetailFragment = ContactDetailFragment()
        fragmentTransaction.add(R.id.fragmentContainer,contactDetailFragment)
        fragmentTransaction.addToBackStack("Add Contact Fragment")
        val bundle = Bundle()
        bundle.putString("ContactName",contact.contactName)
        bundle.putString("ContactImage",contact.contactImage)
        bundle.putString("ContactNumber",contact.contactNumber)
        bundle.putBoolean("hasContactImage",contact.hasContactImage)
        bundle.putString("ContactAddress",contact.contactAddress)
        bundle.putString("ContactEmailId",contact.contactEmailId)
        contactDetailFragment.arguments = bundle
        fragmentTransaction.commit()

    }

    override fun routeFromContactToImportContact() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val fragmentImport = ContactImportFragment()
        fragmentTransaction.add(R.id.fragmentContainer,fragmentImport)
        fragmentTransaction.addToBackStack("Import Contact Fragment")
        fragmentTransaction.commit()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        initFragment()

    }

}