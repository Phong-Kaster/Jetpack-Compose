package com.example.jetpack.ui.modifier


import androidx.compose.foundation.clickable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun Modifier.debouncedClickable(
    debounceTime: Long = 600L,
    enabled: Boolean = true,
    onClick: () -> Unit
): Modifier = composed {
    var lastClickTime by remember { mutableStateOf(0L) }

    Modifier.clickable(enabled = enabled) {
        val now = System.currentTimeMillis()
        if (now - lastClickTime >= debounceTime) {
            lastClickTime = now
            onClick()
        }
    }
}