package com.itsfrz.authentication.ui.views.compose.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itsfrz.authentication.ui.views.compose.ui.theme.Blue100
import com.itsfrz.authentication.ui.views.compose.ui.theme.Blue100UltraTrans
import com.itsfrz.authentication.ui.views.compose.ui.theme.DangerRed200

@Composable
fun Loader(
    loaderMessage: String
) {
    Card(
        elevation = 15.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.wrapContentWidth().padding(horizontal = 40.dp, vertical = 10.dp)
        ) {
            Text(
                modifier = Modifier.padding(vertical = 10.dp),
                text = loaderMessage,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray,
                textAlign = TextAlign.Center
            )
            CircularProgressIndicator(
                color = Blue100UltraTrans,
                modifier = Modifier
                    .padding(10.dp)
                    .size(20.dp),
                strokeWidth = 2.dp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoaderPreview() {
    Loader("Please wait ...")
}