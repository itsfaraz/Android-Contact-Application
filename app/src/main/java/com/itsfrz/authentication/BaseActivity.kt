package com.itsfrz.authentication

import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar

open class BaseActivity() : AppCompatActivity() {


    protected val FINE_LOCATION_RQ = 101
    protected val CAMERA_RQ = 102
    protected val READ_CONTACT_RQ = 103
    protected val WRITE_CONTACT_RQ = 104

    fun requestRunTimePermission(activity: Activity, permissions : Array<String>, requestCode : Int) : Boolean{
        var answer :Boolean = false

        if (permissions.size == 1){
            if (ActivityCompat.checkSelfPermission(activity.baseContext,permissions.get(0)) == PackageManager.PERMISSION_GRANTED){
                answer =  true
            }else{
                if(ActivityCompat.shouldShowRequestPermissionRationale(activity,permissions.get(0))){
                    Snackbar.make(findViewById(android.R.id.content),"Allow Permission", Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",{
                        ActivityCompat.requestPermissions(activity,permissions,requestCode)
                    }).show()
                }else{
                    ActivityCompat.requestPermissions(activity,permissions,requestCode)
                }
            }
        }

        return answer
    }

}