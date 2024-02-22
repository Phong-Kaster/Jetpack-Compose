package com.example.jetpack.ui.fragment.bluetooth

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val bluetoothRepository: BluetoothRepository
) : ViewModel() {
    private val _pairedDevices = MutableStateFlow<Array<BluetoothDevice>>(arrayOf())
    val pairedDevices = _pairedDevices.asStateFlow()

    private val _isDeviceScanning = MutableStateFlow<Boolean>(false)
    val isDeviceScanning = _isDeviceScanning.asStateFlow()

    init {
        getPairedDevices()
    }

    fun isBluetoothSupported(): Boolean {
        return bluetoothRepository.isBluetoothSupported()
    }

    fun isBluetoothEnabled(): Boolean {
        return bluetoothRepository.isBluetoothEnabled()
    }

    /**
     * get devices that paired with this device in the past in local area
     */
    private fun getPairedDevices() {
        val isBluetoothEnabled = bluetoothRepository.isBluetoothEnabled()
        viewModelScope.launch(Dispatchers.IO) {
            if (isBluetoothEnabled) {
                _pairedDevices.value = bluetoothRepository.getPairedDevices()?.toTypedArray() ?: arrayOf()
            }
        }
    }

    fun startDiscovery(){
        bluetoothRepository.resetDiscoveredDevices()
        bluetoothRepository.startDiscovery()
        getDiscoveredDevices()
    }

    fun stopDiscovery(){ bluetoothRepository.stopDiscovery() }

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

                _pairedDevices.value = bluetoothRepository.getDiscoveredDevices().toTypedArray()
                delay(1000)
            }
        }
    }

    fun connectToDevice(device: BluetoothDevice){
        bluetoothRepository.connectToDevice(device = device)
    }
}