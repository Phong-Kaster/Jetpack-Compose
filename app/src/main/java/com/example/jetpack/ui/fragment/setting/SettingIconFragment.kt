package com.example.jetpack.ui.fragment.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.ui.component.CoreTopBar
import com.example.jetpack.ui.component.SolidButton
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.OppositePrimaryColor
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.body16
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingIconFragment : CoreFragment() {

    @Composable
    override fun ComposeView() {
        SettingIconLayout(
            onBack = {},
            onConfirm = {}
        )
    }
}

@Composable
fun SettingIconLayout(
    onBack: () -> Unit = {},
    onConfirm: () -> Unit = {}
) {
    CoreLayout(
        topBar = {
            CoreTopBar(
                title = stringResource(id = R.string.disclaimer),
                leftIcon = R.drawable.ic_back,
                onClickLeft = onBack
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Background)
                    .padding(16.dp)
            ) {
                SolidButton(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = PrimaryColor,
                    text = stringResource(id = R.string.save),
                    textColor = OppositePrimaryColor,
                    textStyle = body16.copy(fontWeight = FontWeight(700)),
                    onClick = { onConfirm() },
                    marginHorizontal = 0.dp,
                    marginVertical = 0.dp
                )
            }
        },
        content = {

        }
    )
}

@Preview
@Composable
fun PreviewSettingIconLayout() {
    SettingIconLayout()
}