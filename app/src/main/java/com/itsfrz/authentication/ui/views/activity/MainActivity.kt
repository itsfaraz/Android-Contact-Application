package com.itsfrz.authentication.ui.views.activity

import android.Manifest
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.itsfrz.authentication.R
import com.itsfrz.authentication.model.database.PreferenceRespository
import com.itsfrz.authentication.data.indatabase.model.Contact
import com.itsfrz.authentication.databinding.ActivityMainBinding
import com.itsfrz.authentication.ui.views.fragments.*

class MainActivity : BaseActivity() ,AuthenticationCommunicator {
    private val preferenceRespository by lazy {
        PreferenceRespository(this)
    }

    private val MAINACTIVITY = "MAIN_ACTIVITY"
    private lateinit var mainActivityBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        Log.d(MAINACTIVITY, "onCreate: Method Called After Layout Rendered")
        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("C10041")))
        actionBar?.hide()

        mainActivityBinding.permissionButton.setOnClickListener {
            if(requestRunTimePermission(this, arrayOf(Manifest.permission.READ_CONTACTS),READ_CONTACT_RQ))
                initFragment()
        }

        Log.d(MAINACTIVITY, "onCreate: Before Persmission Check")
        val permission = requestRunTimePermission(this, arrayOf(Manifest.permission.READ_CONTACTS),READ_CONTACT_RQ)
        if(permission){
            Log.d(MAINACTIVITY, "onCreate: After Persmission Check")
            initFragment()
        }

    }


    private fun initFragment() {

        lateinit var initFragment : Fragment
        if (preferenceRespository.getLoggedIn()){
            initFragment = ContactFragment()
        }else{
            initFragment = LoginFragment()
        }
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragmentContainer,initFragment)
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