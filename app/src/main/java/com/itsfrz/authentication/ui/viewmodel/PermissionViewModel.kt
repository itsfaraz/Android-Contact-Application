package com.itsfrz.authentication.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.itsfrz.authentication.ui.Permission

class PermissionViewModel(
    private val permission: Permission
) : ViewModel() {

    private val _readPermissionState : MutableState<Boolean> = mutableStateOf(false)
    val readPermissionState : State<Boolean> = _readPermissionState

    private val _writePermissionState : MutableState<Boolean> = mutableStateOf(false)
    val writePermissionState : State<Boolean> = _writePermissionState


    fun checkReadPermissionStatus(context : Context){
        _readPermissionState.value = permission.checkReadPermission(context)
    }

    fun checkWritePermissionStatus(context : Context){
        _writePermissionState.value = permission.checkWritePermission(context)
    }

}