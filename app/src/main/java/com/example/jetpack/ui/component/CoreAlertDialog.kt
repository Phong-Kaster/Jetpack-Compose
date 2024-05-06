package com.example.jetpack.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.jetpack.R
import com.example.jetpack.ui.theme.PrimaryColor

@Composable
fun CoreAlertDialog(
    enable: Boolean,
    onDismissRequest: () -> Unit = {},
) {
    if (enable) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            text = {
                Text(
                    text = "Functionality not available \uD83D\uDE48",
                    style = MaterialTheme.typography.bodyMedium,
                    color = PrimaryColor
                )
            },
            confirmButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(
                        text = stringResource(id = R.string.ok),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(
                        text = stringResource(R.string.cancel),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        )
    }
}