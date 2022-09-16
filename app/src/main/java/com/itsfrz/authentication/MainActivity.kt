package com.itsfrz.authentication

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.itsfrz.authentication.data.entities.mapper.ContactModelMapper
import com.itsfrz.authentication.data.repository.UserPreferenceRepository
import com.itsfrz.authentication.ui.utils.Helper
import com.itsfrz.authentication.ui.viewmodel.AuthenticationViewModel
import com.itsfrz.authentication.ui.viewmodel.factory.AuthenticationViewModelFactory
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {


    private var permissionCheck: Boolean = false
    private var isUserLoggedIn: Boolean = false
    private var username : String = ""
    private var loggedInDate : String = ""
    private var sortingType : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val authViewModelFactory = AuthenticationViewModelFactory(UserPreferenceRepository(this))
        val authViewModel =
            ViewModelProvider(this, authViewModelFactory).get(AuthenticationViewModel::class.java)
        runBlocking {
            authViewModel.fetchLoggedInStatus()
            delay(200)
        }

        permissionCheck = checkPermissionStatus()



        isUserLoggedIn = authViewModel.isLogginSuccess.value
        username = authViewModel.userName.value
        sortingType = authViewModel.sortingOrder.value
        loggedInDate = authViewModel.loggedInDate.value



        val bundleArgs = Bundle()
        bundleArgs.apply {
            putBoolean("isLoggedIn", isUserLoggedIn)
            putString("username",username)
            putString("sortingType",sortingType)
            putString("loggedInDate",loggedInDate)
        }


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController



        if (permissionCheck && isUserLoggedIn) {
            navController.navigate(
                resId = R.id.contactFragment,
                args = bundleArgs,
                navOptions = Helper.navOptionsWithPopStack(
                    destinationId = R.id.permissionFragment,
                    true
                )
            )
        } else if (!permissionCheck) {
            navController.navigate(
                resId = R.id.permissionFragment,
                args = bundleArgs,
                navOptions = Helper.navOptions
            )
        } else if (!isUserLoggedIn) {
            navController.navigate(
                resId = R.id.authenticationFragment,
                args = null,
                navOptions = Helper.navOptions
            )
        }
        setUsername()
    }

    private fun setUsername() {
        ContactModelMapper.username = username
    }

    fun checkPermissionStatus(): Boolean {
        val resultRead: Boolean =
            this.checkCallingOrSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
        val resultWrite: Boolean =
            this.checkCallingOrSelfPermission(Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED
        return resultRead || resultWrite

    }
}



//class MainActivity : BaseActivity() , AuthenticationCommunicator {
//    private val preferenceRespository by lazy {
//        PreferenceRespository(this)
//    }
//
//    private val TAG = "TAG"
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        val actionBar = supportActionBar
//        actionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FF70C0FF")))
//        actionBar?.hide()
//
//
//        val permission = requestRunTimePermission(this, arrayOf(Manifest.permission.READ_CONTACTS),READ_CONTACT_RQ)
//        if(permission){
//            initFragment()
//        }
//
//        val permissionButton = findViewById<Button>(R.id.permissionButton)
//        permissionButton.setOnClickListener {
//            if(requestRunTimePermission(this, arrayOf(Manifest.permission.READ_CONTACTS),READ_CONTACT_RQ))
//                initFragment()
//        }
//
//
//
//    }
//
//
//    private fun initFragment() {
//
//        lateinit var initFragment : Fragment
//        val status : Boolean? = preferenceRespository.getLoggedIn()
//        Log.d(TAG, "initFragment: ${preferenceRespository.getCurrentUser()}")
//        if (status == true && status != null){
//            initFragment = ContactFragment()
////            initFragment = ContactImportFragment()
//        }else{
//            initFragment = LoginFragment()
//        }
//        val fragmentTransaction = supportFragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.fragmentContainer,initFragment)
//        fragmentTransaction.commit()
//    }
//
//    override fun routeToLogin(username: String) {
//        val fragmentTransaction = supportFragmentManager.beginTransaction()
//        val loginFragment = LoginFragment()
//        val bundle = Bundle()
//        bundle.putString("username",username)
//        loginFragment.arguments = bundle
//        fragmentTransaction.replace(R.id.fragmentContainer,loginFragment)
//        fragmentTransaction.commit()
//    }
//
//    override fun routeToSignUp() {
//        val fragmentTransaction = supportFragmentManager.beginTransaction()
//        val signUpFragment = SignUpFragment()
//        fragmentTransaction.replace(R.id.fragmentContainer,signUpFragment)
//        fragmentTransaction.commit()
//    }
//
//    override fun routeFromLoginToLandingPage() {
//        val fragmentTransaction = supportFragmentManager.beginTransaction()
//        val landingFragment = LandingFragment()
//        fragmentTransaction.replace(R.id.fragmentContainer,landingFragment)
//        fragmentTransaction.commit()
//    }
//
//    override fun routerFromLoginToContactPage() {
//        val fragmentTransaction = supportFragmentManager.beginTransaction()
//        val contactFragment = ContactFragment()
//        fragmentTransaction.replace(R.id.fragmentContainer,contactFragment)
//        fragmentTransaction.commit()
//    }
//
//    override fun routerFromLoginToContactImportPage() {
//        val fragmentTransaction = supportFragmentManager.beginTransaction()
//        val contactImportFragment = ContactImportFragment()
//        fragmentTransaction.replace(R.id.fragmentContainer,contactImportFragment)
//        fragmentTransaction.commit()
//    }
//
//    override fun routeFromContactToLandingPage() {
//        val fragmentTransaction = supportFragmentManager.beginTransaction()
//        val landingFragment = LandingFragment()
//        fragmentTransaction.replace(R.id.fragmentContainer,landingFragment)
//        fragmentTransaction.commit()
//    }
//
//    override fun routerFromLandingToContactPage() {
//        val fragmentTransaction = supportFragmentManager.beginTransaction()
//        val contactFragment = ContactFragment()
//        fragmentTransaction.replace(R.id.fragmentContainer,contactFragment)
//        fragmentTransaction.commit()
//
//    }
//
//    override fun routeFromContactToAddContact() {
//        val fragmentTransaction = supportFragmentManager.beginTransaction()
//        val addContactFragment = AddContactFragment()
//        fragmentTransaction.add(R.id.fragmentContainer,addContactFragment)
//        fragmentTransaction.addToBackStack("BACKSTACK")
//        fragmentTransaction.commit()
//
//
//    }
//
//    override fun routeFromContactToContactDetail(contact: ContactModel) {
//        val fragmentTransaction = supportFragmentManager.beginTransaction()
////        val contactDetailFragment = ContactDetailFragment()
////        fragmentTransaction.add(R.id.fragmentContainer,contactDetailFragment)
////        fragmentTransaction.addToBackStack("BACKSTACK1")
////        val bundle = Bundle()
////        bundle.putString("ContactName",contact.contactName)
////        bundle.putString("ContactImage",contact.contactImage)
////        bundle.putString("ContactNumber",contact.contactNumber)
////        bundle.putBoolean("hasContactImage",contact.hasContactImage)
////        bundle.putString("ContactAddress",contact.contactAddress)
////        bundle.putString("ContactEmailId",contact.contactEmailId)
////        contactDetailFragment.arguments = bundle
////        fragmentTransaction.commit()
//
//    }
//
//
//
//    override fun routeFromContactToImportContact() {
//        val fragmentTransaction = supportFragmentManager.beginTransaction()
//        val fragmentImport = ContactImportFragment()
//        fragmentTransaction.add(R.id.fragmentContainer,fragmentImport)
//        fragmentTransaction.addToBackStack("Import Contact Fragment")
//        fragmentTransaction.commit()
//
//    }
//
//    override fun routeFromContactImportToContact() {
//        val fragmentTransaction = supportFragmentManager.beginTransaction()
//        val fragmentContact = ContactFragment()
//        fragmentTransaction.replace(R.id.fragmentContainer,fragmentContact)
//        fragmentTransaction.commit()
//    }
//
//    override fun routeFromContactFragmentToContactDetailFragment(contact : ContactModel) {
////        val fragmentTransaction = supportFragmentManager.beginTransaction()
////        val contactDetailFragment = ContactDetailFragment()
////        fragmentTransaction.add(R.id.fragmentContainer,contactDetailFragment)
////        fragmentTransaction.addToBackStack("BACKSTACK1")
////        val bundle = Bundle()
////
////        bundle.putString("ContactId",contact.contactId)
////        bundle.putString("ContactCountry",contact.contactCountry)
////        bundle.putString("ContactPostCode",contact.contactPostCode)
////        bundle.putString("ContactName",contact.contactName)
////        bundle.putString("ContactImage",contact.contactImage)
////        bundle.putString("ContactNumber",contact.contactNumber)
////        bundle.putBoolean("hasContactImage",contact.hasContactImage)
////        bundle.putString("ContactAddress",contact.contactAddress)
////        bundle.putString("ContactEmailId",contact.contactEmailId)
////        contactDetailFragment.arguments = bundle
////        fragmentTransaction.commit()
//    }
//
//    override fun onBackPressed() {
//        super.onBackPressed()
//        initFragment()
//
//    }
//
//}