package com.itsfrz.authentication.ui.views.compose.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itsfrz.authentication.R
import com.itsfrz.authentication.ui.views.compose.ui.theme.Blue100


@Composable
fun SignUpScreen() {

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
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            value = "",
            onValueChange = {},
            label = { "Full Name" }
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            value = "",
            onValueChange = {},
            label = { "Email" },
            placeholder = { "Email" }
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            value = "",
            onValueChange = {},
            label = { "Password" },
            placeholder = { "Password" }
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(Blue100)
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = "Sign Up",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        PrivacyPolicy()
        Spacer(modifier = Modifier.height(50.dp))
        LoginText()
    }
}

@Composable
fun LoginText() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "Already have an account ? ")
        Text(
            modifier = Modifier
                .clickable{},
            color = Blue100,
            text = "Log in",
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun PrivacyPolicy() {
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.DarkGray)){
                    append("By signing up you accept then")
                }
                withStyle(style = SpanStyle(color = Blue100)) {
                    append(" Terms of Service")
                }
                withStyle(style = SpanStyle(color = Color.DarkGray)){
                    append(" and")
                }
                withStyle(style = SpanStyle(color = Blue100)) {
                    append("\nPrivacy Policy")
                }
            },
            lineHeight = 25.sp,
            textAlign = TextAlign.Center
        )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen()
}