package com.itsfrz.authentication.ui.views.compose.components


import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.itsfrz.authentication.R
import com.itsfrz.authentication.ui.views.Screen
import com.itsfrz.authentication.ui.views.compose.ui.theme.Blue100
import com.itsfrz.authentication.ui.views.compose.ui.theme.Blue100Light
import com.itsfrz.authentication.ui.views.compose.ui.theme.Blue200


@Composable
fun NavBarLayout(
    navController : NavController?,
    title : String,
    icon : ImageVector = Icons.Default.Home,
    isActionMenuPresent : Boolean = false,
    isImportMenuItem : Boolean = false,
    isUserInfoMenuItem : Boolean = false,
    isDeleteAllMenuItem : Boolean = false,
    isLogoutMenuItem : Boolean = false,
    isSelectAllMenuItem : Boolean = false,
    isSearchBarMenuItem : Boolean = false,
    toggleSearch : (toggle : Boolean) -> Unit,
    searchQuery : String = "",
    getSearchQuery : (query : String) -> Unit,
    totalContactsFound : Int = 0,
    iconClickEvent : () -> Unit,
    importClickEvent : () -> Unit,
    userInfoClickEvent : () -> Unit,
    deleteAllClickEvent : () -> Unit,
    logoutClickEvent : () -> Unit,
    selectAllClickEvent : () -> Unit
) {
    var showMenu by remember {
        mutableStateOf(false)
    }

    LazyColumn{
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Blue100),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                IconButton(onClick = { iconClickEvent.invoke() }) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Nav Icon",
                        tint = Color.White
                    )
                }

                Text(
                    modifier = Modifier
                        .weight(1F)
                        .padding(top = 0.dp),
                    text = title,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
                if (isSearchBarMenuItem){

                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White,
                        modifier = Modifier.clickable {
                            toggleSearch(true)
                        }
                    )
                }

                if(isActionMenuPresent) {
                    IconButton(onClick = {
                        showMenu = true
                    }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Nav Menu Icon Dots",
                            tint = Color.White
                        )
                    }

                    if (showMenu){
                        DropdownMenu(
                            offset = DpOffset(x = (-60).dp, y = (0).dp),
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false },
                        ) {
                            if (isImportMenuItem){
                                DropdownMenuItem(onClick = { importClickEvent.invoke() }) {
                                    DropDownMenuItem(menuOptionIcon = R.drawable.ui_import_icon, menuOptionText = "Import")
                                }
                            }
                            if (isUserInfoMenuItem)
                            {
                                DropdownMenuItem(onClick = { userInfoClickEvent.invoke() }) {
                                    DropDownMenuItem(menuOptionIcon = R.drawable.ui_userinfo_icon, menuOptionText = "User Info")
                                }
                            }
                            if(isDeleteAllMenuItem){
                                DropdownMenuItem(onClick = { deleteAllClickEvent.invoke() }) {
                                    DropDownMenuItem(menuOptionIcon = R.drawable.ui_deleteall_icon, menuOptionText = "Delete All")
                                }
                            }
                            if (isLogoutMenuItem){
                                DropdownMenuItem(onClick = { logoutClickEvent.invoke() }) {
                                    DropDownMenuItem(menuOptionIcon = R.drawable.ui_logout_icon, menuOptionText = "Logout")
                                }
                            }
                            if (isSelectAllMenuItem){
                                DropdownMenuItem(onClick = { selectAllClickEvent.invoke() }) {
                                    DropDownMenuItem(menuOptionIcon = R.drawable.ui_select_all_icon, menuOptionText = "Select All")
                                }
                            }
                        }
                    }
                }


            }
            if (isSearchBarMenuItem){

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 10.dp),
                    thickness = 0.8.dp,
                    color = Blue100Light
                )

                CustomSearchBarLayout(
                    query = searchQuery,
                    onQueryChange = getSearchQuery,
                    counter = totalContactsFound,
                    )
            }


            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                thickness = 1.dp,
                color = Blue100Light
            )
        }
    }
}

@Composable
fun DropDownMenuItem(
    @DrawableRes menuOptionIcon : Int,
    menuOptionText : String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = menuOptionIcon),
            contentDescription = menuOptionText,
            tint = Blue100
        )
        Text(
            text = menuOptionText,
            color = Blue100,
            modifier = Modifier.padding(horizontal = 20.dp)
            )
    }
}

@Composable
fun CustomSearchBarLayout(
    query : String,
    onQueryChange : (newQuery : String) -> Unit,
    counter : Int = 0
) {
    Surface(
        elevation = 10.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            if (counter > 0){
                Text(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(horizontal = 10.dp),
                    text = "$counter",
                    color = Blue100,
                )
            }

            TextField(
                modifier = Modifier
                    .fillMaxWidth(.9F)
                    .padding(vertical = 2.dp,horizontal = 10.dp),
                value = query,
                onValueChange = {
                        onQueryChange(it)
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Blue100,
                    cursorColor = Blue200,
                    backgroundColor = Color.White,
                ),
                keyboardOptions = KeyboardOptions(
                    autoCorrect = true,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                placeholder = {
                    Text(text = "Search Contacts ...", color = Blue100)
                },
                maxLines = 1
            )
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close Icon",
                tint = Blue100,
                modifier = Modifier.clickable {
                    onQueryChange("")
                }
            )
        }
    }
}


@Preview
@Composable
fun CustomSearchBarLayoutPreview() {
    CustomSearchBarLayout(query = "", onQueryChange = {}, counter = 0)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HeaderLayoutPreview() {


    NavBarLayout(
        navController = null,
        title = "Import Contact",
        iconClickEvent = { /*TODO*/ },
        importClickEvent = { /*TODO*/ },
        userInfoClickEvent = { /*TODO*/ },
        deleteAllClickEvent = { /*TODO*/ },
        logoutClickEvent = { /*TODO*/ },
        selectAllClickEvent = { /*TODO*/ },
        toggleSearch = {
               false
        },
        searchQuery = "",
        getSearchQuery = {},
        totalContactsFound = 0
    )
}