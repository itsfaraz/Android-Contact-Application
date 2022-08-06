package com.itsfrz.authentication.ui.utils

import androidx.compose.ui.graphics.Color
import com.itsfrz.authentication.ui.views.compose.ui.theme.*

object ColorGenerator {
    private val colors = listOf<Color>(
        Color1, Color2, Color3, Color4, Color5, Color6,
        Color7, Color8, Color9, Color10, Color11, Color12,
        Color13, Color14, Color15, Color16
        )


    fun getRandomColor(): Color {
        return colors[(0..colors.lastIndex).random()]
    }
}