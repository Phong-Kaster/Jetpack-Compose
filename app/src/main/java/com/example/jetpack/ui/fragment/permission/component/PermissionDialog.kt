package com.example.jetpack.ui.fragment.permission.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.jetpack.R
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.JetpackComposeTheme
import com.example.jetpack.ui.theme.OppositePrimaryColor
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.ViewUtil.CenterColumn

@Composable
fun PermissionPopup(
    enable: Boolean = true,
    @StringRes title: Int = R.string.fake_title,
    @StringRes content: Int = R.string.fake_message,
    goSetting: () -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    if (enable) {
        JetpackComposeTheme {
            Dialog(
                onDismissRequest = {
                    onDismiss()
                }
            ) {
                CenterColumn(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(color = Background)
                        .padding(horizontal = 24.dp, vertical = 24.dp),
                    itemSpacing = 12.dp
                ) {
                    Text(
                        text = stringResource(title),
                        color = PrimaryColor,
                        style = customizedTextStyle(16, 700),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = stringResource(content),
                        color = PrimaryColor,
                        style = customizedTextStyle(14, 500),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = stringResource(R.string.go_to_settings),
                        style = customizedTextStyle(18, 700),
                        color = OppositePrimaryColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(15.dp))
                            .background(color = PrimaryColor)
                            .clickable {
                                goSetting()
                            }
                            .padding(vertical = 10.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewPermissionPopup() {
    PermissionPopup()
}