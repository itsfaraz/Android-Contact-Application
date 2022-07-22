package com.itsfrz.authentication.ui.views.compose.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp),
)

fun Shapes.cardTopRightCornerShape(size : Dp) : RoundedCornerShape{
    return RoundedCornerShape(topEnd = size)
}


fun Shapes.cardBottomLeftCornerShape(size : Dp) : RoundedCornerShape{
    return RoundedCornerShape(bottomStart = size)
}

