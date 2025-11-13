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
 * A clickable modifier that ignores fast double clicks.
 *
 * Use this instead of [Modifier.clickable] when you want to prevent
 * actions (like navigation or API calls) from being triggered too many times
 * in a short period.
 *
 * @param debounceTime  Minimum time (in milliseconds) between two valid clicks.
 *                      Clicks faster than this are ignored. Default = 600ms.
 * @param enabled       Whether the button is active or not.
 * @param onClick       The action to run when the user clicks (and it's not too soon).
 *
 * Example:
 * ```
 * Button(
 *     modifier = Modifier.debouncedClickable {
 *         viewModel.onSubmit()
 *     }
 * ) {
 *     Text("Submit")
 * }
 * ```
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