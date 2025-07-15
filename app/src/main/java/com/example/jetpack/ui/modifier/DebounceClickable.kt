package com.example.jetpack.ui.modifier


import androidx.compose.foundation.clickable
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

/**
 * Adds a clickable modifier to a composable with a debounce mechanism to prevent rapid consecutive clicks.
 *
 * @param enabled Determines whether the clickable modifier is active. Defaults to `true`.
 * @param onClick Lambda function to be executed when the composable is clicked.
 * @param debounceTime The minimum time interval (in milliseconds) between consecutive clicks. Defaults to `500L`.
 * @return A `Modifier` that applies the debounce clickable behavior.
 */
inline fun Modifier.debounceClickable(
    enabled: Boolean = true,
    crossinline onClick: () -> Unit,
    debounceTime: Long = 500L // Default debounce time in milliseconds
): Modifier = composed {
    // Stores the timestamp of the last click
    val lastClickTime = remember { mutableLongStateOf(0L) }

    // If the modifier is not enabled, return the original Modifier
    if (!enabled) return@composed this

    // Adds a clickable behavior with debounce logic
    clickable {
        val currentTime = System.currentTimeMillis()
        // Executes the click action only if the time since the last click exceeds the debounce time
        if (currentTime - lastClickTime.longValue > debounceTime) {
            lastClickTime.longValue = currentTime
            onClick()
        }
    }
}