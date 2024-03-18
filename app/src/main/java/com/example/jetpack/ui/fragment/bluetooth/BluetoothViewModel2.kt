package com.example.jetpack.ui.fragment.bluetooth
 import androidx.lifecycle.ViewModel
import com.example.jetpack.ui.fragment.bluetooth.mechanicsm.BluetoothLowEnergy
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Phong-Kaster
 * BLE stands for Bluetooth Low Energy.
 */
@HiltViewModel
class BluetoothViewModel2
@Inject
constructor(
    val bluetoothLowEnergy: BluetoothLowEnergy
): ViewModel()
{
    fun isBluetoothSupported(): Boolean { return bluetoothLowEnergy.isBluetoothSupported }

    fun isBluetoothEnabled(): Boolean { return bluetoothLowEnergy.isBluetoothEnabled }

    /**
     * BLE stands for Bluetooth Low Energy.
     */
    fun scanBLEDevices(){ bluetoothLowEnergy.scanBLEDevices() }
}