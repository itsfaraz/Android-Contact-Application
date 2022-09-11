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

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            NavBarLayout(
                navController,
                title = "Home",
                isActionMenuPresent = true,
                isImportMenuItem = true,
                isDeleteSelectedMenuItem =  true,
                isLogoutMenuItem = true,
                isSearchBarMenuItem = true,
                showSearchBar = false,
                toggleSearchBar = {},
                isUserInfoMenuItem = true,
                isSelectAllMenuItem = false,
                iconClickEvent = {},
                importClickEvent = { navController.navigate(
                    resId = R.id.contactImportFragment,
                    args = null,
                    navOptions = Helper.navOptions
                ) },
                userInfoClickEvent = {},
                deleteSelectedClickEvent = {},
                logoutClickEvent = {},
                selectAllClickEvent = {},
                getSearchQuery = {},
                searchQuery = "",
                totalContactsFound = 0

            )
        },
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            ContactListLayout()
        }
    }
}


@Composable
fun SearchBar() {
    var searchText by remember {
        mutableStateOf("")
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier.weight(1F),
            value = searchText, onValueChange = {
                searchText = it
            })

        Icon(
            painter = painterResource(id = R.drawable.ui_search_icon),
            contentDescription = "Search Icon"
        )
    }
}
