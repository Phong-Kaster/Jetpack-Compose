package com.example.jetpack.ui.fragment.permission

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
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
import com.example.jetpack.ui.component.CoreTopBar2
import com.example.jetpack.ui.component.SolidButton
import com.example.jetpack.ui.fragment.permission.component.PermissionPopup
import com.example.jetpack.lifecycleobserver.OnePermissionLifecycleObserver
import com.example.jetpack.lifecycleobserver.MultiplePermissionsLifecycleObserver
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.OppositePrimaryColor
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.NavigationUtil.safeNavigateUp
import com.example.jetpack.util.PermissionUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PermissionFragment2 : CoreFragment() {

    private lateinit var onePermissionObserver: OnePermissionLifecycleObserver
    private lateinit var multiplePermissionsObserver: MultiplePermissionsLifecycleObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObserverOnePermission()
        setupObserverMultiplePermissions()
    }

    /*************************************************
     * for request one permission
     */
    private var showPopupOnePermission: Boolean by mutableStateOf(false)
    private val callbackOnePermission = object : OnePermissionLifecycleObserver.Callback{
        override fun openRationaleDialog() {
            showPopupOnePermission = true
        }
    }

    private fun setupObserverOnePermission() {
        onePermissionObserver = OnePermissionLifecycleObserver(
            registry = requireActivity().activityResultRegistry,
            activity = requireActivity(),
            callback = callbackOnePermission)
        lifecycle.addObserver(onePermissionObserver)
    }

    private fun requestOnePermission() {
        // Request POST NOTIFICATION permission if device has Android OS from 13
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return

        val isAccessed: Boolean = PermissionUtil.isNotiEnabled(context = requireContext())
        if (isAccessed) return
        onePermissionObserver.launcher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
    }

    /*************************************************
     * for request multiple permissions
     */
    private var showPopupMultiplePermissions: Boolean by mutableStateOf(false)
    private val callbackMultiplePermissions = object : MultiplePermissionsLifecycleObserver.Callback{
        override fun openRationaleDialog() {
            showPopupMultiplePermissions = true
        }
    }

    private fun setupObserverMultiplePermissions() {
        multiplePermissionsObserver = MultiplePermissionsLifecycleObserver(
            registry = requireActivity().activityResultRegistry,
            activity = requireActivity(),
            callback = callbackMultiplePermissions)
        lifecycle.addObserver(multiplePermissionsObserver)
    }

    private fun requestMultiplePermission(){
        val enableAllPermissions = PermissionUtil.hasPermissions(
            context = requireContext(),
            permissions = MultiplePermissionsLifecycleObserver.mandatoryPermissions
        )
        if (enableAllPermissions) {
            showToast("All permissions are enabled !")
        } else {
            multiplePermissionsObserver.launcher.launch(MultiplePermissionsLifecycleObserver.mandatoryPermissions)
        }
    }


    /*************************************************
     * open app setting
     */
    private val settingLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {}
    private fun openSettingApp() {
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", requireActivity().packageName, null)
            intent.setData(uri)
            settingLauncher.launch(intent)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }



    @Composable
    override fun ComposeView() {
        super.ComposeView()
        PermissionLayout2(
            onBack = { safeNavigateUp() },
            onOpenAppSetting = { openSettingApp() },
            onRequestOnePermission = { requestOnePermission() },
            onRequestMultiplePermission = { requestMultiplePermission() },
        )

        PermissionPopup(
            enable = showPopupOnePermission,
            title = R.string.attention,
            content = R.string.one_permission_content,
            onDismiss = { showPopupOnePermission = false },
            goSetting = { openSettingApp() },
        )

        PermissionPopup(
            enable = showPopupMultiplePermissions,
            title = R.string.attention,
            content = R.string.multiple_permissions_content,
            onDismiss = { showPopupMultiplePermissions = false },
            goSetting = { openSettingApp() },
        )
    }


}

@Composable
fun PermissionLayout2(
    onBack: () -> Unit = {},
    onOpenAppSetting: () -> Unit = {},
    onRequestOnePermission: () -> Unit = {},
    onRequestMultiplePermission: () -> Unit = {},
) {
    CoreLayout(
        topBar = {
            CoreTopBar2(
                onLeftClick = onBack,
                title = "${stringResource(id = R.string.permissions)} 2",
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
                    text = stringResource(R.string.this_screen_s_functionality_mirrors),
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
private fun PreviewPermissionLayout2() {
    PermissionLayout2()
}