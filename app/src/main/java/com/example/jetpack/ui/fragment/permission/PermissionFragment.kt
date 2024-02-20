package com.example.jetpack.ui.fragment.permission

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.OppositePrimaryColor
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.NavigationUtil.safeNavigateUp
import com.example.jetpack.util.PermissionUtil
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PermissionFragment : CoreFragment() {

    private val TAG = "PermissionFragment"
    private var showPopup: Boolean by mutableStateOf(false)

    private val requiredPermissions = arrayOf(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.RECORD_AUDIO
    )

    private val multipleLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
        val enableRecordAudio = it[android.Manifest.permission.RECORD_AUDIO]
        val enableAccessCoarseLocation = it[android.Manifest.permission.ACCESS_COARSE_LOCATION]
        val enableAccessFineLocation = it[android.Manifest.permission.ACCESS_FINE_LOCATION]


        if(enableRecordAudio == true && enableAccessCoarseLocation == true && enableAccessFineLocation == true){
            Toast.makeText(requireContext(), "All permissions are enabled !", Toast.LENGTH_SHORT).show()
        }else{
            if (shouldShowRequestPermissionRationale(android.Manifest.permission.RECORD_AUDIO)) {
                Log.d(TAG, "shouldShowRequestPermissionRationale RECORD_AUDIO ")
            } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
                Log.d(TAG, "shouldShowRequestPermissionRationale ACCESS_COARSE_LOCATION ")
            } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                Log.d(TAG, "shouldShowRequestPermissionRationale ACCESS_FINE_LOCATION ")
            } else {
                showPopup = true
            }
        }
    }




    /**
     * ------------------- OPEN SETTING APPLICATION
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
        PermissionLayout(
            onBack = { safeNavigateUp() },
            onRequestOnePermission = {},
            onRequestMultiplePermission = {
                val enableAllPermissions = PermissionUtil.hasPermissions(context = requireContext(), permissions = requiredPermissions)
                if (enableAllPermissions) {
                    showToast("All permissions are enabled !")
                } else {
                    multipleLauncher.launch(requiredPermissions)
                }
            }
        )

        PermissionPopup(
            enable = showPopup,
            onDismiss = { showPopup = false },
            goSetting = { openSettingPermission() },
        )
    }
}

@Composable
fun PermissionLayout(
    onBack: () -> Unit = {},
    onRequestOnePermission: ()->Unit = {},
    onRequestMultiplePermission: ()->Unit = {},
) {
    CoreLayout(
        topBar = {
            CoreTopBar2(
                onClick = onBack,
                title = stringResource(id = R.string.permissions)
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

            }
        }
    )
}

@Preview
@Composable
fun PreviewPermissionLayout() {
    PermissionLayout()
}