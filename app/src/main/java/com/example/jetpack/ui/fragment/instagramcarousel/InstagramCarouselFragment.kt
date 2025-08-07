package com.example.jetpack.ui.fragment.instagramcarousel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.base.CoreFragment
import com.example.jetpack.core.base.CoreLayout
import com.example.jetpack.core.base.LocalTheme
import com.example.jetpack.ui.component.CoreTopBar
import com.example.jetpack.ui.fragment.instagramcarousel.component.DraggableIndicator
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.NavigationUtil.safePopBackstack
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * # [Create Instagram-like Long Press and Draggable Carousel Indicators](https://proandroiddev.com/create-instagram-like-long-press-and-draggable-carousel-indicators-in-jetpack-compose-ce16fa75bc1e)
 * */
@AndroidEntryPoint
class InstagramCarouselFragment : CoreFragment() {

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        InstagramCarouselLayout(
            onBack = { safePopBackstack() },
        )
    }
}

@Composable
fun InstagramCarouselLayout(
    onBack: () -> Unit = {},
) {

    val list: List<Color> = listOf(
        Color.Red,
        Color.DarkGray,
        Color.Blue,
        Color.Cyan,
        Color.Magenta,
        Color.Green,
    )

    val state = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) { list.size } // Here, list is images list
    val coroutineScope = rememberCoroutineScope()

    CoreLayout(
        topBar = {
            CoreTopBar(
                title = stringResource(R.string.instagram_carousel),
                leftIcon = R.drawable.ic_back,
                onClickLeft = onBack
            )
        },
        bottomBar = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = LocalTheme.current.background)
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.long_press_on_indicator),
                    style = customizedTextStyle(
                        fontSize = 16,
                        fontWeight = 500,
                        color = Color.White
                    )
                )

                DraggableIndicator(
                    modifier = Modifier,
                    state = state,
                    itemCount = list.size,
                    onPageChange = { page ->
                        coroutineScope.launch {
                            state.scrollToPage(page)
                        }
                    },
                )
            }

        },
        content = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Background)
                    .padding(16.dp)
            ) {
                HorizontalPager(
                    modifier = Modifier.fillMaxSize(),
                    state = state,
                ) { page ->
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(15.dp))
                            .background(color = list[page])
                            .size(280.dp)
                    )
                }
            }
        })
}

@Preview
@Composable
private fun PreviewInstagramCarouselLayout() {
    InstagramCarouselLayout()
}