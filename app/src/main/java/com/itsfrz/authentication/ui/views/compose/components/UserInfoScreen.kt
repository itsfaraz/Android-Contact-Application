package com.itsfrz.authentication.ui.views.compose.components


import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.itsfrz.authentication.R
import com.itsfrz.authentication.ui.views.compose.ui.theme.Blue100
import com.itsfrz.authentication.ui.views.compose.ui.theme.Blue100Trans
import com.itsfrz.authentication.ui.views.compose.ui.theme.WhiteDeem100

@Composable
fun UserInfo(
    navController: NavController
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        ProfileImage()
        Spacer(modifier = Modifier.padding(vertical = 20.dp))
        ContactInfo(12, 32, 522)
        Spacer(modifier = Modifier.padding(vertical = 20.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            value = "ItsFRZ",
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Blue100,
                focusedBorderColor = Blue100,
                unfocusedBorderColor = Blue100Trans
            ),
            onValueChange = {},
            label = { Text(text = "Username", color = Blue100Trans) },
            placeholder = { Text(text = "Update Username ...") }
        )
        Spacer(modifier = Modifier.padding(vertical = 20.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            value = "faraz@gmail.com",
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Blue100,
                focusedBorderColor = Blue100,
                unfocusedBorderColor = Blue100Trans
            ),
            onValueChange = {},
            label = { Text(text = "Email Id", color = Blue100Trans) },
            placeholder = { Text(text = "Update Email Id ...") }
        )
        Spacer(modifier = Modifier.padding(vertical = 20.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            value = "12-March-2022",
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Blue100,
                focusedBorderColor = Blue100,
                unfocusedBorderColor = Blue100Trans
            ),
            onValueChange = {},
            readOnly = true,
            label = { Text(text = "Logged In", color = Blue100Trans) },
        )
    }
}

@Composable
fun ProfileImage(
//    bitmap: Bitmap
) {
    Card(
        modifier = Modifier.size(160.dp),
        elevation = 16.dp,
        shape = CircleShape,
        border = BorderStroke(width = 1.dp, color = Blue100)
    ) {
        Image(
//           bitmap = bitmap.asImageBitmap(), contentDescription = "User Image"
            painter = painterResource(id = R.drawable.profile), contentDescription = "UserImage"
        )
    }
}

@Composable
fun ContactInfo(
    contactAdded: Int = 0,
    contactDeleted: Int = 0,
    totalContact: Int = 0,
) {
    Card(
        modifier = Modifier.fillMaxWidth(.7F),
        backgroundColor = Blue100

    ) {
        LazyRow(
            horizontalArrangement = Arrangement.Center
        ) {
            item {
                ContactInfoRowItem(
                    imageIcon = R.drawable.ui_add_icon,
                    actionTitle = "Added",
                    actionData = contactAdded
                )
                ContactInfoRowItem(
                    imageIcon = R.drawable.ui_deleted_icon,
                    actionTitle = "Deleted",
                    actionData = contactDeleted
                )
                ContactInfoRowItem(
                    imageIcon = R.drawable.ui_total_icon,
                    actionTitle = "Total",
                    actionData = totalContact
                )
            }
        }
    }
}

@Composable
fun ContactInfoRowItem(
    @DrawableRes imageIcon: Int,
    actionTitle: String,
    actionData: Int
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 7.dp, vertical = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(15.dp),
                painter = painterResource(id = imageIcon),
                contentDescription = "Add",
                tint = WhiteDeem100
            )
            Text(
                modifier = Modifier.padding(5.dp),
                text = actionTitle,
                textAlign = TextAlign.Center,
                color = WhiteDeem100
            )

        }
        Text(
            modifier = Modifier.wrapContentSize(),
            text = "$actionData",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UserInfoPreview() {
//    val navController = rememberNavController()
//    UserInfo(navController)
}