package com.itsfrz.authentication.ui.views.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.itsfrz.authentication.ui.views.activity.AuthenticationCommunicator
import com.itsfrz.authentication.MainActivity
import com.itsfrz.authentication.R
import com.itsfrz.authentication.ui.views.animation.startAnimation
import com.itsfrz.authentication.model.database.PreferenceRespository


class ContactFragment : Fragment() {

    private lateinit var communicator : AuthenticationCommunicator
    private val preferenceResository by lazy {
        PreferenceRespository(requireContext())
    }

    private lateinit var contactToolBar : Toolbar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contact, container, false)
        initView(view)
        setToolBar()
        setHasOptionsMenu(true);
        setupNewContact(view);
        communicator = activity as AuthenticationCommunicator

        return view
    }

    private fun setToolBar() {
        contactToolBar.setTitle("My Contact")
        contactToolBar.setBackgroundColor(Color.parseColor(("#60A7DE")))
        contactToolBar.setTitleTextColor(Color.WHITE)
        (activity as AppCompatActivity).setSupportActionBar(contactToolBar)
    }

    private fun initView(view : View) {
        contactToolBar = view.findViewById(R.id.contactToolBar)
    }


    private fun setupNewContact(view : View) {

        val animation = AnimationUtils.loadAnimation(requireContext(),R.anim.circle_explosion_animation).apply {
            duration = 500
            interpolator = AccelerateDecelerateInterpolator()
        }


        val addNewContact = view.findViewById<FloatingActionButton>(R.id.addNewContact)
        val circleMotionView = view.findViewById<View>(R.id.circleMotionView)
        addNewContact.setOnClickListener {
            addNewContact.isVisible = false
            circleMotionView.isVisible = true
            circleMotionView.startAnimation(animation){
                communicator.routeFromContactToAddContact()
            }
            circleMotionView.isVisible = false
            addNewContact.isVisible = true
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.contact_item_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.contactImportMenu -> {
                importContactsMenu()
            }
            R.id.contactUserMenu -> {
                userInfoMenu()
            }
            R.id.contactDeleteMenu -> {
                deleteMyContacts()
            }
            R.id.userLogoutMenu -> {
                logoutUser()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logoutUser() {
        preferenceResository.clearUser()
        contactToActivity()
    }

    private fun contactToActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun deleteMyContacts() {

    }

    private fun userInfoMenu() {
        communicator.routeFromContactToLandingPage()

    }

    private fun importContactsMenu() {
        communicator.routeFromContactToImportContact()
    }


    override fun onResume() {
        super.onResume()


    }

    override fun onPause() {
        super.onPause()
    }


}