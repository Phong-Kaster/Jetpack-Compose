package com.example.jetpack.ui.modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Applies the given [block] to the modifier if [condition] is true.
 * Returns the original modifier if the condition is false.
 */
fun Modifier.conditional(
    condition: Boolean = true,
    block: Modifier.() -> Modifier
): Modifier =
    if (condition) {
        this.block()
    } else {
        this
    }


@Composable
private fun HowToUse() {

    val enable by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Full lambda version (explicit) ",
            modifier = Modifier.conditional(
                condition = enable,
                block = { this.padding(16.dp) }
            )
        )


        Text(
            text = "Multiple modifiers in full lambda",
            modifier = Modifier.conditional(
                condition = enable,
                block = {
                    this
                        .background(Color.Red.copy(alpha = 0.1f))
                        .padding(8.dp)
                }
            )
        )

    }
}