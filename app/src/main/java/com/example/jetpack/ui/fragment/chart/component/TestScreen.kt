package com.example.jetpack.ui.fragment.chart.component


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import androidx.core.graphics.createBitmap
import com.example.jetpack.core.CoreLayout

// 1. Contrast Adjustment Function
fun adjustContrast(bitmap: Bitmap, contrast: Float): Bitmap {
    val cm = ColorMatrix().apply {
        // Adjust contrast
        val scale = contrast + 1f
        val translate = (-0.5f * scale + 0.5f) * 255f // Scale the pixels based on contrast.
        set(
            floatArrayOf(
                scale, 0f, 0f, 0f, translate,
                0f, scale, 0f, 0f, translate,
                0f, 0f, scale, 0f, translate,
                0f, 0f, 0f, 1f, 0f
            )
        )
    }

    val filter = ColorMatrixColorFilter(cm)
    val newBitmap =
        createBitmap(bitmap.width, bitmap.height, bitmap.config ?: Bitmap.Config.ARGB_8888)
    val canvas = android.graphics.Canvas(newBitmap) // Use the fully qualified name.
    val paint = android.graphics.Paint() // Use the fully qualified name.
    paint.colorFilter = filter
    canvas.drawBitmap(bitmap, 0f, 0f, paint)
    return newBitmap
}

// 2. White Balance Adjustment Function
fun adjustWhiteBalance(bitmap: Bitmap, temperature: Float, tint: Float): Bitmap {
    val kelvin = 6500f // Standard daylight temperature (adjust as needed)
    val tempRatio = temperature / kelvin

    // Temperature is measured in Kelvin. Tint is from -1 (green) to 1 (magenta)
    val r = (255 * tempRatio).coerceIn(0f, 255f)
    val g = (255 * tempRatio).coerceIn(0f, 255f)
    val b = 255f

    val tintR = (tint).coerceIn(-1f, 1f) // Tint range -1 to 1
    val tintG = (tint).coerceIn(-1f, 1f)
    val tintB = (tint).coerceIn(-1f, 1f)


    val cm = ColorMatrix().apply {
        set(
            floatArrayOf(
                tintR, 0f, 0f, 0f, r - 255, // Adjust Red based on temperature and tint
                0f, tintG, 0f, 0f, g - 255, // Adjust Green based on temperature and tint
                0f, 0f, tintB, 0f, b - 255, // Adjust Blue based on temperature and tint
                0f, 0f, 0f, 1f, 0f
            )
        )
    }

    val colorFilter = ColorMatrixColorFilter(cm)
    val newBitmap =
        createBitmap(bitmap.width, bitmap.height, bitmap.config ?: Bitmap.Config.ARGB_8888)
    val canvas = android.graphics.Canvas(newBitmap)
    val paint = android.graphics.Paint()
    paint.colorFilter = colorFilter
    canvas.drawBitmap(bitmap, 0f, 0f, paint)
    return newBitmap
}

// 3. (Optional) Combine Adjustments
fun adjustContrastAndWhiteBalance(
    bitmap: Bitmap,
    contrast: Float,
    temperature: Float,
    tint: Float
): Bitmap {
    var tempBitmap = adjustContrast(bitmap, contrast)
    tempBitmap = adjustWhiteBalance(tempBitmap, temperature, tint)
    return tempBitmap
}

// 4. Compose UI to Display the Image
@Composable
fun PhotoEditor(
    originalBitmap: Bitmap,
    contrast: Float, // -1f to 1f (or more)
    temperature: Float, // Kelvin, e.g., 2000 (warm) to 10000 (cool)
    tint: Float, // -1f (green) to 1f (magenta)
    modifier: Modifier = Modifier
) {

    val adjustedBitmap = adjustContrastAndWhiteBalance(originalBitmap, contrast, temperature, tint)
    val painter: Painter = BitmapPainter(adjustedBitmap.asImageBitmap())

    Image(
        painter = painter,
        contentDescription = "Edited Photo",
        modifier = modifier
    )
}

// Example Usage in your Composable
@Composable
fun TestScreen() {
    // Assume you have loaded your bitmap
    var myBitmap = with(LocalContext.current) {
        val inputStream = resources.openRawResource(R.raw.photo_test)
        BitmapFactory.decodeStream(inputStream)
    }
    var temperature by remember { mutableFloatStateOf(6000f) }
    var contrast by remember { mutableFloatStateOf(0f) }
    var tint by remember { mutableFloatStateOf(1f) }

    CoreLayout(
        bottomBar = {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Temperature") // Display the label for the slider
                Slider(
                    value = temperature,
                    onValueChange = { value -> temperature = value},
                    valueRange = 2000f..10000f,

                )
                Text(text = String.format("%.1f", temperature), //Display current value
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Text(text = "Contrast") // Display the label for the slider
                Slider(
                    value = contrast,
                    onValueChange = { value -> contrast = value},
                    valueRange = -1f..1f
                )
                Text(text = String.format("%.1f", contrast), //Display current value
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Text(text = "Tint") // Display the label for the slider
                Slider(
                    value = tint,
                    onValueChange = { value -> tint = value},
                    valueRange = -1f..1f
                )
                Text(text = String.format("%.1f", tint), //Display current value
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        },
        content = {
            PhotoEditor(
                originalBitmap = myBitmap,
                contrast = contrast, // Example contrast value
                temperature = temperature, // Example temperature
                tint = tint, // Example tint
                modifier = Modifier.fillMaxSize()
            )
        }
    )
}

@Preview
@Composable
private fun PreviewTestScreen() {
    TestScreen()
}