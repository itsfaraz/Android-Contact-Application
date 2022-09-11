package com.itsfrz.authentication.ui.views.compose.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itsfrz.authentication.ui.views.compose.ui.theme.Blue100


@Composable
fun AlertBox(
    title: String,
    content: String,
    buttonOneText: String = "No",
    buttonTwoText: String = "Yes",
    buttonClickResponse: (value: Boolean) -> Unit,
    onCloseEvent: () -> Unit,
    cardModifier: Modifier = Modifier
        .fillMaxWidth(.8F)
        .wrapContentHeight()
) {
    Card(
        modifier = cardModifier,
        shape = RoundedCornerShape(10.dp),
        elevation = 15.dp
    ) {

        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopEnd
            ) {
                Card(
                    modifier = Modifier
                        .padding(vertical = 2.dp, horizontal = 10.dp)
                        .size(20.dp)
                        .clickable {
                            onCloseEvent()
                        },
                    shape = RoundedCornerShape(100.dp),
                    elevation = 5.dp
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(2.dp),
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.Gray
                    )
                }
            }

            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                text = title,
                color = Blue100,
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.h5
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(15.dp)
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                text = content,
                textAlign = TextAlign.Left,
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(15.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Card(
                    modifier = Modifier
                        .weight(1F)
                        .clickable {
                            buttonClickResponse(false)
                        },
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        text = buttonOneText,
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Card(
                    modifier = Modifier
                        .weight(1F)
                        .clickable {
                            buttonClickResponse(true)
                        },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        text = buttonTwoText,
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center,
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(15.dp)
            )
        }

    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AlertBoxPreview() {

    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {


        AlertBox(
            title = "Delete",
            content = "Are you sure, You want to delete ?",
            buttonOneText = "Just Kidding",
            buttonTwoText = "Yup",
            buttonClickResponse = {},
            onCloseEvent = {}
        )
    }

}