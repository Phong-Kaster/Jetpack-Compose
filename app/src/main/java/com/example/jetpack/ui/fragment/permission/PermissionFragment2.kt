package com.example.jetpack.ui.fragment.permission

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.ui.component.CoreTopBar2
import com.example.jetpack.ui.component.SolidButton
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.OppositePrimaryColor
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.NavigationUtil.safeNavigateUp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PermissionFragment2 : CoreFragment() {

    /**
     * OPEN SETTING APPLICATION
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

    private val settingLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

        }

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        PermissionLayout2(
            onBack = { safeNavigateUp() },
            onOpenAppSetting = { openSettingPermission() },
            onRequestMultiplePermission = {},
            onRequestOnePermission = {}
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
                    text = stringResource(R.string.this_screen_s_functionality_mirrors_the_permission_screen_but_the_underlying_logic_is_implemented_in_a_separate_class_and_utilizes_activity_results),
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