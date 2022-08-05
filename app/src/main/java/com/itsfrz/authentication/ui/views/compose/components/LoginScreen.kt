package com.itsfrz.authentication.ui.views.compose.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.itsfrz.authentication.R
import com.itsfrz.authentication.ui.viewmodel.LoginViewModel
import com.itsfrz.authentication.ui.views.compose.ui.theme.Blue100


@Composable
fun LoginScreen(
    navController: NavController
) {
    val loginViewModel = LoginViewModel()

    var username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        Icon(
            painter = painterResource(id = R.drawable.contact_logo),
            contentDescription = "Application Logo",
            modifier = Modifier.size(60.dp),
            tint = Blue100
        )
        Spacer(modifier = Modifier.height(30.dp))
        InputField(
            modifier = Modifier.padding(horizontal = 40.dp),
            placeHolder = "Username ...",
            label = "username",
            hasError = loginViewModel.validateUsername(username),
            hasTrailingIcon = true,
            trailingIcon = R.drawable.ui_login_icon,
            errorMessage = "Only alphanumeric character allowed",
            inputText = username,
            generatedText = { username = it }
        )
        Spacer(modifier = Modifier.height(20.dp))
        InputField(
            modifier = Modifier.padding(horizontal = 40.dp),
            placeHolder = "Password ...",
            label = "password",
            hasError = loginViewModel.validatePassword(password),
            hasTrailingIcon = true,
            trailingIcon = R.drawable.ui_password_icon,
            errorMessage = "Only numbers are allowed",
            inputText = password,
            generatedText = { password = it },
            keyboardType = KeyboardType.NumberPassword
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            onClick = { navController.navigate(R.id.contactFragment) },
            colors = ButtonDefaults.buttonColors(Blue100)
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = "Log in",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        PrivacyPolicy()
        Spacer(modifier = Modifier.height(50.dp))
        SignUpText()
    }
}

@Composable
fun SignUpText() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "Don't have an account ? ")
        Text(
            modifier = Modifier
                .clickable {},
            color = Blue100,
            text = "Sign Up",
            fontWeight = FontWeight.SemiBold
        )
    }
}

