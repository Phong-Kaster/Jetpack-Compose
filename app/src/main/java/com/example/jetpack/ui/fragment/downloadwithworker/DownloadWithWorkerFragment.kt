package com.example.jetpack.ui.fragment.downloadwithworker

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import com.example.jetpack.R
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.ui.component.CoreTopBar
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.NavigationUtil.safeNavigateUp
import dagger.hilt.android.AndroidEntryPoint


/**
 * # [Step by Step Guide to Download Files With WorkManager](https://proandroiddev.com/step-by-step-guide-to-download-files-with-workmanager-b0231b03efd1)
 */
@AndroidEntryPoint
class DownloadWithWorkerFragment : CoreFragment() {

    private val viewModel: DownloadWithWorkerViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Composable
    override fun ComposeView() {
        super.ComposeView()

        DownloadWithWorkerLayout(
            state = viewModel.state.collectAsState().value,
            onChoosePDF = { },
            onChooseVideo = { },
            onBack = { safeNavigateUp() },
            onDownload = {
                Log.d(TAG, "onDownload")
                Log.d(TAG, "onDownload - state = ${viewModel.state.value}")
                if (viewModel.state.value.fileLink.isEmpty()) {
                    showToast(message = "File link is empty !")
                    return@DownloadWithWorkerLayout
                }
                viewModel.startDownloadingFile(lifecycleOwner = this)
            },
            onOpenFile = {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(
                    viewModel.state.value.fileUri?.toUri(),
                    viewModel.state.value.mimeType
                )
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                try {
                    startActivity(intent)
                } catch (ex: ActivityNotFoundException) {
                    ex.printStackTrace()
                    Toast.makeText(requireContext(), "Can't open", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
}

@Composable
fun DownloadWithWorkerLayout(
    state: DownloadWithWorkerState,
    onChoosePDF: () -> Unit = {},
    onChooseVideo: () -> Unit = {},
    onDownload: () -> Unit = {},
    onOpenFile: () -> Unit = {},
    onBack: () -> Unit = {},
) {
    CoreLayout(
        topBar = {
            CoreTopBar(
                title = stringResource(R.string.download_with_worker_manager),
                leftIcon = R.drawable.ic_back,
                onClickLeft = onBack
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .border(
                        width = 2.dp,
                        color = LocalTheme.current.primary,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .background(color = LocalTheme.current.background)
                    .clickable {
                        if (state.isDownloading)
                            return@clickable

                        if (state.fileUri.isNullOrEmpty())
                            onDownload()
                        else
                            onOpenFile()

                    }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                ) {
                    Text(
                        text = state.fileName,
                        style = customizedTextStyle(
                            fontWeight = 700,
                            fontSize = 18,
                            color = LocalTheme.current.textColor
                        ),
                    )

                    Row {
                        Text(
                            style = customizedTextStyle(
                                fontWeight = 400,
                                fontSize = 14,
                                color = LocalTheme.current.textColor
                            ),
                            text = if (state.isDownloading)
                                "${stringResource(R.string.downloading)}..."
                            else
                                if (state.fileUri.isNullOrEmpty())
                                    stringResource(R.string.tap_to_download)
                                else
                                    stringResource(R.string.tap_to_open),
                        )
                    }

                }

                if (state.isDownloading) {
                    CircularProgressIndicator(
                        color = LocalTheme.current.primary,
                        modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.CenterVertically)
                    )
                }
            }
        },
        backgroundColor = LocalTheme.current.background,
        content = {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    space = 16.dp,
                    alignment = Alignment.CenterVertically
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                item(
                    key = "setupDownloadPdfExample",
                    content = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(16.dp))
                                .clickable { onChoosePDF() }
                                .background(
                                    color = LocalTheme.current.primary,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Click to setup download PDF Example",
                                style = customizedTextStyle(
                                    fontWeight = 600,
                                    fontSize = 14,
                                    color = LocalTheme.current.onPrimary
                                ),
                                maxLines = 1,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .basicMarquee(Int.MAX_VALUE)
                            )
                        }
                    }
                )


                item(
                    key = "setupDownloadVideoExample",
                    content = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(16.dp))
                                .clickable { onChooseVideo() }
                                .background(
                                    color = LocalTheme.current.primary,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Click to setup download Video Example",
                                style = customizedTextStyle(
                                    fontWeight = 600,
                                    fontSize = 14,
                                    color = LocalTheme.current.onPrimary
                                ),
                                maxLines = 1,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .basicMarquee(Int.MAX_VALUE)
                            )
                        }
                    }
                )
            }
        }
    )
}

@Preview
@Composable
private fun PreviewDownloadWithWorker() {
    DownloadWithWorkerLayout(
        state = DownloadWithWorkerState()
    )
}