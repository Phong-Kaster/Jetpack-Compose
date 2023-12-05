package com.example.jetpack.ui.fragment.home.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.jetpack.R
import com.example.jetpack.ui.component.CoreDialog
import com.example.jetpack.ui.component.CoreDialogLayout

@Composable
fun HomeDialog(
    enable: Boolean,
    onDismissRequest: () -> Unit = {},
    onConfirm: () -> Unit = {},
    onCancel: () -> Unit = {},
) {
    CoreDialog(
        enable = enable,
        onDismissRequest = onDismissRequest,
    ) {
        CoreDialogLayout(
            imageVector = Icons.Rounded.Warning,
            title = stringResource(id = R.string.are_you_sure_to_exit),
            content = stringResource(id = R.string.fake_message),
            onConfirm = {
                onDismissRequest()
                onConfirm()
            },
            onCancel =  {
                onDismissRequest()
                onCancel()
            }
        )
    }
}