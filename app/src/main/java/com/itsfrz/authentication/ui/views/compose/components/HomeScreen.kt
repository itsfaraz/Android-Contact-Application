package com.itsfrz.authentication.ui.views.compose.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.itsfrz.authentication.R
import com.itsfrz.authentication.ui.utils.Helper




//@Composable
//fun SearchBar() {
//    var searchText by remember {
//        mutableStateOf("")
//    }
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        OutlinedTextField(
//            modifier = Modifier.weight(1F),
//            value = searchText, onValueChange = {
//                searchText = it
//            })
//
//        Icon(
//            painter = painterResource(id = R.drawable.ui_search_icon),
//            contentDescription = "Search Icon"
//        )
//    }
//}
