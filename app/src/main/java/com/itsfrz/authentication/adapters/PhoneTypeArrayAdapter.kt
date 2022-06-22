package com.itsfrz.authentication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.itsfrz.authentication.R
import com.itsfrz.authentication.model.PhoneType

class PhoneTypeArrayAdapter(context: Context,phoneTypeList: ArrayList<PhoneType>) :
    ArrayAdapter<PhoneType>(context, 0, phoneTypeList) {


    private fun initView(position: Int,convertView: View?,parent: ViewGroup) : View{
        val phoneTypeView = getItem(position)
        val view = convertView ?:LayoutInflater.from(context).inflate(R.layout.phone_spinner_item_layout,parent,false)
        val phoneTypeImageView : ImageView = view.findViewById(R.id.phoneSpinnerImage)
        val phoneTypeTextView : TextView = view.findViewById(R.id.phoneSpinnerText)

        phoneTypeImageView.setImageResource(phoneTypeView!!.image)
        phoneTypeTextView.text = phoneTypeView.name

        return view
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }
}