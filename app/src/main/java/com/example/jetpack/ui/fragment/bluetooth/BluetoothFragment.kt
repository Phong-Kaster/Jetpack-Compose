package com.example.jetpack.ui.fragment.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.example.jetpack.R
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.ui.fragment.bluetooth.component.BluetoothLifecycleObserver
import com.example.jetpack.ui.fragment.bluetooth.component.PairedDevices
import com.example.jetpack.ui.fragment.bluetooth.mechanicsm.BluetoothLowEnergy
import com.example.jetpack.ui.fragment.permission.component.PermissionPopup
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.ui.view.AnimationCircularWave
import com.example.jetpack.util.NavigationUtil.safeNavigateUp
import com.example.jetpack.util.PermissionUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @author Phong-Kaster
 * @see [Bluetooth overview](https://developer.android.com/develop/connectivity/bluetooth)
 */
@AndroidEntryPoint
class BluetoothFragment : CoreFragment() {

    private val viewModel: BluetoothViewModel2  by viewModels()
    private val tag = "bluetooth"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBluetoothLifecycleObserver()
    }

    override fun onDestroy() {
        super.onDestroy()
        /*viewModel.stopDiscovery()*/
    }


    /*******************************************
     * for requesting bluetooth permission
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
     * for turning on device's bluetooth
     */
    private val doSomeThing = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val isBluetoothEnabled = viewModel.isBluetoothEnabled()
        if (isBluetoothEnabled) {
            viewModel.scanBLEDevices()
        } else {
            /*viewModel.stopDiscovery()*/
        }
    }

    @SuppressLint("MissingPermission")
    private fun turnonBluetooth() {
        val isBluetoothSupported = viewModel.isBluetoothSupported()
        if (isBluetoothSupported == false) return



        /**
         * check BLUETOOTH permission*/
        val allPermission = BluetoothLifecycleObserver.getMandatoryPermissions()
        val enableAllPermission = PermissionUtil.hasPermissions(requireContext(), *allPermission)
        if (!enableAllPermission) {
            bluetoothLifecycleObserver.launcher.launch(allPermission)
        } else {
            val isBluetoothEnabled = viewModel.isBluetoothEnabled()
            if (isBluetoothEnabled == false) { // open system dialog that users can turn on bluetooth
                val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                doSomeThing.launch(enableBluetoothIntent)
            } else {
                viewModel.scanBLEDevices()
            }
        }
    }



    /*************************************************
     * open app setting
     */
    private val settingLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

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

        LaunchedEffect(key1 = Unit) {
            viewModel.scanBLEDevices()
        }

        BluetoothLayout(
            isDeviceScanning = viewModel.scanningFlow.collectAsState().value,
            pairedDevices = viewModel.discoveredDevicesFlow.collectAsState().value,
            onBack = { safeNavigateUp() },
            onTurnOnBluetooth = { /*turnonBluetooth()*/ },
            onConnectDevice = { /*viewModel.connectToDevice(it)*/ }
        )


        PermissionPopup(
            enable = showPopup,
            title = R.string.attention,
            content = "Our app needs Bluetooth to connect to your medical device (like a blood sugar calculator) for real-time health monitoring. This ensures a smooth user experience and is crucial for our app's functionality. Your privacy and health are our top priorities.",
            onDismiss = { showPopup = false },
            goSetting = { openSettingApp() },
            )
    }
}

@Composable
fun BluetoothLayout(
    isDeviceScanning: Boolean,
    pairedDevices: Array<BluetoothDevice>,
    onBack: () -> Unit = {},
    onTurnOnBluetooth: () -> Unit = {},
    onConnectDevice : (BluetoothDevice) -> Unit = {}
) {
    CoreLayout(
        backgroundColor = Color(0xFFF5FCFF),
        modifier = Modifier.navigationBarsPadding(),
        bottomBar = {
            Column(
        
                modifier = Modifier.fillMaxWidth()){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .clip(shape = RoundedCornerShape(15.dp))
                        .clickable { onTurnOnBluetooth() }
                        .background(color = Color(0xFF67C6EB), shape = RoundedCornerShape(15.dp))
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = "Scan",
                        color = Color.White
                    )
                }
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                /**
                 * BUTTON CLOSE & TITLE
                 * */
                Column(
                    modifier = Modifier
                        .padding(top = 40.dp, bottom = 40.dp)
                ) {
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
                }

                /**
                 * ANIMATION BLUETOOTH SCANNING
                 * */
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                ) {
                    AnimationCircularWave(
                        modifier = Modifier
                            .fillMaxWidth(0.5F),
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_bluetooth),
                                contentDescription = null,
                                tint = Color(0xFF1044FF),
                                modifier = Modifier
                                    .fillMaxSize(0.35F)
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(50.dp))


                    Text(
                        text =
                        if (isDeviceScanning) {
                            "Searching..."
                        } else {
                            "Found ${pairedDevices.size} devices"
                        },
                        style = customizedTextStyle(fontSize = 22, fontWeight = 500),
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()

                    )
                }

                /**
                 * PAIRED BLUETOOTH DEVICES
                 * */
                PairedDevices(
                    isDeviceScanning = isDeviceScanning,
                    bluetoothDevices = pairedDevices,
                    onConnectDevice = onConnectDevice

                )
            }
        }
    )
}

@Preview
@Composable
private fun PreviewBluetoothLayout() {
    BluetoothLayout(
        pairedDevices = arrayOf(),
        isDeviceScanning = true
    )
}