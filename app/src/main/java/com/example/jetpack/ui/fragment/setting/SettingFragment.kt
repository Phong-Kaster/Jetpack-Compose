package com.example.jetpack.ui.fragment.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.configuration.Menu
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.ui.component.BottomBar
import com.example.jetpack.ui.component.HomeTopBar
import com.example.jetpack.ui.theme.Background
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : CoreFragment() {

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        SettingLayout()
    }
}

@Composable
fun SettingLayout() {
    CoreLayout(
        topBar = { HomeTopBar(name = stringResource(id = Menu.Setting.nameId)) },
        bottomBar = { BottomBar() },
        backgroundColor = Background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_bottom_settings),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewSettingLayout() {
    SettingLayout()
}