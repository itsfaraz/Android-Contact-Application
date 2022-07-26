package com.itsfrz.authentication.ui.views.compose.components


import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.itsfrz.authentication.R
import com.itsfrz.authentication.ui.views.Screen
import com.itsfrz.authentication.ui.views.compose.ui.theme.*

@Composable
fun PermissionScreen(
    navController: NavController
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(.1f)
            )
            Card(
                shape = Shapes.cardBottomLeftCornerShape(100.dp),
                backgroundColor = Blue100
            ) {
                UpperLayout(
                    permissionCardIcon = R.drawable.permission_lock_icon,
                    permissionCardBackgroundColor = Color.White,
                    permissionIconColor = Blue100,
                    lowerCardColor = Blue100UltraTrans,
                    lowerCardShapeSize = 100.dp
                )
            }
        }
        Spacer(
            modifier = Modifier
                .height(40.dp)
        )
        PermissionInfo()
        Spacer(
            modifier = Modifier
                .height(40.dp)
        )
        Button(
            colors = ButtonDefaults.buttonColors(Blue100),
            onClick = { navController.navigate(Screen.AuthenticationScreen.route) },
            shape = RoundedCornerShape(20.dp),
        ) {
            Text(
                text = "Allow Permission",
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 6.dp)
            )
        }
    }
}

@Composable
fun UpperLayout(
    modifier: Modifier = Modifier,
    @DrawableRes permissionCardIcon: Int,
    permissionIconColor: Color = Color.Cyan,
    permissionCardBackgroundColor: Color,
    lowerCardColor: Color,
    lowerCardShapeSize: Dp
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(320.dp),
        contentAlignment = Alignment.Center
    ) {
        IconCircle(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 5.dp, top = 10.dp),
            permissionCardIcon = permissionCardIcon,
            permissionIconColor = permissionIconColor,
            permissionCardBackgroundColor = permissionCardBackgroundColor
        )
        StylishCard(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .height(230.dp)
                .fillMaxWidth(.6f),
            backgroudColor = lowerCardColor,
            cardTopRightCornerShapeSize = lowerCardShapeSize
        )
    }
}

@Composable
fun IconCircle(
    @DrawableRes permissionCardIcon: Int,
    permissionCardBackgroundColor: Color,
    permissionIconColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        shape = CircleShape,
        elevation = 10.dp,
        backgroundColor = permissionCardBackgroundColor,
        modifier = modifier
            .padding(end = 30.dp)
            .size(80.dp)
    ) {
        Icon(
            painter = painterResource(id = permissionCardIcon),
            contentDescription = "Lock Icon",
            tint = permissionIconColor,
            modifier = Modifier.padding(20.dp)
        )
    }
}

@Composable
fun StylishCard(
    modifier: Modifier = Modifier,
    backgroudColor: Color,
    cardTopRightCornerShapeSize: Dp = 120.dp

) {
    Card(
        modifier = modifier
            .height(200.dp)
            .fillMaxWidth()
            .blur( radius = 4.dp),
        backgroundColor = backgroudColor,
        shape = Shapes.cardTopRightCornerShape(cardTopRightCornerShapeSize)
    ) {
    }

}

@Composable
fun PermissionInfo() {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Permission",
            fontSize = MaterialTheme.typography.h3.fontSize,
            fontFamily = MaterialTheme.typography.h3.fontFamily,
            textAlign = TextAlign.Justify,
            color = Color.LightGray
        )
        Text(
            modifier = Modifier.fillMaxWidth(.6f),
            textAlign = TextAlign.Justify,
            color = Color.LightGray,
            text = "Please allow permission in order to perform further task, App will not manipulate your data without your actions."
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PermissionScreenPreview() {
    val navController = rememberNavController()
    PermissionScreen(navController = navController)
}
