package com.example.jetpack.ui.fragment.downloadmanager

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.domain.enums.HomeShortcut
import com.example.jetpack.ui.component.CoreTopBarWithHomeShortcut
import com.example.jetpack.ui.fragment.downloadwithworker.component.DownloadOption
import com.example.jetpack.util.NavigationUtil.safeNavigateUp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DownloadManagerFragment : CoreFragment() {

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        DownloadManagerLayout(
            onBack = { safeNavigateUp() },
            onDownload = { },
        )
    }
}


@Composable
fun DownloadManagerLayout(
    onBack: () -> Unit = {},
    onDownload: () -> Unit = {},
) {
    CoreLayout(
        topBar = {
            CoreTopBarWithHomeShortcut(
                homeShortcut = HomeShortcut.DownloadManager,
                iconLeft = R.drawable.ic_back,
                onLeftClick = onBack,
            )
        },
        backgroundColor = LocalTheme.current.background,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                DownloadOption(
                    title = "Download",
                    onClick = onDownload,
                )
            }
        }
    )
}

@Preview
@Composable
private fun PreviewDownloadManager() {
    DownloadManagerLayout()
}