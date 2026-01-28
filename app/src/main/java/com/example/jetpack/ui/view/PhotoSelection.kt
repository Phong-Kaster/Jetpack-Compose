package com.example.jetpack.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.jetpack.R
import com.example.jetpack.ui.theme.InterFontFamily

@Composable
fun PhotoSelection(
    imageUrl: String? = null,
    onClearPhoto: () -> Unit = {},
    onSelectPhoto: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFFEAEAEA))
    ) {
        if (imageUrl != null) {


            if (LocalInspectionMode.current) {
                // Display selected image in preview mode
                Image(
                    painter = painterResource(id = R.drawable.img_showcase_7),
                    contentDescription = "Selected image",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .matchParentSize()
                        .blur(100.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.img_showcase_7),
                    contentDescription = "Selected image",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .matchParentSize()
                )
            } else {
                // Display selected image
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Selected image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .matchParentSize()
                        .blur(100.dp)
                )

                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Selected image",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .matchParentSize()
                )
            }


            // Remove button
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Black.copy(alpha = 0.6f))
                    .clickable(onClick = onClearPhoto),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_remove),
                    contentDescription = "Remove",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        } else {
            // Empty state - clickable to open photo picker
            PhotoEmptyLayout(
                onClick = onSelectPhoto,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

/**
 * Empty image state component.
 * Displays when no image is selected, allows user to click to open photo picker.
 */
@Composable
private fun PhotoEmptyLayout(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFFEAEAEA))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 32.dp)
        ) {
            // Icon
            Icon(
                painter = painterResource(id = R.drawable.ic_image_empty),
                contentDescription = "Select image",
                tint = Color(0xFF797979),
                modifier = Modifier.size(56.dp)
            )

            // Text
            Text(
                text = "Tap to upload your image - Accepts JPG, JPEG, PNG files up to 10MB",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF505050),
                    lineHeight = 20.sp,
                    fontFamily = InterFontFamily,
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun PhotoSelectionPreview() {
    PhotoSelection(
        imageUrl = null,
        onClearPhoto = {},
        onSelectPhoto = {},
        modifier = Modifier
            .fillMaxWidth()
            .alpha(1f)
    )
}

@Preview
@Composable
private fun PreviewPhotoEmptyLayout(){
    PhotoEmptyLayout(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .alpha(1f)
    )
}