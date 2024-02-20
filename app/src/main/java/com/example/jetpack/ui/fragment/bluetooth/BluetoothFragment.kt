package com.example.jetpack.ui.fragment.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.jetpack.R
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.ui.fragment.permission.component.PermissionPopup
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.NavigationUtil.safeNavigateUp
import com.example.jetpack.util.PermissionUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BluetoothFragment : CoreFragment() {

    private var bluetoothAdapter: BluetoothAdapter? = null
    private val tag = "bluetooth"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bluetoothAdapter = createBluetoothAdapter()
        setupBluetoothLifecycleObserver()
    }


    /*******************************************
     * for requesting permission
     */
    private var showPopup: Boolean by mutableStateOf(false)
    private lateinit var bluetoothLifecycleObserver: BluetoothLifecycleObserver
    private val callback = object : BluetoothLifecycleObserver.Callback {
        override fun openRationaleDialog() {
            showPopup = true
        }
    }

    private fun setupBluetoothLifecycleObserver() {
        bluetoothLifecycleObserver = BluetoothLifecycleObserver(
            registry = requireActivity().activityResultRegistry,
            activity = requireActivity(),
            callback = callback
        )
        lifecycle.addObserver(bluetoothLifecycleObserver)
    }


    /*******************************************
     * for bluetooth adapter
     */
    private fun createBluetoothAdapter(): BluetoothAdapter? {
        val bluetoothManager: BluetoothManager =
            requireContext().getSystemService(BluetoothManager::class.java)
        val adapter: BluetoothAdapter? = bluetoothManager.adapter
        return adapter
    }

    private val doSomeThing =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult(), {})

    private fun turnonBluetooth() {
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
            return
        }


        val allPermission = BluetoothLifecycleObserver.getMandatoryPermissions()
        val enableAllPermission = PermissionUtil.hasPermissions(requireContext(), *allPermission)
        if (!enableAllPermission) {
            bluetoothLifecycleObserver.launcher.launch(allPermission)
        } else {
            if (bluetoothAdapter?.isEnabled == false) {
                val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                doSomeThing.launch(enableBluetoothIntent)
            }
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

        LaunchedEffect(Unit) { turnonBluetooth() }

        BluetoothLayout(
            onBack = { safeNavigateUp() },
        )


        PermissionPopup(
            enable = showPopup,
            title = R.string.attention,
            content = R.string.multiple_permissions_content,
            onDismiss = { showPopup = false },
            goSetting = { openSettingApp() },
        )
    }
}

@Composable
fun BluetoothLayout(
    onBack: () -> Unit = {}
) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.lottie_animation_bluetooth_scanning)
    )

    CoreLayout(
        backgroundColor = Color(0xFFF5FCFF),
        modifier = Modifier.navigationBarsPadding(),
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(color = Color.White)
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .clip(shape = CircleShape)
                            .background(color = Color(0xFF909090))
                            .size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Connect new device",
                    style = customizedTextStyle(fontSize = 28, fontWeight = 600),
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                LottieAnimation(
                    composition = composition,
                    restartOnPlay = true,
                    iterations = LottieConstants.IterateForever,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth(0.9F)
                        .aspectRatio(1F)
                )

                Text(
                    text = "Searching...",
                    style = customizedTextStyle(fontSize = 17, fontWeight = 400),
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}

@Preview
@Composable
private fun PreviewBluetoothLayout() {
    BluetoothLayout()
}