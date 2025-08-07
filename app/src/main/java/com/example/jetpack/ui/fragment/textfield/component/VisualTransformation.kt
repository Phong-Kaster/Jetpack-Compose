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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.core.base.LocalTheme
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.inputtransformation.VerificationCodeTransformation

@Composable
fun VisualTransformation(
    modifier: Modifier = Modifier
) {
    val visualTransformation = rememberTextFieldState()

    Column(modifier = modifier) {
        // Visual Transformation
        Text(
            text = "Visual transformation | OutputTransformation",
            style = customizedTextStyle(
                fontSize = 18,
                fontWeight = 700,
                color = Color.White
            ),
            modifier = Modifier.padding(vertical = 10.dp)
        )
        BasicTextField(
            state = visualTransformation,
            cursorBrush = SolidColor(Color.Cyan),
            lineLimits = TextFieldLineLimits.SingleLine,
            textStyle = customizedTextStyle(
                color = LocalTheme.current.primary,
                fontSize = 14
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            inputTransformation = InputTransformation.maxLength(6),
            outputTransformation = VerificationCodeTransformation,
            decorator = { innerTextField -> innerTextField() },
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = LocalTheme.current.primary,
                    shape = RoundedCornerShape(5.dp)
                )
                .padding(vertical = 16.dp, horizontal = 16.dp),
        )
    }

}

@Preview
@Composable
private fun PreviewVisualTransformation() {
    VisualTransformation()
}