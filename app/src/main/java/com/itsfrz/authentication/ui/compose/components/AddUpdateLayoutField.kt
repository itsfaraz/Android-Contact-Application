package com.itsfrz.authentication.ui.compose.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddUpdateField(
    value: String,
    onValueChange: (text: String) -> Unit,
    labelText: String,
    keyBoardOption: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Done
    )
) {

    val keyBoard = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(.75F),
        value = value,
        onValueChange = { name -> onValueChange(name) },
        label = { Text(text = labelText) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.White,
            cursorColor = Color.Green,
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.LightGray,
            focusedLabelColor = Color.LightGray,
            unfocusedLabelColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = keyBoardOption,
        keyboardActions = KeyboardActions(
            onDone = {
                keyBoard?.hide()
            }
        )
    )
}
