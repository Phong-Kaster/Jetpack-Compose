package com.example.jetpack.ui.fragment.textfield.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.base.LocalTheme
import com.example.jetpack.ui.theme.SofiaFontFamily
import com.example.jetpack.ui.theme.customizedTextStyle

@Composable
fun AutoFill(
    modifier: Modifier = Modifier,
) {
    val autofillTextFieldState = rememberTextFieldState()
    Column(modifier = modifier) {
        // Visual Transformation
        Text(
            text = stringResource(R.string.autofill),
            style = customizedTextStyle(
                fontSize = 18,
                fontWeight = 700,
                color = Color.White,
                fontFamily = SofiaFontFamily,
            ),
            modifier = Modifier.padding(vertical = 10.dp)
        )
        BasicTextField(
            state = autofillTextFieldState,
            cursorBrush = SolidColor(Color.Cyan),
            lineLimits = TextFieldLineLimits.SingleLine,
            textStyle = customizedTextStyle(
                color = LocalTheme.current.primary,
                fontSize = 14,
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            inputTransformation = InputTransformation.maxLength(20),
            decorator = { innerTextField ->
                if (autofillTextFieldState.text.isEmpty()) {
                    Text(
                        text = "It helps to fill out forms,  checkout processes without manually typing in every detail",
                        style = customizedTextStyle()
                    )
                }
                innerTextField()
            },
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = LocalTheme.current.primary,
                    shape = RoundedCornerShape(5.dp)
                )
                .padding(vertical = 16.dp, horizontal = 16.dp)
            //.semantics { contentType = ContentType.Username },
        )
    }
}

@Preview
@Composable
private fun PreviewAutoFill(){
    AutoFill()
}
