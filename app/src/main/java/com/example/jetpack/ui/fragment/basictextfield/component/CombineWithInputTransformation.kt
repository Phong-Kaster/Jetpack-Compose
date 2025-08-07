package com.example.jetpack.ui.fragment.basictextfield.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.base.LocalTheme
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.inputtransformation.DigitsOnlyTransformation

@Composable
fun CombineWithInputTransformation(
    modifier: Modifier = Modifier
) {

    val digitsOnly = rememberTextFieldState()

    Column(modifier = modifier) {
        // Combine with Input Transformation
        Text(
            text = stringResource(R.string.combine_with_input_transformation),
            style = customizedTextStyle(
                fontSize = 18, fontWeight = 700, color = Color.Cyan
            ), modifier = Modifier.padding(vertical = 10.dp)
        )
        BasicTextField(
            state = digitsOnly,
            cursorBrush = SolidColor(Color.Cyan),
            lineLimits = TextFieldLineLimits.SingleLine,
            textStyle = customizedTextStyle(color = LocalTheme.current.primary),
            inputTransformation = DigitsOnlyTransformation,
            decorator = { innerTextField ->
                if (digitsOnly.text.isEmpty()) {
                    Text(
                        text = stringResource(R.string.digit_only),
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
                .padding(vertical = 16.dp, horizontal = 16.dp),
        )
    }
}

@Preview
@Composable
private fun PreviewCombineWithInputTransformation() {
    CombineWithInputTransformation()
}