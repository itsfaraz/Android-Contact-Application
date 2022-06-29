package com.itsfrz.authentication.ui.views.fragments

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.itsfrz.authentication.ui.views.activity.AuthenticationCommunicator
import com.itsfrz.authentication.MainActivity
import com.itsfrz.authentication.R
import com.itsfrz.authentication.adapters.ContactAdapter
import com.itsfrz.authentication.adapters.ContactListener
import com.itsfrz.authentication.data.entities.ContactModel
import com.itsfrz.authentication.ui.views.animation.startAnimation
import com.itsfrz.authentication.model.database.PreferenceRespository
import com.itsfrz.authentication.ui.viewmodel.ContactViewModel


class ContactFragment : Fragment() , ContactListener {


    private var username : String = ""

    private val CONTACT_FRAGMENT = "CONTACT_FRAGMENT"

    private lateinit var communicator : AuthenticationCommunicator

    private val contactViewModel by lazy {
        ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(ContactViewModel::class.java)
    }




    private val myContacts = ArrayList<ContactModel>()
    private lateinit var contactToolBar : Toolbar

    private lateinit var recyclerView: RecyclerView
    private lateinit var contactAdapter: ContactAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contact, container, false)
        initView(view)
        setUpRecylerView()
        initSwipe()
        communicator = activity as AuthenticationCommunicator
        setUpMyContacts()
        setupSession()
        setToolBar()
        setHasOptionsMenu(true);
        setupNewContact(view);
        Log.d(CONTACT_FRAGMENT, "onCreateView: ")
        return view
    }

    private fun setUpMyContacts() {
        contactViewModel.getContactListObserver().observe(requireActivity()){
            myContacts.clear()
            myContacts.addAll(it)
            Log.d(CONTACT_FRAGMENT, "setUpMyContacts: ${myContacts}")
            contactAdapter.updateContacts(it)
        }
    }

    private fun setupSession() {
        username = contactViewModel.getCurrentSession()
    }

    private fun setUpRecylerView() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        contactAdapter = ContactAdapter(requireContext(),myContacts,this)
        recyclerView.adapter = contactAdapter

    }


    private fun setToolBar() {
        contactToolBar.setTitle("My Contact")
        contactToolBar.setBackgroundColor(Color.parseColor(("#60A7DE")))
        contactToolBar.setTitleTextColor(Color.WHITE)
        (activity as AppCompatActivity).setSupportActionBar(contactToolBar)
    }

    private fun initView(view : View) {
        contactToolBar = view.findViewById(R.id.contactToolBar)
        recyclerView = view.findViewById(R.id.myContactRecyclerView)
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
        contactViewModel.clearUser()
        contactToActivity()
    }

    private fun contactToActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun deleteMyContacts() {
        contactViewModel.deleteMyContacts()
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

    override fun onContactChange(list: List<ContactModel>) {
        /* To Get Selected Data */
    }


    fun initSwipe(){
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                if (direction == ItemTouchHelper.LEFT){
                    deleteSelectedContact(myContacts.get(position))

                }else if(direction == ItemTouchHelper.RIGHT){
                    invokeContactUpdatePage(myContacts.get(position))
                }

            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }

    private fun invokeContactUpdatePage(contact: ContactModel) {
        communicator.routeFromContactFragmentToContactDetailFragment(contact)
    }

    private fun deleteSelectedContact(contact: ContactModel) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle("Are you sure to delete contact ${contact.contactName}")
        dialog.setIcon(R.drawable.ic_baseline_delete_outline_24)
        dialog.setCancelable(true)
        dialog.setNegativeButton("No"){
                dialog,which -> Toast.makeText(requireContext(), "Delete Operation Cancelled :)", Toast.LENGTH_SHORT).show()

        }
        dialog.setPositiveButton("Yes"){
           dialog,which ->  contactViewModel.deleteMyContactItem(contact)

        }
        dialog.show()


    }


}