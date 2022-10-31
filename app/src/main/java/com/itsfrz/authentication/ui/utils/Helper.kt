package com.itsfrz.authentication.ui.utils

import androidx.annotation.IdRes
import androidx.navigation.NavOptions
import com.itsfrz.authentication.R

object Helper {


    const val USER_PREF = "USER_PREF"
    const val READ_CONTACT_RQ = 103
    const val WRITE_CONTACT_RQ = 104

    val navOptions =
        NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right)
            .build()

    fun navOptionsWithPopStack(
        @IdRes destinationId : Int,
        inclusive : Boolean = false
    ) : NavOptions{
        return NavOptions.Builder()
            .setPopUpTo(destinationId = destinationId, inclusive = inclusive)
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right)
            .build()
    }

}