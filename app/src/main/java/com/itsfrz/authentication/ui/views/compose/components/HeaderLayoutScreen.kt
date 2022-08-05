package com.itsfrz.authentication.ui.views.compose.components


import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.itsfrz.authentication.R
import com.itsfrz.authentication.ui.views.Screen
import com.itsfrz.authentication.ui.views.compose.ui.theme.Blue100




@Composable
fun NavBarLayout(
    navController : NavController,
    title : String,
    icon : ImageVector = Icons.Default.Home,
    isActionMenuPresent : Boolean = false,
    isImportMenuItem : Boolean = false,
    isUserInfoMenuItem : Boolean = false,
    isDeleteAllMenuItem : Boolean = false,
    isLogoutMenuItem : Boolean = false,
    isSearchMenuItem : Boolean = false,
    isSelectAllMenuItem : Boolean = false,
    iconClickEvent : () -> Unit,
    importClickEvent : () -> Unit,
    userInfoClickEvent : () -> Unit,
    deleteAllClickEvent : () -> Unit,
    logoutClickEvent : () -> Unit,
    searchClickEvent : () -> Unit,
    selectAllClickEvent : () -> Unit

) {
    var showMenu by remember {
        mutableStateOf(false)
    }

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
                    if (isSearchMenuItem){
                        DropdownMenuItem(onClick = { searchClickEvent.invoke() }) {
                            DropDownMenuItem(menuOptionIcon = R.drawable.ui_search_icon, menuOptionText = "Search")
                        }
                    }
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

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun HeaderLayoutPreview() {
//}