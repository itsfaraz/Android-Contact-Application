package com.itsfrz.authentication.ui.views.compose.components

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.itsfrz.authentication.data.entities.ContactModel
import com.itsfrz.authentication.ui.viewmodel.ContactImportViewModel
import com.itsfrz.authentication.ui.views.compose.ui.theme.Blue100
import com.itsfrz.authentication.ui.views.compose.utils.Loader




@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ImportContactScreen(
    navController: NavController,
    contactList : List<ContactModel>
) {
    Scaffold(topBar = {
        NavBarLayout(
            navController = navController,
            title = "Import Contact",
            icon = Icons.Default.ArrowBack,
            iconClickEvent = {
                navController.navigateUp()
            },
            isActionMenuPresent = true,
            isImportMenuItem = false,
            isSearchMenuItem = false,
            isUserInfoMenuItem = false,
            isLogoutMenuItem = false,
            isDeleteAllMenuItem = false,
            isSelectAllMenuItem = true,
            importClickEvent = {},
            searchClickEvent = {},
            userInfoClickEvent = {},
            deleteAllClickEvent = {},
            logoutClickEvent = {},
            selectAllClickEvent = {}
        )
    }
    ) {

        ImportContact(
            contactList
        )
    }
}


@Composable
fun ImportContact(
    contactList : List<ContactModel>
) {

//   if (contactImportViewModel.loaderVisibility())
//       Loader(loaderMessage = "Please wait ...")
//    val contactList = contactImportViewModel.getContactList()

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        items(contactList.size) { index ->

            val contact = contactList.get(index)
            ImportListItemRow(contact = contact, index = index)
            Divider(
                modifier = Modifier
                    .fillMaxWidth(.96F)
                    .padding(2.dp),
                color = Blue100,
                thickness = .5.dp
            )
        }
    }
}


@Composable
fun ImportListItemRow(
    contact: ContactModel,
    index: Int,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
//                        contactViewModel.addIndexInList(index)
                    }
                )
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImportContactProfilePic(contactImage = contact.contactImage)
        ImportContactInfo(contactName = contact.contactName)
//        if (contactViewModel.checkIndexIsInList(index)) {
//            ContactCheckIcon()
//        } else {
//            Spacer(modifier = Modifier.size(22.dp))
//        }

    }

}

@Composable
fun ContactCheckIcon() {
    Icon(
        modifier = Modifier
            .size(22.dp),
        imageVector = Icons.Default.Check,
        contentDescription = "Item Checked",
        tint = Blue100,
    )
}


@Composable
fun ContactCardListRowItem(
    @DrawableRes contactImage: Int,
    contactName: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ContactProfilePic(contactImage = contactImage)
        ContactInfo(contactName = contactName)
    }
}

@Composable
fun ImportContactProfilePic(
    contactImage: String
) {
//    AsyncImage(
//        modifier = Modifier
//            .border(width = 1.dp, color = Blue100, shape = CircleShape)
//            .clip(CircleShape)
//            .size(60.dp),
//        model = rememberAsyncImagePainter(contactImage),
//        error = painterResource(id = R.drawable.profile),
//        contentDescription = "Contact Image",
//    )

    if (contactImage.isNotEmpty()) {
        Log.d("IMAGE_STRING", "ImportContactProfilePic: ${contactImage}")
    }
}

@Composable
fun ImportContactInfo(
    contactName: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(.9F)
            .padding(horizontal = 15.dp)
    ) {
        Text(
            text = contactName,
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray,
            fontSize = 18.sp
        )
    }
}

//@Preview(showSystemUi = true, showBackground = true)
//@Composable
//fun ImportContactScreenPreview() {
//    val navController = rememberNavController()
//    ImportContactScreen(navController = navController)
//}