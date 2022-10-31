package com.itsfrz.authentication.ui.compose.components


import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.itsfrz.authentication.R
import com.itsfrz.authentication.ui.compose.enums.ConnectType
import com.itsfrz.authentication.ui.compose.ui.theme.*

@Composable
fun ConnectExtraLayout(
    connectClick: (type: ConnectType) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp),
        backgroundColor = Blue200,
        elevation = 25.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                ConnectExtraIcon(
                    contentSize = 40.dp,
                    roundSize = 12.dp,
                    iconId = R.drawable.ui_message_icon,
                    backgroundColor = CandyGreen
                ) {
                    connectClick(ConnectType.MESSAGE)
                }

                ConnectExtraIcon(
                    contentSize = 40.dp,
                    roundSize = 12.dp,
                    iconId = R.drawable.ui_call_icon,
                    backgroundColor = CandyRed
                ) {
                    connectClick(ConnectType.CALL)
                }

                ConnectExtraIcon(
                    contentSize = 40.dp,
                    roundSize = 12.dp,
                    iconId = R.drawable.ui_video_call_icon,
                    backgroundColor = CandyOrange
                ) {
                    connectClick(ConnectType.VIDEO)
                }

            }
            Spacer(modifier = Modifier.height(60.dp))
        }
    }

}

@Composable
fun ConnectExtraIcon(
    contentSize: Dp,
    roundSize: Dp,
    @DrawableRes iconId: Int,
    backgroundColor: Color,
    onIconClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(contentSize)
            .background(shape = RoundedCornerShape(roundSize), color = backgroundColor)
            .clickable { onIconClick() },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = "Message Icon",
            tint = Color.White
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ConnectExtraLayoutPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        ConnectExtraLayout(connectClick = { type: ConnectType ->  
            
        })
    }
}











