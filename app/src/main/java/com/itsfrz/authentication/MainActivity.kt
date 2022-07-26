package com.itsfrz.authentication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.itsfrz.authentication.ui.views.Screen
import com.itsfrz.authentication.ui.views.compose.components.*

//package com.itsfrz.authentication
//
//import android.Manifest
//import android.graphics.Color
//import android.graphics.drawable.ColorDrawable
//import android.os.Bundle
//import android.util.Log
//import android.widget.Button
//import androidx.fragment.app.Fragment
//import com.itsfrz.authentication.data.entities.ContactModel
//import com.itsfrz.authentication.model.database.PreferenceRespository
//import com.itsfrz.authentication.ui.views.activity.AuthenticationCommunicator
//import com.itsfrz.authentication.ui.views.activity.BaseActivity
//import com.itsfrz.authentication.ui.views.fragments.*
//
//
//


class MainActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Log.d("MAIN", "onCreate: Hello")
            ContactNavigation()

        }
    }
}

@Composable
fun ContactNavigation() {
    Log.d("NAV", "ContactNavigation: Entered Nav")
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.PermissionScreen.route){
        composable(route = Screen.PermissionScreen.route){
            PermissionScreen(navController)
        }

        composable(route = Screen.AuthenticationScreen.route){
            AuthenticationScreen(navController)
        }

        composable(route = Screen.SignupScreen.route){
            SignUpScreen(navController,signUpEvent = {username, email, password ->  }, tosEvent = { /*TODO*/ }) {

            }
        }

        composable(route = Screen.LoginScreen.route){
            LoginScreen(navController)
        }

        composable(route = Screen.HomeScreen.route){
            HomeScreen(navController)
        }

        composable(route = Screen.UserInfoScreen.route){
            UserInfo(navController)
        }


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