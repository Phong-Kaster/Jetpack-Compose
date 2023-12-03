package com.example.jetpack.ui.fragment.home

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
import com.example.jetpack.ui.component.CoreBottomBar
import com.example.jetpack.ui.fragment.home.component.HomeTopBar
import com.example.jetpack.ui.theme.Background
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : CoreFragment() {

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        HomeLayout()
    }
}


@Composable
fun HomeLayout() {
    CoreLayout(
        topBar = { HomeTopBar(name = stringResource(id = Menu.Home.nameId)) },
        bottomBar = { CoreBottomBar() },
        backgroundColor = Background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_bottom_home),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewHome() {
    HomeLayout()
}