package com.example.jetpack.ui.fragment.bluetooth

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack.ui.fragment.bluetooth.mechanicsm.BluetoothClassic
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BluetoothViewModel
@Inject
constructor(
    private val bluetoothClassic: BluetoothClassic
) : ViewModel() {
    private val _pairedDevices = MutableStateFlow<Array<BluetoothDevice>>(arrayOf())
    val pairedDevices = _pairedDevices.asStateFlow()

    private val _isDeviceScanning = MutableStateFlow<Boolean>(false)
    val isDeviceScanning = _isDeviceScanning.asStateFlow()

    init {
        getPairedDevices()
    }

    fun isBluetoothSupported(): Boolean {
        return bluetoothClassic.isBluetoothSupported()
    }

    fun isBluetoothEnabled(): Boolean {
        return bluetoothClassic.isBluetoothEnabled()
    }

    /**
     * get devices that paired with this device in the past in local area
     */
    private fun getPairedDevices() {
        val isBluetoothEnabled = bluetoothClassic.isBluetoothEnabled()
        viewModelScope.launch(Dispatchers.IO) {
            if (isBluetoothEnabled) {
                _pairedDevices.value = bluetoothClassic.getPairedDevices()?.toTypedArray() ?: arrayOf()
            }
        }
    }

    fun startDiscovery(){
        bluetoothClassic.resetDiscoveredDevices()
        bluetoothClassic.startDiscovery()
        getDiscoveredDevices()
    }

    fun stopDiscovery(){ bluetoothClassic.stopDiscovery() }

    /**
     * scanning bluetooth-enabled devices in 12 seconds
     */
    fun getDiscoveredDevices(){

        viewModelScope.launch(Dispatchers.IO){
            _isDeviceScanning.value = true
            for(index in 0..12){
                if(index == 12){
                    _isDeviceScanning.value = false
                }

                _pairedDevices.value = bluetoothClassic.getDiscoveredDevices().toTypedArray()
                delay(1000)
            }
        }
    }

    fun connectToDevice(device: BluetoothDevice){
        bluetoothClassic.connectToDevice(device = device)
    }
}