package com.example.jetpack.ui.fragment.bluetooth.mechanicsm

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
import java.util.UUID
import javax.inject.Inject


class BluetoothClassic
@Inject
constructor(val context: JetpackApplication) {

    val TAG =  "BluetoothRepository"
    private var bluetoothManager: BluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val discoveredDevices = mutableListOf<BluetoothDevice>()

    companion object {
        val bluetoothName = "jetpackcompose"

         val bluetoothUUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
         val UUID_SECURE = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66")
    }

    /**
     * @param bluetoothAdapter represents the local Bluetooth adapter (Bluetooth radio).
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
            ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED
        } else {
            // For versions below Android S, BLUETOOTH_SCAN permission is not required, so we can return true.
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

    /**
     * To receive information about each device discovered.
     * The system broadcasts this intent for each device, we can get MAC address which is used to connect
     * from this device to the target device
     */
    private val receiver = object : BroadcastReceiver() {
        @SuppressLint("MissingPermission")
        override fun onReceive(context: Context, intent: Intent) {
            if (BluetoothDevice.ACTION_FOUND == intent.action) {

                val device: BluetoothDevice? =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        intent.getParcelableExtra(
                            BluetoothDevice.EXTRA_DEVICE,
                            BluetoothDevice::class.java
                        )
                    } else {
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    }


                if (device != null && device.name != null && !discoveredDevices.contains(device)) {
                    discoveredDevices.add(device)
                }
            }
        }
    }


    /**
     * Discover devices - The discovery process usually involves an inquiry scan of about 12 seconds,
     * followed by a page scan of each device found to retrieve its Bluetooth name.
     */
    fun startDiscovery() {
        Log.d(TAG, "BluetoothRepository - startDiscovery")
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
        Log.d(TAG, "BluetoothRepository - stopDiscovery")

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_DENIED) {
            return
        }

        bluetoothAdapter?.cancelDiscovery()
        context.unregisterReceiver(receiver)
    }

    fun resetDiscoveredDevices(){ discoveredDevices.clear() }

    fun getDiscoveredDevices(): List<BluetoothDevice> { return discoveredDevices }


    @SuppressLint("MissingPermission")
    fun connectToDevice(device: BluetoothDevice) {

    }
}