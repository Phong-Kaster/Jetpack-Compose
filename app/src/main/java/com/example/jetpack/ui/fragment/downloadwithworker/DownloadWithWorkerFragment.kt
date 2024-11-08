package com.example.jetpack.ui.fragment.downloadwithworker

import android.content.ActivityNotFoundException
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.jetpack.R
import com.example.jetpack.configuration.Constant
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.domain.model.DownloadFile
import com.example.jetpack.ui.component.CoreTopBar
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.NavigationUtil.safeNavigateUp
import com.example.jetpack.worker.DownloadFileWorker
import dagger.hilt.android.AndroidEntryPoint


/**
 * # [Step by Step Guide to Download Files With WorkManager](https://proandroiddev.com/step-by-step-guide-to-download-files-with-workmanager-b0231b03efd1)
 */
@AndroidEntryPoint
class DownloadWithWorkerFragment : CoreFragment() {

    private fun startDownloadingFile(
        file: DownloadFile,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit,
        onRunning: () -> Unit
    ) {
        val data = Data.Builder()

        data.apply {
            putString(Constant.DOWNLOAD_FILE_WORKER_KEY_FILE_NAME, file.name)
            putString(Constant.DOWNLOAD_FILE_WORKER_KEY_FILE_URL, file.url)
            putString(Constant.DOWNLOAD_FILE_WORKER_KEY_FILE_TYPE, file.type)
        }

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresStorageNotLow(true)
            .setRequiresBatteryNotLow(true)
            .build()


        val downloadFileWorker = OneTimeWorkRequestBuilder<DownloadFileWorker>()
            .setConstraints(constraints)
            .setInputData(data.build())
            .build()

        val workManager = WorkManager.getInstance(requireContext())
        workManager.enqueueUniqueWork(
            "oneFileDownloadWork_${System.currentTimeMillis()}",
            ExistingWorkPolicy.KEEP,
            downloadFileWorker
        )

        workManager.getWorkInfoByIdLiveData(downloadFileWorker.id)
            .observe(this) { info ->
                info?.let {
                    when (it.state) {
                        WorkInfo.State.SUCCEEDED -> {
                            onSuccess(
                                it.outputData.getString(Constant.DOWNLOAD_FILE_WORKER_KEY_FILE_URI)
                                    ?: ""
                            )
                        }

                        WorkInfo.State.FAILED -> onFailure("Downloading failed!")
                        WorkInfo.State.RUNNING -> onRunning()
                        else -> onFailure("Something went wrong")

                    }
                }
            }
    }

    @Composable
    override fun ComposeView() {
        super.ComposeView()

        var targetFile = remember {
            mutableStateOf(
                DownloadFile(
                    id = "10",
                    name = "PDF File 10 MB",
                    type = "PDF",
                    url = "https://www.learningcontainer.com/wp-content/uploads/2019/09/sample-pdf-download-10-mb.pdf",
                    downloadedUri = null
                )
            )
        }


        DownloadWithWorkerLayout(
            downloadFile = targetFile.value,
            onStartDownload = { downloadFile: DownloadFile ->
                startDownloadingFile(
                    file = downloadFile,
                    onSuccess = { downloadedUri ->
                        targetFile.value = targetFile.value.copy(
                            isDownloading = false,
                            downloadedUri = downloadedUri
                        )
                    },
                    onFailure = { downloadedUri ->
                        targetFile.value =
                            targetFile.value.copy(isDownloading = false, downloadedUri = null)
                    },
                    onRunning = {
                        targetFile.value = targetFile.value.copy(isDownloading = true)
                    }
                )
            },
            onOpenFile = { downloadFile: DownloadFile ->
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(downloadFile.downloadedUri?.toUri(), "application/pdf")
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                try {
                    startActivity(intent)
                } catch (ex: ActivityNotFoundException) {
                    ex.printStackTrace()
                    Toast.makeText(requireContext(), "Can't open Pdf", Toast.LENGTH_SHORT).show()
                }
            },
            onBack = { safeNavigateUp() }
        )
    }
}

@Composable
fun DownloadWithWorkerLayout(
    downloadFile: DownloadFile,
    onStartDownload: (DownloadFile) -> Unit = {},
    onOpenFile: (DownloadFile) -> Unit = {},
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
        backgroundColor = LocalTheme.current.background,
        content = {
            Box(
                contentAlignment = Alignment.Center,
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
                        if (downloadFile.isDownloading)
                            return@clickable

                        if (downloadFile.downloadedUri.isNullOrEmpty())
                            onStartDownload(downloadFile)
                        else
                            onOpenFile(downloadFile)

                    }
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                    ) {
                        Text(
                            text = downloadFile.name,
                            style = customizedTextStyle(
                                fontWeight = 700,
                                fontSize = 18,
                                color = LocalTheme.current.textColor
                            ),
                        )

                        Row {
                            Text(
                                text = if (downloadFile.isDownloading)
                                    "Downloading..."
                                else
                                    if (downloadFile.downloadedUri.isNullOrEmpty()) "Tap to download the file" else "Tap to open file",
                                style = customizedTextStyle(
                                    fontWeight = 400,
                                    fontSize = 14,
                                    color = LocalTheme.current.textColor
                                ),
                            )
                        }

                    }

                    if (downloadFile.isDownloading) {
                        CircularProgressIndicator(
                            color = LocalTheme.current.primary,
                            modifier = Modifier
                                .size(32.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }
                }
            }
        }
    )
}

@Preview
@Composable
private fun PreviewDownloadWithWorker() {
    val downloadFile = DownloadFile(
        id = "10",
        name = "PDF File 10 MB",
        type = "PDF",
        url = "https://www.learningcontainer.com/wp-content/uploads/2019/09/sample-pdf-download-10-mb.pdf",
        downloadedUri = null
    )

    DownloadWithWorkerLayout(
        downloadFile = downloadFile
    )
}