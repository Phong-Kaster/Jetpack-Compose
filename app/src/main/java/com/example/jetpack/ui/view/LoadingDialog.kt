package com.example.jetpack.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.jetpack.R
import com.example.jetpack.util.ViewUtil

/**
 * @author Phong-Apero
 * show a loading animation with circular
 * */
@Composable
fun LoadingDialog(
    enable: Boolean,
    modifier: Modifier = Modifier,
) {
    if (enable) {
        Dialog(
            onDismissRequest = {},
            content = {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier
                        .background(color = Color(0xFF3D4145), shape = RoundedCornerShape(10.dp))
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.width(30.dp),
                        color = Color.Cyan,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                    Text(
                        text = stringResource(id = R.string.fake_title),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.inter_black))
                        ),
                        modifier = Modifier
                            .fillMaxWidth(),
                    )
                }
            })
    }
}

@Preview
@Composable
fun PreviewLoadingAnimationCircular() {
    ViewUtil.PreviewContent {
        LoadingDialog(enable = true)
    }
}