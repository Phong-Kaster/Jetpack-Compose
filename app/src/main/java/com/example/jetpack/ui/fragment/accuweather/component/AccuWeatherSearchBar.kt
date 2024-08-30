package com.example.jetpack.ui.fragment.accuweather.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
    modifier: Modifier = Modifier,
    onChangeKeyword: (String) -> Unit = {},
    onSearchKeyword: (String) -> Unit = {},
    onClearKeyword: () -> Unit = {},
    @DrawableRes leadingIcon: Int? = R.drawable.ic_search,
) {
    var keyword by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current


    /*SEARCH VIEW*/
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(vertical = 0.dp)
            .height(50.dp)
            .padding(horizontal = 10.dp)
            .drawBehind {
                drawLine(
                    color = Color.White,
                    start = Offset(x = 0F, y = this.size.height),
                    end = Offset(x = size.width, y = this.size.height)
                )
            },
    ) {


        TextField(
            value = keyword,
            onValueChange = {
                keyword = it
                onChangeKeyword(it)
            },
            textStyle = TextStyle(fontSize = 14.sp),
            maxLines = 1,
            singleLine = true,
            leadingIcon = {
                if (leadingIcon != null) {
                    Icon(
                        painter = painterResource(id = leadingIcon),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            },
            trailingIcon = {
                if (keyword.isNotBlank()) {
                    IconButton(
                        onClick = {
                            keyword = ""
                            onClearKeyword()
                        },
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_close),
                                contentDescription = null,
                                tint = PrimaryColor,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    )
                }
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.search),
                    color = Color.White.copy(alpha = 0.3f),
                    textAlign = TextAlign.Start,
                    fontFamily = FontFamily(Font(R.font.inter_black)),
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                )
            },
            colors = TextFieldDefaults.colors(
                cursorColor = Color.Cyan,
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
                    onSearchKeyword(keyword)
                }
            ),
            modifier = Modifier.fillMaxWidth(),
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
            modifier = Modifier.fillMaxWidth(),
            onSearchKeyword = {},
            onChangeKeyword = {},
            leadingIcon = R.drawable.ic_search,
        )
    }
}