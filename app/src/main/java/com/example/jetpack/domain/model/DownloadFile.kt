package com.example.jetpack.domain.model

/**
 * # [Step by Step Guide to Download Files With WorkManager](https://proandroiddev.com/step-by-step-guide-to-download-files-with-workmanager-b0231b03efd1)
 */
data class DownloadFile(
    val id: String,
    val name: String,
    val type: String,
    val url: String,
    var downloadedUri: String? = null,
    var isDownloading: Boolean = false,
)