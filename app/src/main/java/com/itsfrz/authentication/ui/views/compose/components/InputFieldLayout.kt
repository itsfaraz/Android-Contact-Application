package com.itsfrz.authentication.ui.views.compose.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.itsfrz.authentication.R
import com.itsfrz.authentication.ui.views.compose.ui.theme.Blue100
import com.itsfrz.authentication.ui.views.compose.ui.theme.DangerRed100
import com.itsfrz.authentication.ui.views.compose.ui.theme.DangerRed200

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    placeHolder : String,
    label : String,
    hasError : Boolean,
    errorMessage : String,
    hasTrailingIcon : Boolean,
    @DrawableRes trailingIcon : Int,
    inputText : String,
    generatedText : (String) -> Unit,
    isSingleLine : Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text
) {

    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = inputText,
            onValueChange = { generatedText(it) },
            label = { Text(text = label) },
            placeholder = { Text(text = placeHolder) },
            trailingIcon = {
                if (hasError){
                    Icon(painter = painterResource(
                        id = R.drawable.ui_error_icon),
                        contentDescription = "Trailing Icon",
                        tint = DangerRed100
                    )
                }else{
                   if (hasTrailingIcon){
                       Icon(
                           painter = painterResource(id = trailingIcon),
                           contentDescription = "Trailing Icon",
                           tint = Blue100
                       )
                   }
                }

            },
            singleLine = isSingleLine,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
        )
        if (hasError){
            Text(
                modifier = modifier
                    .fillMaxWidth(),
                text = errorMessage,
                color = DangerRed200,
                style = MaterialTheme.typography.caption)
        }
    }
}

@Preview()
@Composable
fun InputFieldPreview() {
    var userdata by remember {
        mutableStateOf("")
    }

    var userdataValid by remember {
        mutableStateOf(false)
    }
    InputField(
        placeHolder = "Username ....",
        label = "username",
        hasError = userdataValid,
        errorMessage = "Username should be in Alphabetic characters",
        hasTrailingIcon = true,
        trailingIcon = R.drawable.ui_login_icon,
        inputText =userdata,
        generatedText = {
            if (it.isNotBlank() && checkValidUsername(it)){
                userdataValid = true
            }
            userdata = it
        }

    )
}

//Testing Viewmodel Function
fun checkValidUsername(username: String): Boolean {
    val pattern = Regex("")
    return pattern.matches(username)
}
