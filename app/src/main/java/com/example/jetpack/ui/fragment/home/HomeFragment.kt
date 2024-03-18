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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.example.jetpack.R
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.data.enums.HomeShortcut
import com.example.jetpack.lifecycleobserver.NotificationLifecycleObserver
import com.example.jetpack.notification.LockscreenManager
import com.example.jetpack.notification.NotificationManager
import com.example.jetpack.ui.component.CoreBottomBar
import com.example.jetpack.ui.component.CoreDialog
import com.example.jetpack.ui.component.CoreFloatingMenu
import com.example.jetpack.ui.fragment.accuweather.component.SearchBar
import com.example.jetpack.ui.fragment.home.component.HomeDialog
import com.example.jetpack.ui.fragment.home.component.HomeShortcutItem
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.ShimmerItem
import com.example.jetpack.ui.view.DigitalClock2
import com.example.jetpack.util.NavigationUtil.safeNavigate
import com.example.jetpack.util.PermissionUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import java.util.Timer
import java.util.TimerTask


@AndroidEntryPoint
class HomeFragment : CoreFragment() {
    private val viewModel: HomeViewModel by viewModels()
    private var showDialog by mutableStateOf(false)
    private lateinit var notificationLifecycleObserver: NotificationLifecycleObserver


    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNotificationLauncher()
        setupNotification()
    }


    private fun setupNotificationLauncher() {
        notificationLifecycleObserver = NotificationLifecycleObserver(
            activity = requireActivity(),
            registry = requireActivity().activityResultRegistry
        )
        lifecycle.addObserver(notificationLifecycleObserver)
    }

    private fun setupNotification() {
        //1. Request POST NOTIFICATION permission if device has Android OS from 13
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val isAccessed: Boolean = PermissionUtil.isNotiEnabled(context = requireContext())
            if (!isAccessed) {
                notificationLifecycleObserver.systemLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
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
            shortcuts = viewModel.shortcuts.collectAsState().value,
            onOpenConfirmDialog = { showDialog = !showDialog },
            onChangeKeyword = { viewModel.searchWithKeyword(it) },
            onSearchKeyword = { viewModel.searchWithKeyword(it) },
            onClearKeyword = { viewModel.resetShortcuts() },
            onOpenShortcut = {
                when (it) {
                    HomeShortcut.Tutorial -> safeNavigate(R.id.toTutorial)
                    HomeShortcut.Quote -> safeNavigate(R.id.toQuote)
                    HomeShortcut.AccuWeatherLocation -> safeNavigate(R.id.toAccuWeatherLocation)
                    HomeShortcut.Permissions -> safeNavigate(R.id.toPermission)
                    HomeShortcut.Permissions2 -> safeNavigate(R.id.toPermission2)
                    HomeShortcut.MotionLayout -> safeNavigate(R.id.toMotionLayout)
                    HomeShortcut.Login -> safeNavigate(R.id.toLogin)
                    HomeShortcut.Bluetooth -> safeNavigate(R.id.toBluetooth)
                    HomeShortcut.Webview -> safeNavigate(R.id.toWebview)
                    else -> {}
                }
            }
        )
    }
}


@Composable
fun HomeLayout(
    shortcuts: kotlinx.collections.immutable.ImmutableList<HomeShortcut> = persistentListOf(),
    onOpenConfirmDialog: () -> Unit = {},
    onOpenShortcut: (HomeShortcut) -> Unit = {},
    onChangeKeyword: (String) -> Unit = {},
    onSearchKeyword: (String) -> Unit = {},
    onClearKeyword: () -> Unit = {}
) {
    var loading by remember { mutableStateOf(true) }


    LaunchedEffect(key1 = loading) {
        Timer().schedule(
            object : TimerTask() {
                override fun run() {
                    loading = false
                }
            },
            1000
        )
    }

    BackHandler(
        enabled = true,
        onBack = onOpenConfirmDialog
    )


    CoreLayout(
        backgroundColor = Background,
        topBar = {
            DigitalClock2(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            )
        },
        bottomBar = { CoreBottomBar() },
        floatingActionButton = { CoreFloatingMenu() },
        modifier = Modifier.then(
            if (loading) {
                Modifier.blur(10.dp)
            } else {
                Modifier
            }
        )
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 32.dp)
                .fillMaxSize()
        ) {
            item(key = "searchBar") {
                SearchBar(
                    onChangeKeyword = onChangeKeyword,
                    onSearchKeyword = onSearchKeyword,
                    onClearKeyword = onClearKeyword,
                    leadingIcon = R.drawable.ic_search,
                )
            }


            items(
                items = shortcuts,
                key = { item: HomeShortcut -> item.name },
                itemContent = { homeShortcut: HomeShortcut ->
                    ShimmerItem(
                        loading = loading,
                        content = {
                            HomeShortcutItem(
                                shortcut = homeShortcut,
                                onClick = onOpenShortcut
                            )
                        })
                })
        }
    }


}

@Preview
@Composable
fun PreviewHome() {
    HomeLayout(
        shortcuts = HomeShortcut.entries.toImmutableList()
    )
}