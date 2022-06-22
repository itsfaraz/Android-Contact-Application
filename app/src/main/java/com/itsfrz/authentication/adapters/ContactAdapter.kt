package com.itsfrz.authentication.adapters


import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itsfrz.authentication.AuthenticationCommunicator
import com.itsfrz.authentication.R
import com.itsfrz.authentication.model.Contact


class ContactAdapter(val context: Context,val contactList: ArrayList<Contact>) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private lateinit var communicator: AuthenticationCommunicator

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_list_item,parent,false)
        val contactViewHolder = ContactViewHolder(view)
        communicator = context as AuthenticationCommunicator
        return contactViewHolder
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        with(holder){
            contactName.text = contactList[position].contactName
            val hasImage = contactList[position].hasContactImage
            if (hasImage){
                val imageString = contactList[position].contactImage
                contactImage.setImageURI(Uri.parse(imageString))
            }else{
                contactImage.setImageResource(R.drawable.ic_baseline_account_box_24)
            }
        }
        holder.itemView.setOnClickListener {
            communicator.routeFromContactToContactDetail(contactList[position])
        }

    }



    override fun getItemCount(): Int = contactList.size

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contactName : TextView = itemView.findViewById(R.id.contactName)
        val contactImage : ImageView = itemView.findViewById(R.id.contactImage)


    }

}
