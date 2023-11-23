package com.example.jetpack.ui.fragment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetpack.core.CoreFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : CoreFragment() {

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        HomeContent()
    }
}


@Composable
fun HomeContent() {
    Column(modifier = Modifier.fillMaxSize().background(color = Color.DarkGray)) {
        Text(text = "Android", modifier =Modifier.align(Alignment.CenterHorizontally))
    }
}

@Preview
@Composable
fun PreviewHome() {
    HomeContent()
}