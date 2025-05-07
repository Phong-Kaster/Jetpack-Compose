package com.example.jetpack.ui.fragment.intro.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.core.base.LocalTheme
import com.example.jetpack.util.ViewUtil

@Composable
fun IntroIndicator(currentPage: Int, pageSize: Int, modifier: Modifier = Modifier) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        (0 until pageSize).forEach {
            Spacer(
                modifier = Modifier
                    .size(10.dp)
                    .clip(shape = CircleShape)
                    .background(color = if (currentPage == it) LocalTheme.current.primary else LocalTheme.current.dim)
            )
        }
    }
}

@Preview
@Composable
fun PreviewIntroIndicator() {
    ViewUtil.PreviewContent {
        IntroIndicator(
            currentPage = 1, pageSize = 3, modifier = Modifier.fillMaxWidth()
        )
    }
}