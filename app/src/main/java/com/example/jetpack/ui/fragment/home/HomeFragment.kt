package com.example.jetpack.ui.fragment.home

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import com.example.jetpack.ui.component.CoreBottomBar
import com.example.jetpack.ui.component.CoreDialog
import com.example.jetpack.ui.fragment.home.component.HomeDialog
import com.example.jetpack.ui.fragment.home.component.HomeShortcutItem
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.view.DigitalClock2
import com.example.jetpack.util.AppUtil
import com.example.jetpack.util.NavigationUtil.safeNavigate
import com.example.jetpack.util.PermissionUtil
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : CoreFragment() {
    private var showDialog by mutableStateOf(false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNotification()
    }

    /********************ALL FUNCTIONS BELOWS ARE USED FOR SETTING DAILY NOTIFICATIONS */
    private fun setupNotification() {
        //1. Request POST NOTIFICATION permission if device has Android OS from 13
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val isAccessed: Boolean = PermissionUtil.isNotiEnabled(context = requireContext())
            if (!isAccessed) {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        //2. Create notification channel and setup daily notification
        NotificationManager.createNotificationChannel(context = requireContext())
        NotificationManager.sendNotification(context = requireContext())

        //3. Create lockscreen-styled notification and send it every day
        LockscreenManager.createNotificationChannel(context = requireContext())
        LockscreenManager.sendNotification(context = requireContext())
    }

    /**
     * request POST NOTIFICATION by using Android Runtime System
     */
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private val permissionLauncher: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isAccessed ->
        if (isAccessed) {
            Toast.makeText(requireContext(), "Enable notification !", Toast.LENGTH_SHORT).show()
            LockscreenManager.sendNotification(context = requireContext())
            NotificationManager.sendNotification(context = requireContext())
        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                openRationaleDialog()
            } else {
                openSettingDialog()
            }
        }
    }


    /**
     * request POST NOTIFICATION by using Android Runtime System part 2
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun openRationaleDialog() {
        //1. define listener for button positive
        val listener =
            DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }


        //2. show dialog
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.fake_title))
        builder.setMessage(getString(R.string.fake_message))
        builder.setPositiveButton(R.string.ok, listener)
        builder.show()
    }

    /**
     * guide users open app setting to enable POST NOTIFICATION
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun openSettingDialog() {
        //1. define listener for button positive
        val positiveListener =
            DialogInterface.OnClickListener { dialog: DialogInterface, which: Int ->
                //1.1 dismiss intro dialog
                dialog.dismiss()


                //1.2 open app setting
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", requireContext().packageName, null)
                intent.setData(uri)
                settingPermissionLauncher.launch(intent)
            }


        //2. show dialog
        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.fake_title))
        builder.setMessage(getString(R.string.fake_message))
        builder.setPositiveButton(R.string.ok, positiveListener)
        builder.setNegativeButton(R.string.cancel, null)
        builder.show()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val settingPermissionLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val enabled = PermissionUtil.isNotiEnabled(context = requireContext())
            if (enabled) {
                NotificationManager.sendNotification(context = requireContext())
                LockscreenManager.sendNotification(context = requireContext())
            } else {
                AppUtil.logcat("user denied access POST NOTIFICATION !")
            }
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