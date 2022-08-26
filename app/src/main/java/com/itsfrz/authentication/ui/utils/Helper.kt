package com.itsfrz.authentication.ui.utils

import androidx.navigation.NavOptions
import com.itsfrz.authentication.R

class Helper {

    companion object{
        val navOptions =
            NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right)
                .build()
    }

}