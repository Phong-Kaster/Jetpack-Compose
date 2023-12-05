package com.example.jetpack.ui.fragment.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import com.example.jetpack.ui.component.CoreDialog
import com.example.jetpack.ui.fragment.home.component.HomeDialog
import com.example.jetpack.ui.fragment.home.component.HomeTopBar
import com.example.jetpack.ui.theme.Background
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : CoreFragment() {

    private var showDialog by mutableStateOf(false)

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        /*CoreAlertDialog(
            enable = showDialog,
            onDismissRequest = {  },
            onConfirmation = {  },
            dialogTitle =  stringResource(id = R.string.fake_title),
            dialogContent = stringResource(id = R.string.fake_content),
            textButtonConfirm = "OK",
            textButtonCancel = "Cancel",
            icon = R.drawable.ic_nazi_eagle
        )*/

        HomeDialog(
            enable = showDialog,
            onDismissRequest = { showDialog = false },
            onConfirm = {},
            onCancel = {}
        )

        CoreDialog(
            enable = showDialog,
            onDismissRequest = { showDialog = false }
        )

        HomeLayout(
            onOpenConfirmDialog = {
                showDialog = !showDialog
            }
        )
    }
}


@Composable
fun HomeLayout(
    onOpenConfirmDialog: () -> Unit = {}
) {
    CoreLayout(
        topBar = { HomeTopBar(name = stringResource(id = Menu.Home.nameId)) },
        bottomBar = { CoreBottomBar() },
        backgroundColor = Background
    ) {
        BackHandler(
            enabled = true,
            onBack = onOpenConfirmDialog
        )

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