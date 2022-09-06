package com.itsfrz.authentication.ui.views

import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar


open class Permission() : AppCompatActivity(){

    fun requestRunTimePermission(
        activity: Activity, permissions : Array<String>, requestCode : Int) : Boolean{
        var answer :Boolean = false

        if (permissions.size == 1){
            if (ActivityCompat.checkSelfPermission(activity.baseContext,permissions.get(0)) == PackageManager.PERMISSION_GRANTED){
                answer =  true
            }else{
                if(ActivityCompat.shouldShowRequestPermissionRationale(activity,permissions.get(0))){
                      ActivityCompat.requestPermissions(activity, permissions, requestCode)
                }else{
                    ActivityCompat.requestPermissions(activity,permissions,requestCode)
                }
            }
        }

        return answer
    }

}
