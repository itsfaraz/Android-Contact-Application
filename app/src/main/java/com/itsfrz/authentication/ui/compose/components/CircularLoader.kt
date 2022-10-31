package com.itsfrz.authentication.ui.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.itsfrz.authentication.ui.compose.ui.theme.Blue100

@Composable
fun CircularLoader(
    size : Dp = 40.dp
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .alpha(0.8F)
        ,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(size),
            color = Blue100,
            strokeWidth = 2.dp
        )
    }

}