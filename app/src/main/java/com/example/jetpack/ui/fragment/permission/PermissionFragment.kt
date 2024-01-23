package com.example.jetpack.ui.fragment.permission

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
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
class PermissionFragment : CoreFragment() {

    private val TAG = "PermissionFragment"

    private val requiredPermissions = arrayOf(
        android.Manifest.permission.BLUETOOTH,
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.RECORD_AUDIO
    )

    private val multipleLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
        Log.d(TAG, "-----------")
        Log.d(TAG, "map: $it")
    }

    private fun hasPermissions(context: Context, vararg permissions: String): Boolean = permissions.all {
        ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        PermissionLayout(
            onBack = { safeNavigateUp() },
            onRequestOnePermission = {},
            onRequestMultiplePermission = {
                val enableAllPermissions = hasPermissions(requireContext(), *requiredPermissions)
                Log.d(TAG, "--------------")
                Log.d(TAG, "enableAllPermissions: $enableAllPermissions")
                if (enableAllPermissions) {
                    showToast("mission successes !")
                } else {
                    multipleLauncher.launch(requiredPermissions)
                }
            }
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