package com.example.jetpack.ui.fragment.bluetooth

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack.ui.fragment.bluetooth.mechanicsm.BluetoothLowEnergy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Phong-Kaster
 * BLE stands for Bluetooth Low Energy.
 */
@HiltViewModel
class BluetoothViewModel2
@Inject
constructor(
    private val bluetoothLowEnergy: BluetoothLowEnergy
): ViewModel()
{

    /**
     * is device scanning?
     * */
    private val _scanningFlow = MutableStateFlow<Boolean>(false)
    val scanningFlow: StateFlow<Boolean>
        get() = _scanningFlow.asStateFlow()

    /**
     * how many devices are discovered?
     */
    private val _discoveredDevicesFlow = MutableStateFlow<Array<BluetoothDevice>>(arrayOf())
    val discoveredDevicesFlow: StateFlow<Array<BluetoothDevice>>
        get() = _discoveredDevicesFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.Default){
            bluetoothLowEnergy.discoveredDevicesFlow.collectLatest {
                _discoveredDevicesFlow.value = it
            }
        }

        viewModelScope.launch(Dispatchers.Default){
            bluetoothLowEnergy.scanningFlow.collectLatest {
                _scanningFlow.value = it
            }
        }
    }

    fun isBluetoothSupported(): Boolean { return bluetoothLowEnergy.isBluetoothSupported() }

    fun isBluetoothEnabled(): Boolean { return bluetoothLowEnergy.isBluetoothEnabled() }

    /**
     * BLE stands for Bluetooth Low Energy.
     */
    fun scanBLEDevices(){ bluetoothLowEnergy.scanBLEDevices() }

}