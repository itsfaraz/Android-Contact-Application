package com.itsfrz.authentication.ui.views.compose.components

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.itsfrz.authentication.R
import com.itsfrz.authentication.ui.utils.ColorGenerator
import com.itsfrz.authentication.ui.viewmodel.ContactViewModel
import com.itsfrz.authentication.ui.views.compose.ui.theme.Blue100
import com.itsfrz.authentication.ui.views.compose.ui.theme.Blue100Light
import com.itsfrz.authentication.ui.views.compose.ui.theme.DangerRed100

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ContactListLayout() {


    val contactViewModel = ContactViewModel()
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        items(
            count = contactViewModel.listOfDemoContacts.size,
            key = { index: Int -> contactViewModel.listOfDemoContacts[index].hashCode()+index}
        ) { index ->

            val dismissState = rememberDismissState(
                confirmStateChange = {
                    when (it) {
                        DismissValue.DismissedToStart -> {
                            contactViewModel.removeContact(index)
                        }
                        DismissValue.DismissedToEnd -> {
                            contactViewModel.addContact(
                                ContactViewModel.Contact(R.drawable.profile,"Demo ${index+1}")
                            )
                        }
                    }
                    true
                }
            )
            SwipeToDismiss(
                modifier = Modifier,
                state = dismissState,
                dismissThresholds = {FractionalThreshold(0.1F)},
                directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
                background = {
                    val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
                    val color by animateColorAsState(
                        targetValue = when (dismissState.targetValue) {
                            DismissValue.Default -> Color.LightGray
                            DismissValue.DismissedToStart -> DangerRed100
                            DismissValue.DismissedToEnd -> Blue100
                        }
                    )
                    val icon = when (direction) {
                        DismissDirection.EndToStart -> Icons.Default.Delete
                        DismissDirection.StartToEnd -> Icons.Default.Done
                    }
                    val scale by animateFloatAsState(targetValue = if (dismissState.targetValue == DismissValue.Default) 0.8F else 1.2F)

                    val alignment = when(direction){
                        DismissDirection.StartToEnd -> Alignment.CenterStart
                        DismissDirection.EndToStart -> Alignment.CenterEnd
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color)
                            .padding(start = 12.dp, end = 12.dp),
                        contentAlignment = alignment
                    )
                    {
                      Row(
                          modifier = Modifier.wrapContentWidth(),
                          verticalAlignment = Alignment.CenterVertically
                      ) {
                          var operationText = if (direction == DismissDirection.EndToStart) " -- Delete" else " -- Update"
                          Icon(imageVector = icon, contentDescription = "Icon", modifier = Modifier.scale(scale), tint = Color.White)
                          Text(text = operationText, color = Color.White, fontSize = 12.sp)
                      }
                    }
                },
                dismissContent = {
                    ListItemRow(contactViewModel = contactViewModel, index = index)

                }
            )
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
fun ListItemRow(
    contactViewModel: ContactViewModel,
    index : Int,
) {
    val contact = contactViewModel.getContacts().get(index)
    val color = ColorGenerator.getRandomColor()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        contactViewModel.addIndexInList(index)
                    }
                )
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ContactProfilePic(contactImage = contact.contactImage)
        ContactInfo(contactName = contact.contactName, color = color)
        if (contactViewModel.checkIndexIsInList(index)) {
            CheckIcon()
        } else {
            Spacer(modifier = Modifier.size(22.dp))
        }

    }

}

@Composable
fun CheckIcon() {
    Icon(
        modifier = Modifier
            .size(22.dp),
        imageVector = Icons.Default.Check,
        contentDescription = "Item Checked",
        tint = Blue100,
    )
}

@Preview
@Composable
fun CheckIconPreview() {
    CheckIcon()
}

@Composable
fun ContactProfilePic(
    @DrawableRes contactImage: Int
) {
    Image(
        modifier = Modifier
            .border(width = 1.dp, color = Blue100, shape = CircleShape)
            .clip(CircleShape)
            .size(60.dp),
        painter = painterResource(id = contactImage),
        contentDescription = "Contact Image",
    )
}

@Composable
fun ContactInfo(
    contactName: String,
    color: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(.9F)
            .padding(horizontal = 15.dp)
    ) {
        Text(
            text = contactName,
            fontWeight = FontWeight.SemiBold,
            color = color,
            fontSize = 18.sp
        )
    }
}

//@Preview(showSystemUi = true, showBackground = true)
//@Composable
//fun ContactListLayoutPreview() {
////    ContactProfilePic(contactImage = R.drawable.profile)
////    ContactInfo(contactName = "Faraz Sheikh")
////    CardListRowItem(contactImage = R.drawable.profile, contactName = "Faraz Sheikh")
//    ContactListLayout()
//}