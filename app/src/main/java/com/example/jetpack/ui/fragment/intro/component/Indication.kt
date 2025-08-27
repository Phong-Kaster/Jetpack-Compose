package com.example.jetpack.ui.fragment.intro.component


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.ui.theme.customizedTextStyle


@Composable
fun Indication(
    modifier: Modifier = Modifier,
    currentPage: Int,
    pageCount: Int,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pageCount) { iteration ->

                if (currentPage - 1 == iteration) {
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(shape = RoundedCornerShape(16.dp))
                            .background(color = Color.White)
                            .width(24.dp)
                            .height(6.dp)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color = Color.White.copy(alpha = 0.12f))
                            .size(6.dp)
                    )
                }

            }
        }

        Text(
            text = stringResource(R.string.ok),
            style = customizedTextStyle(
                fontSize = 18,
                color = Color(0xFFEE9FFC),
                fontWeight = 400,
            ),
            modifier = Modifier
                .padding(4.dp)
                .clip(RoundedCornerShape(6.dp))
                .align(Alignment.CenterEnd)
                .clickable {
                    onClick()
                },
        )
    }
}

@Preview
@Composable
private fun PreviewIndication() {
    Indication(
        modifier = Modifier.fillMaxWidth(),
        currentPage = 1,
        pageCount = 3,
        onClick = {},
    )
}