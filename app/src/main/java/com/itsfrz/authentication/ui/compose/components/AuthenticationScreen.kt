package com.itsfrz.authentication.ui.compose.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.itsfrz.authentication.R
import com.itsfrz.authentication.ui.utils.Helper
import com.itsfrz.authentication.ui.compose.ui.theme.Blue100

@Composable
fun AuthenticationScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        ApplicationLogo()
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Manage your contacts with total\ncontrol",
            textAlign = TextAlign.Center,
            color = Color.LightGray,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(100.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            onClick = {
                navController.navigate(
                    resId = R.id.signUpFragment,
                    args = null,
                    navOptions = Helper.navOptions
                )
            },
            colors = ButtonDefaults.buttonColors(Blue100)
        ) {
            Text(
                modifier = Modifier.padding(3.dp),
                text = "Sign Up",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            onClick = {
                navController.navigate(
                    resId = R.id.loginFragment,
                    args = null,
                    navOptions = Helper.navOptions
                )
            },
            border = BorderStroke(width = 1.dp, color = Blue100)
        ) {
            Text(
                modifier = Modifier.padding(3.dp),
                text = "Log in",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = Blue100
            )
        }
    }
}


@Composable
fun ApplicationLogo() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.contact_logo),
            contentDescription = "Application Logo",
            modifier = Modifier.size(120.dp),
            tint = Blue100
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Connect",
            fontSize = MaterialTheme.typography.h6.fontSize,
            fontWeight = FontWeight.ExtraBold,
            color = Color.DarkGray
        )
    }
}