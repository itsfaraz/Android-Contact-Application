package com.itsfrz.authentication.ui.views.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.itsfrz.authentication.ui.views.activity.AuthenticationCommunicator
import com.itsfrz.authentication.R
import com.itsfrz.authentication.adapters.ContactAdapter
import com.itsfrz.authentication.adapters.ContactListener

import com.itsfrz.authentication.data.entities.ContactModel
import com.itsfrz.authentication.model.database.PreferenceRespository
import com.itsfrz.authentication.ui.viewmodel.ContactImportViewModel


class ContactImportFragment : Fragment() , ContactListener{
    private val CONTACTIMPORT : String = "CONTACTIMPORT"


    private lateinit var communicator: AuthenticationCommunicator
    private lateinit var contactRecyclerView: RecyclerView
    private lateinit var contactAdapter: ContactAdapter
    private var contactList: ArrayList<ContactModel> = ArrayList()
    private val selectedContactList : ArrayList<ContactModel> = ArrayList()




    private lateinit var contactImportToolBar: Toolbar
    private lateinit var progressBar1: ProgressBar
    private lateinit var progressBar2: ProgressBar
    private lateinit var fabImportButton : FloatingActionButton


    private val contactProviderViewModel by lazy {
        ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(ContactImportViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.fragment_contact_import, container, false)
        initView(view)
        setUpToolBar()
        communicator = activity as AuthenticationCommunicator
        setupRecyclerView(contactList, view)
        setContactList()
        importContact()

        Log.d(CONTACTIMPORT, "onCreateView: contactProviderViewModel View Model Instance Hash Value ${contactProviderViewModel.hashCode()}")



        return view;
    }



    private fun importContact() {
        fabImportButton.setOnClickListener {
            contactProviderViewModel.insertContactsInDB(selectedContactList)
            communicator.routeFromContactImportToContact()
        }
    }





    private fun setUpProgressBar() {
        progressBar1.visibility = View.VISIBLE
        progressBar2.visibility = View.VISIBLE
    }

    private fun setContactList(){
        val contactList = ArrayList<ContactModel>()
        contactProviderViewModel.getContactList().observe(requireActivity()){
            contactList.addAll(it)
            contactAdapter.updateContacts(it)
            Log.d(CONTACTIMPORT, "getContactList: ${it.size}")
            progresBarHide()
            selectedContactList.clear()


        }

    }




    private fun setUpToolBar() {
        contactImportToolBar.setTitle("Phone Contact")
        contactImportToolBar.setTitleTextColor(Color.WHITE)
        contactImportToolBar.setBackgroundColor(Color.parseColor("#60A7DE"))
        (activity as AppCompatActivity).setSupportActionBar(contactImportToolBar)
    }

    private fun initView(view: View) {
        contactImportToolBar = view.findViewById(R.id.contactImportToolBar)
        progressBar1 = view.findViewById(R.id.contactImportProgresBar1)
        progressBar2 = view.findViewById(R.id.contactImportProgresBar2)
        fabImportButton = view.findViewById(R.id.importDbButton)
    }


    public fun progresBarHide(){
        progressBar1.visibility = View.GONE
        progressBar2.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.item_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.selectAllContactMenu -> {
                selectAllContact()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun selectAllContact() {
        selectedContactList.clear()
        selectedContactList.addAll(contactList)
        contactAdapter.selectAllContact()
        fabButtonVisible()
    }


    override fun onResume() {
        super.onResume()

    }


    override fun onPause() {
        super.onPause()

    }


    private fun setupRecyclerView(contactList: ArrayList<ContactModel>, view: View) {
        contactRecyclerView = view.findViewById<RecyclerView>(R.id.contactRecyclerView)
        contactRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        contactRecyclerView.setHasFixedSize(true)
        contactAdapter = ContactAdapter(requireContext(), contactList,this)
        contactRecyclerView.adapter = contactAdapter
        Log.d("RECYLER", "setupRecyclerView: ${contactRecyclerView} Adapter ${contactAdapter}")

    }

    private fun fabButtonVisible(){
        fabImportButton.visibility = View.VISIBLE
    }

    private fun fabButtonInVisible(){
        fabImportButton.visibility = View.GONE
    }

    override fun onContactChange(list: List<ContactModel>) {
        selectedContactList.clear()
        selectedContactList.addAll(list)
        Log.d(CONTACTIMPORT, "onContactChange: ${selectedContactList.size}")
        if (selectedContactList.size > 0){
            fabButtonVisible()
        }else
            fabButtonInVisible()
    }


}