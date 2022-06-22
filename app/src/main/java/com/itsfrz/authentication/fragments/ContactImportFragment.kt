package com.itsfrz.authentication.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itsfrz.authentication.AuthenticationCommunicator
import com.itsfrz.authentication.R
import com.itsfrz.authentication.adapters.ContactAdapter
import com.itsfrz.authentication.database.PreferenceRespository
import com.itsfrz.authentication.model.Contact
import com.itsfrz.authentication.provider.ContactLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ContactImportFragment : Fragment() {

    private lateinit var communicator: AuthenticationCommunicator
    private lateinit var contactRecyclerView: RecyclerView
    private lateinit var contactAdapter: ContactAdapter
    private var contactList: ArrayList<Contact> = ArrayList()
    private lateinit var contactLoader: ContactLoader
    private lateinit var contactImportToolBar: Toolbar
    private lateinit var progressDialog1: ProgressBar
    private lateinit var progressDialog2: ProgressBar

    private val preferenceRespository by lazy {
        PreferenceRespository(requireContext())
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
        handleLoadContacts(view)





        return view;
    }


    private  fun handleLoadContacts(view: View) {
        CoroutineScope(Dispatchers.Main).launch {
            CoroutineScope(Dispatchers.IO).async {
                loadData() // Loading data in my arraylis
            }.await()
            contactAdapter.notifyDataSetChanged() // recycler view
            progressDialog1.visibility = View.GONE
            progressDialog2.visibility = View.GONE
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
        progressDialog1 = view.findViewById(R.id.contactImportProgresBar1)
        progressDialog2 = view.findViewById(R.id.contactImportProgresBar2)
    }

    private suspend fun loadData() {

        contactLoader = ContactLoader()
        if (contactList.size > 0) {
            return
        } else {
            val list = contactLoader.fetchContacts(requireContext())
            contactList.clear()
            contactList.addAll(list)
        }

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.item_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onResume() {
        super.onResume()

    }


    override fun onPause() {
        super.onPause()

    }


    private fun setupRecyclerView(contactList: ArrayList<Contact>, view: View) {
        contactRecyclerView = view.findViewById<RecyclerView>(R.id.contactRecyclerView)
        contactAdapter = ContactAdapter(requireContext(), contactList)
        contactRecyclerView.adapter = contactAdapter
        contactRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        Log.d("RECYLER", "setupRecyclerView: ${contactRecyclerView} Adapter ${contactAdapter}")

    }


}