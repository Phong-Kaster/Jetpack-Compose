package com.example.jetpack.ui.fragment.bluetooth

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.jetpack.JetpackApplication
import java.io.IOException
import java.util.UUID
import javax.inject.Inject


class BluetoothRepository
@Inject
constructor(val context: JetpackApplication) {

    private val tag = "BluetoothRepository"
    private var bluetoothManager: BluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val discoveredDevices = mutableListOf<BluetoothDevice>()

    companion object {
        val bluetoothName = "jetpackcompose"

         val bluetoothUUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
         val UUID_SECURE = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66")
    }

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

    fun resetDiscoveredDevices(){ discoveredDevices.clear() }

    fun getDiscoveredDevices(): List<BluetoothDevice> { return discoveredDevices }


    @SuppressLint("MissingPermission")
    fun connectToDevice(device: BluetoothDevice) {
        Log.d(tag, "connectToDevice with device ${device.name} with ${device.address}")
        val connectThread = ConnectThread(device)
        connectThread.start()
    }

    /**
     * Thread class is used when this device acts as client-side
     */
    @SuppressLint("MissingPermission")
    private inner class ConnectThread(val device: BluetoothDevice) : Thread() {

        private val mmSocket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) { device.createRfcommSocketToServiceRecord(bluetoothUUID) }

        override fun run() {
            // Cancel discovery because it otherwise slows down the connection.
            bluetoothAdapter?.cancelDiscovery()

            try {
                Log.d(tag, "manageMyConnectedSocket to transfer bluetooth data ")
                mmSocket?.connect()
            } catch (ex: Exception) {
                ex.printStackTrace()
                cancel()
                /*try {
                    val fallbackSocket = createBluetoothSocketFallback(device)
                    fallbackSocket?.connect()
                    Log.d(tag, "Fallback connection success")
                } catch (fallbackException: IOException) {
                    Log.e(tag, "Fallback also failed, no options left", fallbackException)
                    // Handle connection failure
                    cancel()
                }*/
            }
        }

        // Closes the client socket and causes the thread to finish.
        fun cancel() {
            try {
                mmSocket?.close()
            } catch (e: IOException) {
                Log.e(tag, "Could not close the client socket", e)
            }
        }

        private fun createBluetoothSocketFallback(device: BluetoothDevice): BluetoothSocket? {
            Log.d(tag, "Trying fallback method for creating a Bluetooth socket")
            val method = device.javaClass.getMethod("createRfcommSocket", Int::class.javaPrimitiveType)
            return method.invoke(device, 1) as? BluetoothSocket
        }
    }

    /**
     * Thread class is used when this device acts as server-side
     * */
    @SuppressLint("MissingPermission")
    private inner class AcceptThread : Thread() {
        private val tag = "Bluetooth - AcceptThread"

        /**
         *
         * @param bluetoothServerSocket To connect two devices, one must act as a server
         * by holding an open BluetoothServerSocket.
         *
         * The purpose of the server socket is to listen for incoming connection
         * requests and provide a connected BluetoothSocket
         */
        private val bluetoothServerSocket: BluetoothServerSocket? by lazy(LazyThreadSafetyMode.NONE) {
            bluetoothAdapter?.listenUsingInsecureRfcommWithServiceRecord(bluetoothName, bluetoothUUID)
        }

        override fun run() {
            // Keep listening until exception occurs or a socket is returned.
            var shouldLoop = true
            while (shouldLoop) {
                val socket: BluetoothSocket? = try {
                    bluetoothServerSocket?.accept()
                } catch (e: IOException) {
                    Log.d(tag, "Socket's accept() method failed", e)
                    shouldLoop = false
                    null
                }
                socket?.also {
                    manageMyConnectedSocket(it)
                    bluetoothServerSocket?.close()
                    shouldLoop = false
                }
            }
        }

        // Closes the connect socket and causes the thread to finish.
        fun cancel() {
            try {
                bluetoothServerSocket?.close()
            } catch (e: IOException) {
                Log.e(tag, "Could not close the connect socket", e)
            }
        }

        private fun manageMyConnectedSocket(socket: BluetoothSocket) {
            // Use the connected socket to send and receive data
        }
    }
}