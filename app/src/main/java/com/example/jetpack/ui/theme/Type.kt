package com.example.jetpack.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.jetpack.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val InterFontFamily = FontFamily(
    Font(R.font.inter_black, FontWeight.Black),
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_bold, FontWeight.Bold),
    Font(R.font.inter_extra_bold, FontWeight.ExtraBold),
    Font(R.font.inter_semi_bold, FontWeight.SemiBold),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_light, FontWeight.Light),
    Font(R.font.inter_extra_light, FontWeight.ExtraLight),
    Font(R.font.inter_thin, FontWeight.Thin),
)

val InterTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight(700),
        fontSize = 24.sp,
        lineHeight = 36.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    ),
    headlineMedium = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight(700),
        fontSize = 18.sp,
        lineHeight = 27.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    ),
    headlineSmall = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight(700),
        fontSize = 16.sp,
        lineHeight = 24.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    ),
    bodyLarge = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight(400),
        fontSize = 16.sp,
        lineHeight = 24.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    ),
    bodyMedium = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight(400),
        fontSize = 14.sp,
        lineHeight = 21.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    ),
    bodySmall = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight(400),
        fontSize = 12.sp,
        lineHeight = 18.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
    )
)

fun customizedTextStyle(
    fontSize: Int = 14,
    fontWeight: Int = 400,
    lineHeight: Int = (fontSize * 1.5f).toInt(),
    color: Color = TextColor3,
    textDecoration: TextDecoration? = null,
): TextStyle = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = fontSize.sp,
    fontWeight = FontWeight(fontWeight),
    lineHeight = lineHeight.sp,
    color = color,
    textDecoration = textDecoration
)

private val TextUnit.nonScaledSp
    @Composable
    get() = (this.value / LocalDensity.current.fontScale).sp

@Composable
fun customizedTextStyleNonScale(
    fontSize: Int = 14,
    fontWeight: Int = 400,
    lineHeight: Int = (fontSize * 1.5f).toInt(),
    color: Color = TextColor3
): TextStyle = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = fontSize.sp.nonScaledSp,
    fontWeight = FontWeight(fontWeight),
    lineHeight = lineHeight.sp,
    color = color,
)



val h38 = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 38.sp,
    lineHeight = 53.sp,
    fontWeight = FontWeight(600),
    color = Color.Black,
)

val h32 = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 32.sp,
    lineHeight = 45.sp,
    fontWeight = FontWeight(400),
    color = Color.Black,
)

val h30 = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 30.sp,
    lineHeight = 45.sp,
    fontWeight = FontWeight(400),
    color = Color.Black,
)

val h24 = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 24.sp,
    lineHeight = 36.sp,
    fontWeight = FontWeight(600),
    color = TextColor3,
)

val h20 = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 20.sp,
    lineHeight = 30.sp,
    fontWeight = FontWeight(600),
    color = TextColor3,
)

val h18 = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 18.sp,
    lineHeight = 27.sp,
    fontWeight = FontWeight(600),
    color = TextColor3,
)

val h16 = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    fontWeight = FontWeight(600),
    color = TextColor3,
)

val h14 = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 14.sp,
    lineHeight = 21.sp,
    fontWeight = FontWeight(600),
    color = TextColor3,
)

val body18 = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 18.sp,
    lineHeight = 27.sp,
    fontWeight = FontWeight(400),
    color = TextColor3,
)

val body16 = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    fontWeight = FontWeight(400),
    color = TextColor3,
)

val body15 = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 15.sp,
    lineHeight = 22.sp,
    fontWeight = FontWeight(400),
    color = TextColor3,
)

val body14 = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 14.sp,
    lineHeight = 21.sp,
    fontWeight = FontWeight(400),
    color = TextColor3,
)

val body13 = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 13.sp,
    lineHeight = 20.sp,
    fontWeight = FontWeight(400),
    color = TextColor3,
)

val body12 = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 12.sp,
    lineHeight = 18.sp,
    fontWeight = FontWeight(400),
    color = TextColor3,
)

val body10 = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 10.sp,
    lineHeight = 15.sp,
    fontWeight = FontWeight(400),
    color = TextColor3,
)

val medium12 = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 12.sp,
    lineHeight = 18.sp,
    fontWeight = FontWeight(500),
    color = TextColor3,
)

val medium13 = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 13.sp,
    lineHeight = 18.sp,
    fontWeight = FontWeight(500),
    color = TextColor3,
)

val medium14 = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 14.sp,
    lineHeight = 21.sp,
    fontWeight = FontWeight(500),
    color = TextColor3,
)

val medium15 = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 15.sp,
    lineHeight = 22.sp,
    fontWeight = FontWeight(500),
    color = TextColor3,
)

val medium16 = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    fontWeight = FontWeight(500),
    color = TextColor3,
)

val medium18 = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 18.sp,
    lineHeight = 27.sp,
    fontWeight = FontWeight(500),
    color = TextColor3,
)