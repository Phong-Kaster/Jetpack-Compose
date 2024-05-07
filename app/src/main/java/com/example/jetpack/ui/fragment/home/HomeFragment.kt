package com.example.jetpack.ui.fragment.home

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jetpack.R
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.data.enums.HomeShortcut
import com.example.jetpack.data.enums.SortOption
import com.example.jetpack.lifecycleobserver.NotificationLifecycleObserver
import com.example.jetpack.notification.LockscreenManager
import com.example.jetpack.notification.NotificationManager
import com.example.jetpack.ui.component.CoreBottomBar
import com.example.jetpack.ui.component.CoreDialog
import com.example.jetpack.ui.component.CoreFloatingMenu
import com.example.jetpack.ui.component.CoreTopBarWithScrollBehavior
import com.example.jetpack.ui.fragment.accuweather.component.SearchBar
import com.example.jetpack.ui.fragment.home.component.HomeDialog
import com.example.jetpack.ui.fragment.home.component.HomeShortcutItem
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.ShimmerItem
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.ui.view.DigitalClock2
import com.example.jetpack.ui.view.DigitalClock3
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
        // 1. Request POST NOTIFICATION permission if device has Android OS from 13
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return

        val isAccessed: Boolean = PermissionUtil.isNotiEnabled(context = requireContext())
        if (isAccessed) return
        notificationLifecycleObserver.systemLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)


        // 2. Create notification channel and setup daily notification
        NotificationManager.createNotificationChannel(context = requireContext())
        NotificationManager.sendNotification(context = requireContext())

        // 3. Create lockscreen-styled notification and send it every day
        LockscreenManager.createNotificationChannel(context = requireContext())
        LockscreenManager.sendNotification(context = requireContext())
    }

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        var loading by remember { mutableStateOf(true) }
        LaunchedEffect(key1 = loading) {
            Timer().schedule(
                object : TimerTask() {
                    override fun run() {
                        loading = false
                    }
                }, 1000
            )
        }

        HomeDialog(
            enable = showDialog,
            onDismissRequest = { showDialog = false },
            onConfirm = {
                requireActivity().finish()
                showToast(getString(R.string.good_bye))
            },
        )

        HomeLayout(
            loading = loading,
            shortcuts = viewModel.shortcutsWithLifecycle.collectAsStateWithLifecycle().value,
            onOpenConfirmDialog = { showDialog = !showDialog },
            onChangeKeyword = { viewModel.searchWithKeyword(it) },
            onSearchKeyword = { viewModel.searchWithKeyword(it) },
            onClearKeyword = { viewModel.resetShortcuts() },
            onApplySortOption = { viewModel.applySortOption(it) },
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
                    HomeShortcut.ForegroundService -> safeNavigate(R.id.toForegroundService)
                    HomeShortcut.BasicTextField2 -> safeNavigate(R.id.toBasicTextField2)
                    HomeShortcut.CollapsibleTopBar -> safeNavigate(R.id.toCollasibleTopbar)
                    HomeShortcut.SharedElementTransition -> safeNavigate(R.id.toSharedElementTransition)
                    HomeShortcut.Article -> safeNavigate(R.id.toArticleRead)
                    else -> {}
                }
            })
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeLayout(
    loading: Boolean,
    shortcuts: kotlinx.collections.immutable.ImmutableList<HomeShortcut> = persistentListOf(),
    onOpenConfirmDialog: () -> Unit = {},
    onOpenShortcut: (HomeShortcut) -> Unit = {},
    onChangeKeyword: (String) -> Unit = {},
    onSearchKeyword: (String) -> Unit = {},
    onClearKeyword: () -> Unit = {},
    onApplySortOption: (SortOption) -> Unit = {}
) {

    var expandSortMenu by remember { mutableStateOf(false) }

    // for using top bar with scroll behavior
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)

    BackHandler(enabled = true, onBack = onOpenConfirmDialog)

    CoreLayout(
        backgroundColor = Background,
        topBar = {
            CoreTopBarWithScrollBehavior(
                backgroundColor = PrimaryColor,
                scrolledContainerColor = PrimaryColor,
                scrollBehavior = scrollBehavior,
                navigationIconContent = {},
                modifier = Modifier.clip(shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 5.dp, bottomEnd = 5.dp)),
                content = {
                    DigitalClock3(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                            .statusBarsPadding(),
                        textColor = Background
                    )
                }
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
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            item(key = "searchBarAndSortMenu") {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                ) {
                    SearchBar(
                        onChangeKeyword = onChangeKeyword,
                        onSearchKeyword = onSearchKeyword,
                        onClearKeyword = onClearKeyword,
                        leadingIcon = R.drawable.ic_search,
                        modifier = Modifier.weight(0.8F)
                    )

                    // Sort Menu
                    IconButton(
                        onClick = { expandSortMenu = true },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                tint = PrimaryColor,
                                contentDescription = stringResource(id = R.string.icon),
                            )

                            DropdownMenu(
                                expanded = expandSortMenu,
                                onDismissRequest = { expandSortMenu = false }
                            ) {
                                SortOption.entries.forEach { option ->
                                    DropdownMenuItem(
                                        leadingIcon = {
                                            Icon(
                                                painter = painterResource(id = option.leadingIcon),
                                                tint = PrimaryColor,
                                                contentDescription = stringResource(id = R.string.icon)
                                            )
                                        },
                                        text = {
                                            Text(
                                                text = stringResource(id = option.text),
                                                style = customizedTextStyle(color = PrimaryColor)
                                            )
                                        },
                                        onClick = {
                                            expandSortMenu = false
                                            onApplySortOption(option)
                                        }
                                    )
                                }
                            }
                        })


                }

            }


            items(
                items = shortcuts,
                key = { item: HomeShortcut -> item.name },
                itemContent = { homeShortcut: HomeShortcut ->
                    when (homeShortcut) {
                        HomeShortcut.AccuWeatherLocation -> {
                            ShimmerItem(
                                loading = loading,
                                content = {
                                    HomeShortcutItem(
                                        shortcut = homeShortcut,
                                        onClick = onOpenShortcut
                                    )
                                })
                        }

                        else -> HomeShortcutItem(shortcut = homeShortcut, onClick = onOpenShortcut)
                    }
                })
        }
    }


}

@Preview
@Composable
fun PreviewHome() {
    HomeLayout(
        shortcuts = HomeShortcut.entries.toImmutableList(),
        loading = false
    )
}

@Preview
@Composable
fun PreviewHomeWithLoading() {
    HomeLayout(
        shortcuts = HomeShortcut.entries.toImmutableList(),
        loading = true
    )
}