package com.itsfrz.authentication.adapters


import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itsfrz.authentication.R
import com.itsfrz.authentication.data.entities.ContactModel
import com.itsfrz.authentication.ui.views.activity.AuthenticationCommunicator


/*

How to get arraylist from recyclerview adapter in android kotlin
https://stackoverflow.com/questions/66237391/how-to-get-arraylist-from-recyclerview-adapter-in-android-kotlin



*/


class ContactAdapter(
    val context: Context,
    val contactList: ArrayList<ContactModel>,
    private val contactListener: ContactListener
) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {


    private val CONTACT_ADAPTER = "CONTACT_ADAPTER"

    private lateinit var communicator: AuthenticationCommunicator


    private var isChoosen: Boolean = false
    private var selectedItems: ArrayList<ContactModel> = ArrayList()

    private var doesAllSelected: Boolean = false


    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contactName: TextView = itemView.findViewById(R.id.contactName)
        val contactImage: ImageView = itemView.findViewById(R.id.contactImage)


    }

    fun updateContacts(newContactList: List<ContactModel>) {
        contactList.clear()
        contactList.addAll(newContactList)
        Log.d(CONTACT_ADAPTER, "updateContacts: ${newContactList}")
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.contact_list_item, parent, false)
        val contactViewHolder = ContactViewHolder(view)
        communicator = context as AuthenticationCommunicator

        return contactViewHolder
    }

    private fun highlightAllRow(holder: ContactViewHolder) {
        if (doesAllSelected) {
            holder.itemView.setBackgroundResource(R.color.item_selected_background)
            isChoosen = true;
        }
        else {
            holder.itemView.setBackgroundResource(R.color.item_deselected_background)
            isChoosen = false
        }
        
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact: ContactModel = contactList[position]
        with(holder) {
            contactName.text = contactList[position].contactName
            val hasImage = contactList[position].hasContactImage
            if (hasImage) {
                val imageString = contactList[position].contactImage
                contactImage.setImageURI(Uri.parse(imageString))
            } else {
                contactImage.setImageResource(R.drawable.ic_baseline_account_box_24)
            }

            // post check view highlight
            rowHighlight(holder, contact)
            // highlight All View when select all clicked
            highlightAllRow(holder)

        }


        selectItem(holder.itemView, position, contact)
//        Double Click Listener
//        var doubleClick : Boolean? = false
//        holder.itemView.setOnClickListener {
//            if (doubleClick!!){
//                communicator.routeFromContactToContactDetail(contactList[position])
//            }
//            doubleClick = true
//            Handler().postDelayed({ doubleClick = false },1000) // 1 sec
//        }


//        {
//            communicator.routeFromContactToContactDetail(contactList[position])
//        }


    }

    private fun rowHighlight(holder: ContactAdapter.ContactViewHolder, contact: ContactModel) {
        if (!selectedItems.contains(contact)) {
            holder.itemView.setBackgroundResource(R.color.item_deselected_background)
        } else {
            holder.itemView.setBackgroundResource(R.color.item_selected_background)
        }
    }


    private fun selectItem(itemView: View, position: Int, selectedContact: ContactModel) {
        itemView.setOnLongClickListener {

            if (!isChoosen && selectedItems.isEmpty()) {

                itemView.setBackgroundResource(R.color.item_selected_background)
                selectedItems.add(selectedContact)
                isChoosen = true
            } else {
                itemView.setBackgroundResource(R.color.item_deselected_background)
                selectedItems.remove(selectedContact)
                isChoosen = false
            }
            Log.d(CONTACT_ADAPTER, "selectItem: ${selectedItems.size}")
            contactListener.onContactChange(selectedItems)
            true
        }

        itemView.setOnClickListener {
            val selectedContact: ContactModel = contactList[position]
            if (isChoosen) {
                if (!selectedItems.contains(selectedContact)) {
                    itemView.setBackgroundResource(R.color.item_selected_background)
                    selectedItems.add(selectedContact)
                }
            } else {
                selectedItems.remove(selectedContact)
                itemView.setBackgroundResource(R.color.item_deselected_background)
            }
            Log.d(CONTACT_ADAPTER, "selectItem: ${selectedItems.size}")
            contactListener.onContactChange(selectedItems)
        }

    }


    override fun getItemCount(): Int = contactList?.size ?: 0
    fun selectAllContact() {
        doesAllSelected = true;
    }


}
