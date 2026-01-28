package com.example.jetpack.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpack.R
import com.example.jetpack.ui.theme.PrimaryColor
import kotlinx.coroutines.delay

@Composable
fun CoreTopBar4(
    modifier: Modifier = Modifier,
    title: String = "Setting",
    onBackPressed: (() -> Unit)? = null,
    backIcon: Int = R.drawable.ic_back,
    actionContent: @Composable RowScope.() -> Unit = {},
) {
    Column(modifier = modifier) {

//        Spacer(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 8.dp)
//                .dynamicStatusBarPadding()
//        )

        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            if (onBackPressed != null) {
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .clickable(
                            onClick = onBackPressed,
                        )
                        .align(Alignment.CenterStart)
                ) {
                    Icon(
                        modifier = Modifier.align(Alignment.Center),
                        painter = painterResource(id = backIcon),
                        contentDescription = "back",
                        tint = Color.White
                    )
                }
            }

            Text(
                text = title,
                modifier = Modifier.align(Alignment.Center),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                )
            )

            Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                actionContent()
            }
        }
    }
}

private var maximumStatusBarHeight by mutableStateOf(0.dp)

@Composable
fun Modifier.dynamicStatusBarPadding(): Modifier = this.composed {
    // Get current status bar height
    val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    // Set initial background color
    var backgroundColor by remember { mutableStateOf(Color.Transparent) }

    // When status bar height changes & status bar is hidden when splash appears
    // then delay 1 second before changing background color to black
    LaunchedEffect(statusBarHeight) {
        if (statusBarHeight == 0.dp) {
            delay(1000)
            backgroundColor = Color.Black
        } else {
            backgroundColor = Color.Transparent
        }
    }

    // Whenever status bar height is greater than previous maximum height
    // update the maximum height (maximumStatusBarHeight)
    LaunchedEffect(statusBarHeight) {
        if (statusBarHeight > maximumStatusBarHeight) maximumStatusBarHeight = statusBarHeight
    }

    Modifier
        .background(color = backgroundColor)
        .padding(top = maximumStatusBarHeight)
}

@Preview
@Composable
private fun PreviewCoreTopBar4() {
    CoreTopBar4(
        title = "Settings",
        onBackPressed = {},
        actionContent = {
            // Done button - purple circular button with checkmark
            Box(
                modifier = Modifier
                    .size(23.dp)
                    .clip(CircleShape)
                    .background(PrimaryColor)
                    .clickable(onClick = {}),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_check),
                    contentDescription = "Done",
                    tint = Color.Black,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    )
}