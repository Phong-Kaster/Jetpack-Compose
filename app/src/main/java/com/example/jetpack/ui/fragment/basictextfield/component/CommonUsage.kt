package com.example.jetpack.ui.fragment.basictextfield.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.ui.theme.customizedTextStyle

@Composable
fun CommonUsage(

    modifier: Modifier = Modifier
) {
    val usernameState = rememberTextFieldState()
    val showError by remember(usernameState.text) {
        derivedStateOf {
            usernameState.text.contains("a")
        }
    }

    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.common_usage),
            style = customizedTextStyle(
                fontSize = 18, fontWeight = 700, color = Color.Cyan
            ), modifier = Modifier.padding(vertical = 10.dp)
        )

        BasicTextField(
            state = usernameState,
            cursorBrush = SolidColor(Color.Cyan),
            lineLimits = TextFieldLineLimits.SingleLine,
            textStyle = customizedTextStyle(color = LocalTheme.current.primary),
            decorator = { innerTextField ->
                if (usernameState.text.isEmpty()) {
                    Text(
                        text = stringResource(id = R.string.search),
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
        AnimatedVisibility(
            visible = showError, modifier = Modifier.padding(vertical = 10.dp)
        ) {
            Text(
                text = "Username is invalid, please try again !",
                style = customizedTextStyle(color = Color.Red)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewCommonUsage() {
    CommonUsage(
        modifier = Modifier
    )
}