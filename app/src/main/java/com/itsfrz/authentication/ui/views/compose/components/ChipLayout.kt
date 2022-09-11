package com.itsfrz.authentication.ui.views.compose.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.itsfrz.authentication.R
import com.itsfrz.authentication.data.entities.ContactModel
import com.itsfrz.authentication.ui.views.compose.ui.theme.Blue100


@Composable
fun ChipLayout(
    contacts: List<ContactModel>,
    removeContact: (index : Int) -> Unit
) {

    LazyRow(
       modifier = Modifier
           .fillMaxWidth()
    ){
        items(
            count = contacts.size,
            key = { index: Int -> contacts[index].hashCode()+index }
        ) { index ->
            Spacer(modifier = Modifier.padding(horizontal = 1.dp))
            Chip(contactModel = contacts[index], removeContact = {
                removeContact(index)
            })
            Spacer(modifier = Modifier.padding(horizontal = 1.dp))
        }
    }

}


@Composable
fun Chip(
    contactModel: ContactModel,
    removeContact : () -> Unit
) {

    Card(
        modifier = Modifier
            .wrapContentWidth()
            .height(44.dp),
        shape = RoundedCornerShape(18.dp),
        elevation = 10.dp
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!contactModel.hasContactImage){
                Icon(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Dummy Profile",
                    modifier = Modifier
                        .padding(12.dp)
                        .border(width = 1.dp, color = Blue100, shape = CircleShape)
                        .clip(CircleShape)
                        .size(20.dp),
                    tint = Blue100
                )
            }else{

                if (contactModel.contactImage.isNotBlank()){
                    AsyncImage(
                        modifier = Modifier
                            .padding(12.dp)
                            .border(width = 1.dp, color = Blue100, shape = CircleShape)
                            .clip(CircleShape)
                            .size(20.dp),
                        model = (contactModel.contactImage),
                        error = painterResource(id = R.drawable.profile),
                        contentDescription = "Contact Image",
                    )
                }
            }

            Text(
                modifier = Modifier.padding(horizontal = 1.dp),
                text = if (contactModel.contactName.length > 14) "${contactModel.contactName.substring(0,12)}..." else contactModel.contactName,
                fontSize = 10.sp
            )
            Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            Card(
                modifier = Modifier
                    .size(10.dp)
                    .clickable {
                        removeContact()
                    },
                shape = CircleShape,
                backgroundColor = Blue100,
                elevation = 12.dp
            ) {
                Icon(
                    imageVector = Icons.Default.Close, contentDescription = "Discard",
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
        }
    }

}


@Preview
@Composable
fun ChipLayoutPreview() {
    
    val contactList = arrayListOf<ContactModel>(
        ContactModel(
            contactId = "",
            contactName = "Mahindra Singh Dhoni",
            contactNumber = "123456789",
            hasContactImage = false,
            contactImage = "",
            contactAddress = "",
            contactEmailId = "",
            contactCountry = "",
            contactPostCode = "",
            username = ""
        ),
        ContactModel(
            contactId = "",
            contactName = "Faisal Sheikh",
            contactNumber = "123456789",
            hasContactImage = false,
            contactImage = "",
            contactAddress = "",
            contactEmailId = "",
            contactCountry = "",
            contactPostCode = "",
            username = ""
        ),
        ContactModel(
            contactId = "",
            contactName = "Hasnain Sheikh",
            contactNumber = "987656162",
            hasContactImage = false,
            contactImage = "",
            contactAddress = "",
            contactEmailId = "",
            contactCountry = "",
            contactPostCode = "",
            username = ""
        )
    )
    
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
    ){
        items(
            count = contactList.size,
            key = { index: Int -> contactList[index].hashCode()+index }
        ) { index ->
            Chip(contactModel = contactList[index],{ })
            Spacer(modifier = Modifier.padding(horizontal = 2.dp))
        }
    }
}