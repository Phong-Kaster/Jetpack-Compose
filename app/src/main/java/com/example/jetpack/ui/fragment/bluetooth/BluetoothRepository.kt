package com.example.jetpack.ui.fragment.bluetooth

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.jetpack.JetpackApplication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


class BluetoothRepository
@Inject
constructor(val context: JetpackApplication) {

    private val tag = "BluetoothRepository"
    private var bluetoothManager: BluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val discoveredDevices = mutableListOf<BluetoothDevice>()

    /*private val _discoveredDevicesFlow = MutableStateFlow<List<BluetoothDevice>>(listOf())
    val discoveredDevicesFlow = _discoveredDevicesFlow.asStateFlow()*/
    /**
     * bluetoothAdapter represents the local Bluetooth adapter (Bluetooth radio).
     * The BluetoothAdapter is the entry-point for all Bluetooth interaction.
     * Using this, you can discover other Bluetooth devices, query a list of bonded (paired) devices,
     * instantiate a BluetoothDevice using a known MAC address, and create a BluetoothServerSocket to listen for communications from other devices.
     * */
    private var bluetoothAdapter: BluetoothAdapter? = null

    init {
        bluetoothAdapter = bluetoothManager.adapter
    }

    fun isBluetoothSupported(): Boolean {
        return bluetoothAdapter != null
    }

    fun isBluetoothEnabled(): Boolean {
        return bluetoothAdapter?.isEnabled == true
    }

    fun isBluetoothPermissionEnabled(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    fun getPairedDevices(): Set<BluetoothDevice>? {
        return if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_DENIED
        ) {
            return setOf()
        } else {
            bluetoothAdapter?.bondedDevices
        }
    }

    private val receiver = object : BroadcastReceiver() {
        @SuppressLint("MissingPermission")
        override fun onReceive(context: Context, intent: Intent) {
            if (BluetoothDevice.ACTION_FOUND == intent.action) {
                val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)

                if (device != null && device.name != null && !discoveredDevices.contains(device)) {
                    discoveredDevices.add(device)
                }
            }
        }
    }


    fun startDiscovery() {
        Log.d(tag, "BluetoothRepository - startDiscovery")
        val isBluetoothSupported = isBluetoothSupported()
        val isBluetoothEnabled = isBluetoothEnabled()

        if (!isBluetoothEnabled || !isBluetoothSupported) {
            return
        }

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_DENIED) {
            return
        }


        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        context.registerReceiver(receiver, filter)
        bluetoothAdapter?.startDiscovery()
    }


    fun stopDiscovery() {
        Log.d(tag, "BluetoothRepository - stopDiscovery")

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_DENIED) {
            return
        }

        bluetoothAdapter?.cancelDiscovery()
        context.unregisterReceiver(receiver)
    }

    fun getDiscoveredDevices(): List<BluetoothDevice> {
        return discoveredDevices
    }
}