package com.itsfrz.authentication.ui.views.compose.components

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.itsfrz.authentication.R
import com.itsfrz.authentication.data.entities.ContactModel
import com.itsfrz.authentication.ui.utils.ColorGenerator
import com.itsfrz.authentication.ui.viewmodel.ContactImportViewModel
import com.itsfrz.authentication.ui.views.compose.ui.theme.Blue100
import com.itsfrz.authentication.ui.views.compose.utils.Loader




@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ImportContactScreen(
    navController: NavController,
    contactList : List<ContactModel>,
    progressBar : Boolean
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
            contactList,
            progressBar
        )
    }
}


@Composable
fun ImportContact(
    contactList : List<ContactModel>,
    progressBar: Boolean
) {
    if(!progressBar){
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
                    thickness = 0.3.dp
                )
            }
        }
    }else{
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Loader(loaderMessage = "Contact Syncing ...")
        }
    }
}


@Composable
fun ImportListItemRow(
    contact: ContactModel,
    index: Int,
) {
    val color : Color = ColorGenerator.getRandomColor()
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
        ImportContactProfilePic(contactImage = contact.contactImage, isContactImage = contact.hasContactImage,color = color)
        ImportContactInfo(contactName = contact.contactName,contactNumber = contact.contactNumber,color = color)
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
fun ImportContactProfilePic(
    isContactImage : Boolean,
    contactImage: String,
    color: Color
) {

    if (!isContactImage){
        Icon(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Dummy Profile",
            modifier = Modifier
                .padding(10.dp)
                .border(width = 1.dp, color = color, shape = CircleShape)
                .clip(CircleShape)
                .size(60.dp),
            tint = Blue100
        )
    }else{

        if (contactImage.isNotBlank()){
            AsyncImage(
                modifier = Modifier
                    .padding(10.dp)
                    .border(width = 1.dp, color = color, shape = CircleShape)
                    .clip(CircleShape)
                    .size(60.dp),
                model = (contactImage),
                error = painterResource(id = R.drawable.profile),
                contentDescription = "Contact Image",
            )
        }
    }

}

@Composable
fun ImportContactInfo(
    contactName: String,
    contactNumber: String,
    color: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(.9F)
            .padding(horizontal = 15.dp)
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = color, fontSize = 22.sp)){
                    append(contactName.substring(0,1))
                }
                append(contactName.substring(1))
            },
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray,
            fontSize = 18.sp
        )
        Text(
            text = contactNumber,
            fontWeight = FontWeight.Normal,
            color = color,
            fontSize = 10.sp
        )
    }
}

@Preview(showSystemUi = false)
@Composable
fun ImportContactScreenPreview() {
    ImportListItemRow(contact = ContactModel("","Faraz Sheikh","7796224997",false,"","","","","",""), index = 0)
//    val navController = rememberNavController()
//    ImportContactScreen(navController = navController)
}