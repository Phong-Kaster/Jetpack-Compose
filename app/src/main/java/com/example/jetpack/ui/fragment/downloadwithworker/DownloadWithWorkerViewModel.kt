package com.example.jetpack.ui.fragment.downloadwithworker

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.jetpack.JetpackApplication
import com.example.jetpack.configuration.Constant
import com.example.jetpack.worker.DownloadFileWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DownloadWithWorkerViewModel
@Inject
constructor(
    private val applicationContext: JetpackApplication
) : ViewModel() {

    private val TAG = this.javaClass.simpleName

    private var _state = MutableStateFlow(DownloadWithWorkerState())
    val state = _state.asStateFlow()

    init {
        //choosePDFtoDownload()

    }

/*     fun choosePDFtoDownload(){
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(
                id = "1",
                fileName = "PDF Example",
//                fileType = Constant.PDF.first,
                fileType = "pdf",
                fileLink = "https://www.learningcontainer.com/wp-content/uploads/2019/09/sample-pdf-download-10-mb.pdf",
                fileUri = null,
                isDownloading = false,
//                mimeType = Constant.PDF.second,
                mimeType = "application/pdf",
            )
            Log.d(TAG, "state = ${_state.value}")
        }
    }*/

    fun chooseVideotoDownload(){
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(
                id = "2",
                fileName = "Video Example",
                fileType = Constant.MP4.first,
                fileLink = "https://file-examples.com/storage/fe91352fe66730de9982024/2017/04/file_example_MP4_1920_18MG.mp4",
                fileUri = null,
                isDownloading = false,
                mimeType = Constant.MP4.second
            )
        }
    }


    fun startDownloadingFile(
        lifecycleOwner: LifecycleOwner,
    ) {
        Log.d(TAG, "startDownloadingFile")
        val data = Data.Builder()

        data.apply {
            putString(Constant.DOWNLOAD_FILE_WORKER_KEY_FILE_NAME, _state.value.fileName)
            putString(Constant.DOWNLOAD_FILE_WORKER_KEY_FILE_URL, _state.value.fileLink)
            putString(Constant.DOWNLOAD_FILE_WORKER_KEY_FILE_TYPE, _state.value.fileType)
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

        val workManager = WorkManager.getInstance(applicationContext)
        workManager.enqueueUniqueWork(
            "oneFileDownloadWork_${System.currentTimeMillis()}",
            ExistingWorkPolicy.KEEP,
            downloadFileWorker
        )

        try {
            workManager
                .getWorkInfoByIdLiveData(downloadFileWorker.id)
                .observe(lifecycleOwner) { info ->
                    Log.d(TAG, "startDownloadingFile - info: $info")
                    info?.let {
                        when (it.state) {
                            WorkInfo.State.SUCCEEDED -> _state.value = _state.value.copy(isDownloading = false, fileUri = it.outputData.getString(Constant.DOWNLOAD_FILE_WORKER_KEY_FILE_URI))
                            WorkInfo.State.FAILED -> {
                                _state.value = _state.value.copy(isDownloading = false, fileUri = null)
                            }
                            WorkInfo.State.RUNNING -> _state.value = _state.value.copy(isDownloading = true)
                            else -> _state.value = _state.value.copy(isDownloading = false, fileUri = null)
                        }
                    }
                }
        }catch (ex: Exception){
            ex.printStackTrace()
        }

    }
}