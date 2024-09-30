package com.example.jetpack.ui.fragment.permission

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.notification.LockscreenManager
import com.example.jetpack.notification.NotificationManager
import com.example.jetpack.ui.component.CoreTopBar2
import com.example.jetpack.ui.component.SolidButton
import com.example.jetpack.ui.fragment.permission.component.PermissionPopup
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.OppositePrimaryColor
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.NavigationUtil.safeNavigateUp
import com.example.jetpack.util.PermissionUtil
import dagger.hilt.android.AndroidEntryPoint

/**
 * This fragment handles request one permission and multiple permissions.
 * All logic are placed in this fragment.
 */
@AndroidEntryPoint
class PermissionFragment : CoreFragment() {

    override val TAG = "PermissionFragment"


    /***************************************
     * for request one permission
     * */
    private var showPopupOnePermission: Boolean by mutableStateOf(false)
    private val notificationLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                showToast("Notification permission is enabled")
            } else {
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                    Log.d(TAG, "shouldShowRequestPermissionRationale")
                } else {
                    showPopupOnePermission = true
                }
            }
        }

    /*************************************************
     * setupNotification
     */
    private fun setupNotification() {
        //1. Request POST NOTIFICATION permission if device has Android OS from 13
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val isAccessed: Boolean = PermissionUtil.isNotiEnabled(context = requireContext())
            if (!isAccessed) {
                notificationLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        //2. Create notification channel and setup daily notification
        NotificationManager.createNotificationChannel(context = requireContext())
        NotificationManager.sendNotification(context = requireContext())

        //3. Create lockscreen-styled notification and send it every day
        LockscreenManager.createNotificationChannel(context = requireContext())
        LockscreenManager.sendNotification(context = requireContext())
    }


    /***************************************
     * for request multiple permissions
     * */
    private var showPopupMultiplePermissions: Boolean by mutableStateOf(false)
    private val requiredPermissions = arrayOf(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.RECORD_AUDIO
    )

    /*************************************************
     * multiplePermissionLauncher
     */
    private val multiplePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            val enableRecordAudio = it[android.Manifest.permission.RECORD_AUDIO]
            val enableAccessCoarseLocation = it[android.Manifest.permission.ACCESS_COARSE_LOCATION]
            val enableAccessFineLocation = it[android.Manifest.permission.ACCESS_FINE_LOCATION]


            if (enableRecordAudio == true && enableAccessCoarseLocation == true && enableAccessFineLocation == true) {
                Toast.makeText(
                    requireContext(),
                    "All permissions are enabled !",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.RECORD_AUDIO)) {
                    // TODO: do nothing because Android system will request automatically
                } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    // TODO: do nothing because Android system will request automatically
                } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                    // TODO: do nothing because Android system will request automatically
                } else {
                    showPopupMultiplePermissions = true
                }
            }
        }


    /*************************************************
     * openSettingPermission
     */
    private fun openSettingPermission() {
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", requireActivity().packageName, null)
            intent.setData(uri)
            settingLauncher.launch(intent)
        } catch (_: Exception) {

        }
    }

    /*************************************************
     * settingLauncher
     */
    private val settingLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

        }


    @Composable
    override fun ComposeView() {
        super.ComposeView()
        PermissionLayout(
            onBack = { safeNavigateUp() },
            onOpenAppSetting = { openSettingPermission() },
            onRequestOnePermission = { setupNotification() },
            onRequestMultiplePermission = {
                val enableAllPermissions = PermissionUtil.hasPermissions(
                    context = requireContext(),
                    permissions = requiredPermissions
                )
                if (enableAllPermissions) {
                    showToast("All permissions are enabled !")
                } else {
                    multiplePermissionLauncher.launch(requiredPermissions)
                }
            }
        )

        PermissionPopup(
            enable = showPopupMultiplePermissions,
            title = R.string.attention,
            content = R.string.multiple_permissions_content,
            onDismiss = { showPopupMultiplePermissions = false },
            goSetting = { openSettingPermission() },
        )

        PermissionPopup(
            enable = showPopupOnePermission,
            title = R.string.attention,
            content = R.string.one_permission_content,
            onDismiss = { showPopupOnePermission = false },
            goSetting = { openSettingPermission() },
        )
    }
}

@Composable
fun PermissionLayout(
    onBack: () -> Unit = {},
    onOpenAppSetting: () -> Unit = {},
    onRequestOnePermission: () -> Unit = {},
    onRequestMultiplePermission: () -> Unit = {},
) {
    CoreLayout(
        topBar = {
            CoreTopBar2(
                onLeftClick = onBack,
                title = stringResource(id = R.string.permissions),
                onRightClick = onOpenAppSetting,
                iconRight = R.drawable.ic_setting
            )
        },
        bottomBar = {
            Column {
                SolidButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onRequestOnePermission,
                    text = stringResource(R.string.request_one_permission),
                    textColor = OppositePrimaryColor,
                    textStyle = customizedTextStyle(fontWeight = 700),
                    backgroundColor = PrimaryColor,
                    marginVertical = 0.dp
                )
                SolidButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onRequestMultiplePermission,
                    text = stringResource(R.string.request_multiple_permissions),
                    textColor = OppositePrimaryColor,
                    textStyle = customizedTextStyle(fontWeight = 700),
                    backgroundColor = PrimaryColor
                )
            }

        },
        backgroundColor = Background,
        modifier = Modifier,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Background)
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.this_class_makes_the_request),
                    style = customizedTextStyle(fontSize = 16, fontWeight = 400),
                    color = PrimaryColor,
                    modifier = Modifier
                )
            }
        }
    )
}

@Preview
@Composable
fun PreviewPermissionLayout() {
    PermissionLayout()
}