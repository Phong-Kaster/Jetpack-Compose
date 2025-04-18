package com.example.jetpack.ui.fragment.chart.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.asImageBitmap
import android.graphics.Bitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import com.example.jetpack.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WhiteBalanceAdjustUI(
    originalBitmap: Bitmap, // Pass the original Bitmap
    onClose: () -> Unit, // Callback for the close button
    onApply: (Bitmap) -> Unit // Callback to apply changes
) {
    var temperature by remember { mutableStateOf(5500f) } // Kelvin
    var tint by remember { mutableStateOf(0f) } // -1 to 1
    var selectedAdjustment by remember { mutableStateOf("Temperature") } // Track the selected adjustment
    var adjustedBitmap by remember { mutableStateOf(originalBitmap) }


    // Function to apply edits
    fun applyWhiteBalance() {
        adjustedBitmap = adjustWhiteBalance(originalBitmap, temperature, tint)
    }


    // Function to handle the reset click
    fun resetAdjustments() {
        temperature = 5500f // Reset to default
        tint = 0f // Reset to default
        applyWhiteBalance()
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Top Bar
        TopAppBar(
            title = { Text("Adjust", fontSize = 20.sp) },
            navigationIcon = {
                IconButton(onClick = onClose) {
                    Icon(Icons.Filled.Close, contentDescription = "Close")
                }
            },
            actions = {
                IconButton(onClick = { resetAdjustments() }) { // Reset Button
                    Icon(Icons.Filled.Refresh, contentDescription = "Reset")
                }
                IconButton(onClick = { onApply(adjustedBitmap) }) { // Apply button
                    Icon(Icons.Filled.Check, contentDescription = "Apply")
                }
            }
        )

        // Adjustment Options (Icons)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AdjustmentOption(
                iconResId = R.drawable.ic_lightbulb, // Replace with your icon
                text = "Temperature",
                isSelected = selectedAdjustment == "Temperature",
                onClick = { selectedAdjustment = "Temperature" }
            )

            AdjustmentOption(
                iconResId = R.drawable.ic_collapsible_top_bar, // Replace with your icon
                text = "Tint",
                isSelected = selectedAdjustment == "Tint",
                onClick = { selectedAdjustment = "Tint" }
            )
        }

        // Slider (Based on selected adjustment)
        when (selectedAdjustment) {
            "Temperature" -> {
                SliderWithLabel(
                    value = temperature,
                    onValueChange = {
                        temperature = it
                        applyWhiteBalance()
                    },
                    valueRange = 2000f..10000f, // Kelvin range
                    label = "Temperature"
                )
            }
            "Tint" -> {
                SliderWithLabel(
                    value = tint,
                    onValueChange = {
                        tint = it
                        applyWhiteBalance()
                    },
                    valueRange = -1f..1f, // Tint range
                    label = "Tint"
                )
            }
        }
    }
}

@Composable
fun AdjustmentOption(
    iconResId: Int,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        val iconTint = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = text,
            tint = iconTint,
            modifier = Modifier.size(32.dp)
        )
        Text(text = text, color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray)
    }
}


@Composable
fun SliderWithLabel(
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    label: String
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = label) // Display the label for the slider
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange
        )
        Text(text = String.format("%.1f", value), //Display current value
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

// Preview (for testing in Android Studio)
@Preview(showBackground = true)
@Composable
fun WhiteBalanceAdjustUIPreview() {
    // Create a placeholder bitmap for the preview
    val placeholderBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
    var showUi by remember { mutableStateOf(true) }

    MaterialTheme {
        if(showUi){
            WhiteBalanceAdjustUI(
                originalBitmap = placeholderBitmap,
                onClose = { showUi = false },
                onApply = { newBitmap ->
                    // Handle applying the changes (e.g., update the parent UI)
                }
            )
        }
    }
}