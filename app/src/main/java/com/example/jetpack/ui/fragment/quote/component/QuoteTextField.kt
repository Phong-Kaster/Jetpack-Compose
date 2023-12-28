package com.example.jetpack.ui.fragment.quote.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetpack.R
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.TextColor1
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.ViewUtil

@Composable
fun QuoteTextField(
    title: String,
    onTextChange: (String) -> Unit = {}
) {
    var content by remember { mutableStateOf("") }
    OutlinedTextField(
        value = content,
        onValueChange = {
            content = it
            onTextChange(it)
        },
        label = {
            Text(
                text = title,
                style = customizedTextStyle(),
                color = PrimaryColor
            )
        },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Background,
            focusedTextColor = TextColor1,
            focusedPlaceholderColor = Background,
            focusedIndicatorColor = PrimaryColor,
            unfocusedIndicatorColor = PrimaryColor,
            unfocusedContainerColor = Background
        )
    )
}

@Preview
@Composable
fun PreviewQuoteTextField() {
    ViewUtil.PreviewContent {
        QuoteTextField(
            title = stringResource(id = R.string.fake_title),
        )
    }
}