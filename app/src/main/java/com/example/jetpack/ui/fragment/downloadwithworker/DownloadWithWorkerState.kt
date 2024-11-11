package com.example.jetpack.ui.fragment.downloadwithworker

import androidx.compose.runtime.Stable

@Stable
data class DownloadWithWorkerState(
    var id: String = "10",
    var fileName: String = "PDF File",
    var fileType: String = "PDF",
    var fileLink: String = "https://www.learningcontainer.com/wp-content/uploads/2019/09/sample-pdf-download-10-mb.pdf",
    var fileUri: String? = null,
    var mimeType: String? = "application/pdf",
    var isDownloading: Boolean = false
)

