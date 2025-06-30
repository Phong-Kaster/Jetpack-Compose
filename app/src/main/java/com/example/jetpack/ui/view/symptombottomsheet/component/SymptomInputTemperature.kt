package com.example.jetpack.ui.view.symptombottomsheet.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apero.lunacycle.R
import com.apero.lunacycle.domain.enums.TemperatureUnit
import com.apero.lunacycle.domain.model.BodyTemperature
import com.apero.lunacycle.ui.theme.customizedTextStyle
import com.apero.lunacycle.ui.theme.outerShadow

@Composable
fun SymptomInputTemperature(
    onTextChanged: (BodyTemperature) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    var temperatureUnit by remember { mutableStateOf(TemperatureUnit.Celsius) }
    val floatPattern = remember { Regex("^\\d*\\.?\\d*$") }

    var temperature by remember { mutableStateOf("") }
    var temperatureInstance by remember { mutableStateOf(BodyTemperature()) }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .outerShadow(
                shadowRadius = 2.dp,
                spreadRadius = 2.dp,
                shadowColor = Color(0x1A000000),
                shape = RoundedCornerShape(30.dp)
            )
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(30.dp)),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier
                .fillMaxWidth()

                .clip(shape = RoundedCornerShape(16.dp))
                .background(color = Color.White)
                .padding(vertical = 16.dp),
        ) {
            Box(modifier = Modifier) {
                MaterialTheme(
                    shapes = Shapes(extraSmall = RoundedCornerShape(16.dp)),
                    colorScheme = MaterialTheme.colorScheme.copy(surfaceContainer = Color.White)
                ) {
                    // weight unit
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(4.dp))
                            .clickable { expanded = true },

                        ) {
                        // °C
                        Text(
                            modifier = Modifier
                                .width(40.dp)
                                .clip(shape = RoundedCornerShape(5.dp)),
                            text = temperatureUnit.signal,
                            textAlign = TextAlign.End,
                            style = customizedTextStyle(
                                fontSize = 18,
                                fontWeight = 500,
                                color = Color.Black
                            ),
                        )

                        // DROP DOWN ICON
                        Icon(
                            modifier = Modifier
                                .size(24.dp)
                                .rotate(degrees = if (expanded) 180f else 0f),
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown",
                            tint = Color(0xFF424242),
                        )
                    }

                    DropdownMenu(
                        modifier = Modifier
                            .background(Color.White)
                            .align(Alignment.TopEnd),
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        TemperatureUnit.entries.forEach { it: TemperatureUnit ->
                            val selected = it == temperatureUnit
                            DropdownMenuItem(
                                modifier = Modifier.background(
                                    color = if (selected) {
                                        Color(0xFFFFF3F2)
                                    } else {
                                        Color.White
                                    }
                                ),
                                text = {
                                    Text(
                                        text = it.name,
                                        style = if (selected) {
                                            customizedTextStyle(
                                                fontSize = 18,
                                                fontWeight = 600,
                                                color = Color(0xFFFF9988)
                                            )
                                        } else {
                                            customizedTextStyle(
                                                fontSize = 18,
                                                fontWeight = 500,
                                                color = Color(0xFF111111)
                                            )
                                        },
                                    )
                                },
                                onClick = {
                                    expanded = false
                                    temperatureUnit = it
                                    temperatureInstance = temperatureInstance.copy(unit = temperatureUnit)
                                    onTextChanged(temperatureInstance)
                                }
                            )
                        }
                    }


                }
            }

            // Vertical divider
            Spacer(
                modifier = Modifier
                    .height(21.dp)
                    .width(1.dp)
                    .clip(shape = CircleShape)
                    .background(color = Color(0xFFD5D5D5))
            )

            BasicTextField(
                modifier = Modifier.weight(1f),
                value = temperature,
                onValueChange = {
                    val lengthBeforeDot = if (it.contains('.')) it.indexOf('.') else it.length
                    val lengthAfterDot =
                        if (it.contains('.')) it.length - it.indexOf('.') - 1 else 0
                    if (it.matches(floatPattern) && lengthBeforeDot <= 4 && lengthAfterDot <= 2) {
                        temperature = it
                        temperatureInstance = temperatureInstance.copy(value = temperature.toFloatOrNull() ?: 0f)
                        onTextChanged(temperatureInstance)
                    }
                },
                textStyle = customizedTextStyle(
                    fontSize = 18,
                    fontWeight = 400,
                    color = Color.Black
                ),
                maxLines = 1,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal,
                ),

                decorationBox = { innerTextField ->
                    if (temperature.isEmpty()) {
                        Text(
                            text = stringResource(R.string.enter_your_weight),
                            style = customizedTextStyle(
                                fontSize = 18,
                                fontWeight = 400,
                                color = Color(0xFF888888)
                            ),
                        )
                    }
                    innerTextField()
                },

                )


        }
    }
}

@Preview
@Composable
private fun PreviewSymptomInputTemperature() {
    SymptomInputTemperature()
}