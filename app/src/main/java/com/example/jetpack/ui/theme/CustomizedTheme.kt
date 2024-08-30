package com.example.jetpack.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * We can define a customized theme like the following below:
 * Next, we need to open Core Fragment to provide the theme as reference to whole app
 */
data class CustomizedTheme(
    val primary: Color,
    val second: Color,
    val background: Color,
    val error: Color
)

val DarkCustomizedTheme = CustomizedTheme(
    primary = Color(0xFF9EFFFF),
    second = Color(0xFF004BDC),
    background = Color(0xFF2C3141),
    error = Color(0xFFFF5449)
)

