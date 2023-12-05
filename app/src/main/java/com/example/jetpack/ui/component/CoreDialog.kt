package com.example.jetpack.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.jetpack.R
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.body12
import com.example.jetpack.ui.theme.body18
import com.example.jetpack.util.ViewUtil

@Composable
fun CoreDialog(
    enable: Boolean,
    onDismissRequest: () -> Unit = {},
    onConfirm: () -> Unit = {},
    onCancel: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    if (enable) {
        Dialog(
            onDismissRequest = onDismissRequest,
            content = {
                CoreDialogLayout()
            }
        )
    }
}

@Composable
fun CoreDialogLayout(
    title: String = stringResource(R.string.are_you_sure_to_exit),
    content: String? = null,
    onConfirm: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
    onCancel: () -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(color = Background, shape = RoundedCornerShape(15.dp))
            .padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Rounded.Warning, contentDescription = null, tint = PrimaryColor,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(
            text = title,
            color = PrimaryColor,
            style = body18
        )
        if(content != null){
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(id = R.string.fake_message),
                color = PrimaryColor, style = body12,
                textAlign = TextAlign.Center
            )
        }

    }
}

@Preview
@Composable
fun PrevCoreDialogLayout() {
    ViewUtil.PreviewContent {
        CoreDialogLayout()
    }
}