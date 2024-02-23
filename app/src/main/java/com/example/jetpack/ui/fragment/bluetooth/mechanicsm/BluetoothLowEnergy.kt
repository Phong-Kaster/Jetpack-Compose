package com.example.jetpack.ui.fragment.bluetooth.mechanicsm

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.jetpack.JetpackApplication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * @author Phong-Kaster
 * BLE stands for Bluetooth Low Energy.
 * BLE is designed for significantly lower power consumption.
 * This allows apps to communicate with BLE devices that have stricter power requirements,
 * such as proximity sensors, heart rate monitors, and fitness devices
 */
class BluetoothLowEnergy
@Inject
constructor(val context: JetpackApplication) {

    private val tag = "BluetoothLowEnergy"
    private var bluetoothManager: BluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val discoveredDevices = mutableListOf<BluetoothDevice>()



    private val _discoveredDevicesFlow = MutableStateFlow<Array<BluetoothDevice>>(arrayOf())
     val discoveredDevicesFlow: StateFlow<Array<BluetoothDevice>>
        get() = _discoveredDevicesFlow.asStateFlow()




    private val _scanningFlow = MutableStateFlow<Boolean>(false)
    val scanningFlow: StateFlow<Boolean>
        get() = _scanningFlow.asStateFlow()

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
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_SCAN
            ) == PackageManager.PERMISSION_GRANTED
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

    private val bluetoothLeScanner = bluetoothAdapter?.bluetoothLeScanner
    private var scanning = false
    private val handler = Handler(Looper.getMainLooper())

    // Stops scanning after 10 seconds.
    private val SCAN_PERIOD: Long = 10000

    /**
     * BLE stands for Bluetooth Low Energy
     */
    @SuppressLint("MissingPermission")
    fun scanBLEDevices() {
        if (!_scanningFlow.value) { // Stops scanning after a pre-defined scan period.
            handler.postDelayed({
                _scanningFlow.value = false

                bluetoothLeScanner?.stopScan(leScanCallback)
                Log.d(tag, "stop scanning using Bluetooth Low Energy")
            }, SCAN_PERIOD)
            _scanningFlow.value = true

            bluetoothLeScanner?.startScan(leScanCallback)
            Log.d(tag, "start scanning using Bluetooth Low Energy")
        } else {
            _scanningFlow.value = false
            bluetoothLeScanner?.stopScan(leScanCallback)
            Log.d(tag, "stop scanning using Bluetooth Low Energy")
        }
    }


    /**
     * BLE stands for Bluetooth Low Energy
     * Device scan callback.
     */
    @SuppressLint("MissingPermission")
    private val leScanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            /**
             * update discoveredDevicesFlow when find new BLE-enabled devices
             */
            val device = result.device
            if (device != null && device.name != null && !discoveredDevices.contains(device)) {
                discoveredDevices.add(device)
                _discoveredDevicesFlow.value = discoveredDevices.toTypedArray()
            }
        }
    }
}