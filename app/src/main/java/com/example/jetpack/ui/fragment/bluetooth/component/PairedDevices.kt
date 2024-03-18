package com.example.jetpack.ui.fragment.bluetooth.component

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.ui.view.LoadingDialog
import com.example.jetpack.util.ViewUtil

@SuppressLint("MissingPermission")
@Composable
fun PairdDeviceItem(
    bluetoothDevice: BluetoothDevice,
    onClick: (BluetoothDevice) -> Unit = {}
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        /*.padding(horizontal = 16.dp, vertical = 16.dp)*/
        .clip(shape = RoundedCornerShape(15.dp))
        .clickable { onClick(bluetoothDevice) }
        .background(color = Color.White, shape = RoundedCornerShape(15.dp))
        .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Text(
            text = bluetoothDevice.name ?: "",
            color = Color.Black
        )
    }
}

@SuppressLint("MissingPermission")
@Composable
fun PairedDevices(
    modifier: Modifier = Modifier,
    isDeviceScanning: Boolean,
    bluetoothDevices: Array<BluetoothDevice>,
    onConnectDevice: (BluetoothDevice) -> Unit = {}
) {
    Column(modifier = Modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)){

            Text(
                text = "Paired Devices",
                color = Color.Gray,
                style = customizedTextStyle(fontSize = 14, fontWeight = 600),
                modifier = Modifier
            )

            Spacer(modifier = Modifier.weight(0.5F))

            if(isDeviceScanning){
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp).align(Alignment.CenterVertically),
                    color = Color.Cyan,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = modifier.fillMaxWidth()
        ) {
            items(items = bluetoothDevices, itemContent = {
                bluetoothDevices ->
                if (bluetoothDevices.name != null
                ) {
                    PairdDeviceItem(
                        bluetoothDevice = bluetoothDevices,
                        onClick = onConnectDevice,
                    )
                }
            })
        }
    }


}

@Preview
@Composable
private fun PreviewPairedDevices() {
    ViewUtil.CenterColumn {
        PairedDevices(
            bluetoothDevices = arrayOf(),
            isDeviceScanning = true
        )
    }
}