package com.itsfrz.authentication.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class PermissionViewModel : ViewModel() {

    val permissionState: MutableState<Boolean> = mutableStateOf(false)

}