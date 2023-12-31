package com.example.jetpack.ui.fragment.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.database.enums.HomeShortcut
import com.example.jetpack.ui.component.CoreBottomBar
import com.example.jetpack.ui.component.CoreDialog
import com.example.jetpack.ui.fragment.home.component.HomeDialog
import com.example.jetpack.ui.fragment.home.component.HomeShortcutItem
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.view.DigitalClock2
import com.example.jetpack.util.NavigationUtil.safeNavigate
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
            onConfirm = {
                requireActivity().finish()
                showToast(getString(R.string.good_bye))
            },
        )

        CoreDialog(
            enable = showDialog,
            onDismissRequest = { showDialog = false }
        )

        HomeLayout(
            onOpenConfirmDialog = { showDialog = !showDialog },
            onOpenShortcut = {
                when (it) {
                    HomeShortcut.Tutorial -> safeNavigate(R.id.toTutorial)
                    else -> {}
                }
            }
        )
    }
}


@Composable
fun HomeLayout(
    onOpenConfirmDialog: () -> Unit = {},
    onOpenShortcut: (HomeShortcut) -> Unit = {}
) {
    CoreLayout(
        bottomBar = { CoreBottomBar() },
        backgroundColor = Background,
        topBar = {
            DigitalClock2(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            )
        },
    ) {
        BackHandler(
            enabled = true,
            onBack = onOpenConfirmDialog
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 32.dp)
                .fillMaxSize()
        ) {
            /* item(key = "digitalClock", span = { GridItemSpan(2) }) {
                 DigitalClock2 ()
             }*/

            items(
                items = HomeShortcut.entries,
                key = { item: HomeShortcut -> item.name },
                itemContent = { it ->
                    HomeShortcutItem(
                        shortcut = it,
                        onClick = onOpenShortcut
                    )
                })
        }
    }


}

@Preview
@Composable
fun PreviewHome() {
    HomeLayout()
}