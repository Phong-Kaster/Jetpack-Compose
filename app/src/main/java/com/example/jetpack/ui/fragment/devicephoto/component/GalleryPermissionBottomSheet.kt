package com.example.jetpack.ui.fragment.devicephoto.component


import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.ui.component.CoreBottomSheet
import com.example.jetpack.ui.theme.customizedTextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryPermissionBottomSheet(
    enable: Boolean,
    isCameraGranted: Boolean = false,
    isStorageGranted: Boolean = false,
    onDismiss: () -> Unit = {},
    onCameraGranted: () -> Unit = {},
    onStorageGranted: () -> Unit = {},
) {
    CoreBottomSheet(
        enable = enable,
        containerColor = Color(0xff201E21),
        onDismissRequest = onDismiss
    ) {
        PhotosPermissionBottomSheetLayout(
            onDismiss = onDismiss,
            cameraGranted = isCameraGranted,
            storageGranted = isStorageGranted,
            onCameraGranted = onCameraGranted,
            onStorageGranted = onStorageGranted
        )
    }
}

@Composable
fun PhotosPermissionBottomSheetLayout(
    onDismiss: () -> Unit = {},
    cameraGranted: Boolean = false,
    storageGranted: Boolean = false,
    onCameraGranted: () -> Unit = {},
    onStorageGranted: () -> Unit = {},
) {
    Column {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 22.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)

        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        onDismiss()
                    }
                )
            }

            Image(
                modifier = Modifier.size(107.dp),
                painter = painterResource(R.drawable.img_lock_permission),
                contentDescription = "",
            )

            Spacer(modifier = Modifier.height(16.dp))


            // Camera switch & rationale
            PermissionSwitch(
                title = stringResource(R.string.camera),
                description = stringResource(R.string.camera_perf_des),
                checked = cameraGranted,
                onCheckedChange = { boolean ->
                    onCameraGranted()
                },
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )

            // Library switch & rationale
            PermissionSwitch(
                title = stringResource(R.string.library),
                description = stringResource(R.string.this_enables_you_to_select),
                checked = storageGranted,
                onCheckedChange = { boolean ->
                    onStorageGranted()
                },
            )
        }
    }
}

@Composable
fun PermissionSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit = {},
    title: String = stringResource(R.string.camera),
    description: String = stringResource(R.string.camera_perf_des),
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        CustomSwitch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            switchColor = Color(0xFF35A0F5),
            disableBackgroundColor = Color.White.copy(alpha = 0.15f),
            thumbColor = Color.White,
            modifier = Modifier
        )

        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                text = title,
                style = customizedTextStyle(
                    fontSize = 16,
                    fontWeight = 600,
                    color = Color.White
                )
            )

            Text(
                text = description,
                style = customizedTextStyle(
                    fontSize = 16,
                    fontWeight = 400,
                    color = Color.White.copy(alpha = 0.6f)
                )
            )
        }
    }
}

@Composable
fun CustomSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    width: Dp = 42.dp,
    height: Dp = 24.dp,
    switchColor: Color = Color.Green,
    disableBackgroundColor: Color = Color.LightGray,
    enableBackgroundColor: Brush = Brush.horizontalGradient(
        colors = listOf(Color(0xFF2ACDCD), Color(0xFFF236F5))
    ),
    thumbColor: Color = Color.White
) {
    val transition = updateTransition(targetState = checked, label = "SwitchTransition")

    val thumbOffset by transition.animateDp(
        label = "ThumbOffset"
    ) { state ->
        if (state) width - height else 0.dp
    }


    Box(
        modifier = modifier
            .width(width)
            .height(height)
            .clip(CircleShape)
            .then(
                if (checked) {
                    Modifier.background(switchColor)
                } else {
                    Modifier.background(disableBackgroundColor)
                }
            )
            .clickable { onCheckedChange(!checked) },
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .size(height)
                .offset(x = thumbOffset)
                .clip(CircleShape)
                .background(thumbColor)
        )
    }
}

@Preview
@Composable
fun PreviewImagesPermissionBottomSheet() {
    PhotosPermissionBottomSheetLayout()
}