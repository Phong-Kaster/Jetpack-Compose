package com.example.jetpack.ui.fragment.downloadmanager

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.base.CoreFragment
import com.example.jetpack.core.base.CoreLayout
import com.example.jetpack.core.base.LocalTheme
import com.example.jetpack.domain.enums.HomeShortcut
import com.example.jetpack.notification.NotificationManager
import com.example.jetpack.ui.component.CoreTopBarWithHomeShortcut
import com.example.jetpack.ui.fragment.downloadwithworker.component.DownloadOption
import com.example.jetpack.util.AppUtil.showToast
import com.example.jetpack.util.NavigationUtil.safeNavigateUp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DownloadManagerFragment : CoreFragment() {

    private lateinit var downloadManager: DownloadManager
    private var downloadId: Long = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NotificationManager.createNotificationChannel(context = requireContext())
    }

    private fun enqueueDownloadRequest(
        url: String,
        nameWithExtension: String,
        title: String = "Bundeswehr",
        description: String = "Downloading a file using DownloadManager",

    ) {
        if (url.isEmpty()) {
            requireContext().showToast("Invalid URL")
            return
        }

        downloadManager = requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        /*val request: DownloadManager.Request = DownloadManager.Request(Uri.parse(url))
        with(request) {
            setTitle("Test pdf")
            setMimeType("pdf")
            setDescription("Downloading pdf...")
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "Download-Manager-PDF-Example.pdf"
            )
        }*/

        val uri = Uri.parse(url)
        val request = DownloadManager.Request(uri)
            .setTitle(title)
            .setDescription(description)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, nameWithExtension)

        downloadId = downloadManager.enqueue(request)
    }

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        DownloadManagerLayout(
            onBack = { safeNavigateUp() },
            onDownload = {
                enqueueDownloadRequest(
                    url = "https://file-examples.com/storage/fec85039006734629a992d7/2017/04/file_example_MP4_1920_18MG.mp4",
                    nameWithExtension = "videoExample.mp4",
                    title = "Download video",
                    description = "Downloading Video File using Download manager"
                )
                enqueueDownloadRequest(
                    url = "https://www.learningcontainer.com/wp-content/uploads/2019/09/sample-pdf-download-10-mb.pdf",
                    nameWithExtension = "pdfExample.pdf",
                    title = "Download PDF",
                    description = "Downloading PDF File using Download manager")
            },
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