package com.example.jetpack.ui.fragment.home

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
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
import com.example.jetpack.data.enums.HomeShortcut
import com.example.jetpack.notification.LockscreenManager
import com.example.jetpack.notification.NotificationManager
import com.example.jetpack.repository.WeatherRepositoryImplement
import com.example.jetpack.ui.component.CoreBottomBar
import com.example.jetpack.ui.component.CoreDialog
import com.example.jetpack.ui.fragment.home.component.HomeDialog
import com.example.jetpack.ui.fragment.home.component.HomeShortcutItem
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.view.DigitalClock2
import com.example.jetpack.util.NavigationUtil.safeNavigate
import com.example.jetpack.util.NotificationResultLauncher
import com.example.jetpack.util.PermissionUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : CoreFragment() {
    private var showDialog by mutableStateOf(false)
    private lateinit var notificationResultLauncher: NotificationResultLauncher

    @Inject
    lateinit var weatherRepositoryImplement: WeatherRepositoryImplement

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNotificationLauncher()
        setupNotification()
    }


    private fun setupNotificationLauncher() {
        notificationResultLauncher = NotificationResultLauncher(
            activity = requireActivity(),
            registry = requireActivity().activityResultRegistry
        )
        lifecycle.addObserver(notificationResultLauncher)
    }

    private fun setupNotification() {
        //1. Request POST NOTIFICATION permission if device has Android OS from 13
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val isAccessed: Boolean = PermissionUtil.isNotiEnabled(context = requireContext())
            if (!isAccessed) {
                notificationResultLauncher.systemLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        //2. Create notification channel and setup daily notification
        NotificationManager.createNotificationChannel(context = requireContext())
        NotificationManager.sendNotification(context = requireContext())

        //3. Create lockscreen-styled notification and send it every day
        LockscreenManager.createNotificationChannel(context = requireContext())
        LockscreenManager.sendNotification(context = requireContext())
    }

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
                    HomeShortcut.Quote -> safeNavigate(R.id.toQuote)
                    HomeShortcut.AccuWeatherLocation -> safeNavigate(R.id.toAccuWeatherLocation)
                    HomeShortcut.Permissions -> { safeNavigate(R.id.toPermission) }
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