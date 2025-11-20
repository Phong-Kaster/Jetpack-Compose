package com.example.jetpack.ui.fragment.basictextfield.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.allCaps
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.then
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.core.base.LocalTheme
import com.example.jetpack.ui.theme.customizedTextStyle


@Composable
fun CombineWithBuiltInInputTransformation(
    modifier: Modifier = Modifier
) {
    val maxSixCharacters = rememberTextFieldState()


    // Combine with built-in Input Transformation

    Column(modifier = modifier){
        Text(
            text = "Combine with built-in Input Transformation",
            style = customizedTextStyle(
                fontSize = 18, fontWeight = 700, color = Color.White
            ), modifier = Modifier.padding(vertical = 10.dp)
        )
        BasicTextField(
            state = maxSixCharacters,
            cursorBrush = SolidColor(Color.Cyan),
            lineLimits = TextFieldLineLimits.SingleLine,
            textStyle = customizedTextStyle(color = LocalTheme.current.primary),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            inputTransformation = InputTransformation.maxLength(6)
                .then(InputTransformation.allCaps(Locale.current)),
            decorator = { innerTextField ->
                if (maxSixCharacters.text.isEmpty()) {
                    Text(
                        text = "All capitalized 6 characters", style = customizedTextStyle()
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
                .padding(vertical = 16.dp, horizontal = 16.dp),
        )
    }

}

@Preview
@Composable
private fun PreviewCombineWithBuiltInInputTransformation() {
    CombineWithBuiltInInputTransformation()
}