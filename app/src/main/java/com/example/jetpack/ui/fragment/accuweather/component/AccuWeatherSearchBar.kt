package com.example.jetpack.ui.fragment.accuweather.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpack.R
import com.example.jetpack.ui.theme.PrimaryColor

@Composable
fun SearchBar(
    queryValue: String,
    onTextChangeAutocompleteSearch: (String) -> Unit = {},
    onSearch: (String) -> Unit = {}
) {
    var query by remember { mutableStateOf(queryValue) }
    val focusManager = LocalFocusManager.current


    /*SEARCH VIEW*/
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .padding(vertical = 0.dp)
            .height(50.dp)
            .padding(horizontal = 10.dp)
            .drawBehind {
                drawLine(
                    color = PrimaryColor,
                    start = Offset(x = 0F, y = this.size.height),
                    end = Offset(x = size.width, y = this.size.height)
                )
            },
    ) {
        Icon(
            painterResource(id = R.drawable.ic_nazi_symbol),
            contentDescription = null,
            tint = PrimaryColor,
            modifier = Modifier.size(20.dp)
        )

        TextField(
            value = query,
            onValueChange = {
                query = it
                onTextChangeAutocompleteSearch(it)
            },
            textStyle = TextStyle(fontSize = 14.sp),
            maxLines = 1,
            singleLine = true,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.fake_title),
                    color = PrimaryColor.copy(alpha = 0.3f),
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.inter_black)),
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            colors = TextFieldDefaults.colors(
                cursorColor = Color.Red,
                focusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                focusedTextColor = Color.White,
                unfocusedContainerColor = Color.Transparent,
                unfocusedTextColor = Color.White,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    onSearch(query)
                }
            ),
        )
    }
}

@Preview
@Composable
fun PreviewSearchBar() {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.DarkGray)
    ) {
        SearchBar(
            onSearch = {},
            onTextChangeAutocompleteSearch = {},
            queryValue = ""
        )
    }
}