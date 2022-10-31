package com.itsfrz.authentication.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar


class Permission : AppCompatActivity(){

    fun requestRunTimePermission(
        activity: Activity,
        permissions : Array<String>,
        requestCode : Int
    ) : Boolean{
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


    fun checkWritePermission(context: Context) : Boolean{
        val writeContact = ActivityCompat.checkSelfPermission(context,
            Manifest.permission.WRITE_CONTACTS
        );
        return writeContact == PackageManager.PERMISSION_GRANTED
    }

    fun checkReadPermission(context: Context) : Boolean{
        val readContact = ActivityCompat.checkSelfPermission(context,
            Manifest.permission.READ_CONTACTS
        );
        return readContact == PackageManager.PERMISSION_GRANTED
    }

    fun requestWritePermission(context: Context){}

    fun checkReadWritePermissions(context: Context) : Boolean{
        val readContact = ActivityCompat.checkSelfPermission(context,
            Manifest.permission.READ_CONTACTS
        );
        val writeContact = ActivityCompat.checkSelfPermission(context,
            Manifest.permission.WRITE_CONTACTS
        );
        return readContact == PackageManager.PERMISSION_GRANTED && writeContact == PackageManager.PERMISSION_GRANTED
    }

}
