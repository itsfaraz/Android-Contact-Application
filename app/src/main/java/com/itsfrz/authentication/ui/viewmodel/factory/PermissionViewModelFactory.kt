package com.itsfrz.authentication.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itsfrz.authentication.ui.Permission
import com.itsfrz.authentication.ui.viewmodel.PermissionViewModel

class PermissionViewModelFactory(
    private val permission : Permission
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PermissionViewModel(permission) as T
    }
}