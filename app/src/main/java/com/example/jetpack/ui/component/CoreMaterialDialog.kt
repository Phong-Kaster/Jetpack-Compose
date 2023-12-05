package com.example.jetpack.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

/**
 * Alert Dialog - https://developer.android.com/jetpack/compose/components/dialog#alert*/
@Composable
fun CoreAlertDialog(
    enable: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogContent: String?,
    textButtonConfirm: String = "OK",
    textButtonCancel: String = "Cancel",
    icon: ImageVector?,
) {
    if (enable) {
        AlertDialog(
            icon = {
                if (icon != null) {
                    Icon(icon, contentDescription = null, modifier = Modifier.size(25.dp))
                }
            },
            title = {
                Text(text = dialogTitle)
            },
            text = {
                if (dialogContent != null) {
                    Text(text = dialogContent)
                }
            },
            onDismissRequest = {
                onDismissRequest()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirmation()
                    }
                ) {
                    Text(textButtonConfirm)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text(textButtonCancel)
                }
            }
        )
    }
}

/**
 * Alert Dialog - https://developer.android.com/jetpack/compose/components/dialog#alert*/
@Composable
fun CoreAlertDialog(
    enable: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogContent: String?,
    textButtonConfirm: String = "OK",
    textButtonCancel: String = "Cancel",
    @DrawableRes icon: Int?,
) {
    if (enable) {
        AlertDialog(
            icon = {
                if (icon != null) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                }
            },
            title = {
                Text(text = dialogTitle)
            },
            text = {
                if (dialogContent != null) {
                    Text(text = dialogContent)
                }
            },
            onDismissRequest = {
                onDismissRequest()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirmation()
                    }
                ) {
                    Text(textButtonConfirm)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text(textButtonCancel)
                }
            }
        )
    }
}
