package com.itsfrz.authentication.ui.views.compose.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.itsfrz.authentication.R
import com.itsfrz.authentication.data.entities.ContactModel
import com.itsfrz.authentication.ui.utils.ColorGenerator
import com.itsfrz.authentication.ui.views.compose.ui.theme.Blue100


//@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
//@Composable
//fun ImportContactScreen(
//    navController: NavController,
//    contactList : List<ContactModel>,
//    progressBar : Boolean,
//    addContact : (contactModel : ContactModel) -> Unit
//) {
//
//}


@Composable
fun ImportListItemRow(
    modifier: Modifier = Modifier,
    contact: ContactModel,
) {
    var color by remember {
        mutableStateOf(ColorGenerator.getRandomColor())
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImportContactProfilePic(contactImage = contact.contactImage, isContactImage = contact.hasContactImage,color = color)
        ImportContactInfo(contactName = contact.contactName,contactNumber = contact.contactNumber,color = color)
    }

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
                .clip(RoundedCornerShape(12.dp))
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
                withStyle(style = SpanStyle(color = Color.Gray,fontSize = 22.sp)){
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
            color = Blue100,
            fontSize = 10.sp
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun ImportContactScreenPreview() {
    ImportListItemRow(contact = ContactModel("","Faraz Sheikh","7796224997",false,"","","","","",""))
//    val navController = rememberNavController()
//    ImportContactScreen(navController = navController)
}