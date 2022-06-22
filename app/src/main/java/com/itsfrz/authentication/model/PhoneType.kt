package com.itsfrz.authentication.model

import com.itsfrz.authentication.R

data class PhoneType(
    val image: Int,
    val name: String
)

object PhoneTypes{

    private val images = intArrayOf(
        R.drawable.smartphone,
        R.drawable.work,
        R.drawable.home,
        R.drawable.other

    )

    private val phoneText = arrayOf<String>(
            "Mobile",
            "Work",
            "Home",
            "Other"
    )


    var phoneTypeList : ArrayList<PhoneType>? = null
    get() {
        if (field!=null)
            return field
        else{

            field = ArrayList<PhoneType>()
            for (index in 0 until images.size){
                val imageId = images[index]
                val phoneTypeText = phoneText[index]
                field!!.add(PhoneType(imageId,phoneTypeText))
            }
            return field
        }
    }


}