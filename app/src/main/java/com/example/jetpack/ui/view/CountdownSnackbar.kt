package com.example.jetpack.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

/**
 * Countdown Snackbar
 *
 * ⏳ snackbarData ➜ Data for the snackbar.
 *
 * ⏳ modifier ➜ Modifier to be applied to the snackbar.
 *
 * ⏳ durationInSeconds ➜ Duration of the countdown timer.
 *
 * ⏳ actionOnNewLine ➜ Whether to display the action on a separate line.
 *
 * ⏳ shape ➜ The shape of the snackbar’s container.
 *
 * ⏳ containerColor, contentColor, actionColor, actionContentColor, dismissActionContentColor ➜ Various color styling parameters.
 */
@Composable
fun CountdownSnackbar(
    snackbarData: SnackbarData,
    durationInSeconds: Int = 5,
    actionOnNewLine: Boolean = false,
    shape: androidx.compose.ui.graphics.Shape = SnackbarDefaults.shape,
    containerColor: Color = SnackbarDefaults.color,
    contentColor: Color = SnackbarDefaults.contentColor,
    actionColor: Color = SnackbarDefaults.actionColor,
    actionContentColor: Color = SnackbarDefaults.actionContentColor,
    dismissActionContentColor: Color = SnackbarDefaults.dismissActionContentColor,
    modifier: Modifier = Modifier,
) {
    val totalDuration = remember(durationInSeconds) { durationInSeconds * 1000 }
    var millisRemaining by remember { mutableIntStateOf(totalDuration) }

    /*Using a 40-millisecond interval results in a 25 FPS progress update, which is quite smooth for the human eye*/
    val interval = 40L

    /*we calculate the total duration in milliseconds and manage the remaining time using a state variable*/
    LaunchedEffect(snackbarData) {
        while (millisRemaining > 0) {
            delay(interval)
            millisRemaining -= interval.toInt()
        }
        snackbarData.dismiss()
    }

    // Define the action button if an action label is provided
    val actionLabel = snackbarData.visuals.actionLabel
    val actionComposable: (@Composable () -> Unit)? = if (actionLabel == null) {
        null
    } else {
        @Composable {
            TextButton(
                colors = ButtonDefaults.textButtonColors(contentColor = actionColor),
                onClick = { snackbarData.performAction() },
                content = { Text(actionLabel) }
            )
        }
    }

    // Define the dismiss button if the snackbar includes a dismiss action
    val dismissActionComposable: (@Composable () -> Unit)? =
        if (snackbarData.visuals.withDismissAction) {
            @Composable {
                IconButton(
                    onClick = { snackbarData.dismiss() },
                    content = {
                        Icon(Icons.Rounded.Close, null)
                    }
                )
            }
        } else {
            null
        }


    Snackbar(
        modifier = modifier.padding(12.dp), // Apply padding around the snackbar
        action = actionComposable,
        actionOnNewLine = actionOnNewLine,
        dismissAction = dismissActionComposable,
        dismissActionContentColor = dismissActionContentColor,
        actionContentColor = actionContentColor,
        containerColor = containerColor,
        contentColor = contentColor,
        shape = shape,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CountdownSnackbarTime(
                // Calculate the progress of the timer
                timerProgress = millisRemaining.toFloat() / totalDuration.toFloat(),
                // Calculate the remaining seconds
                secondsRemaining = (millisRemaining / 1000) + 1,
                color = contentColor
            )
            // Display the message
            Text(snackbarData.visuals.message)
        }
    }
}

@Preview
@Composable
private fun PreviewCountdownSnackbar() {
    //CountdownSnackbar()
}