package com.example.jetpack.ui.fragment.intro

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.ui.fragment.intro.component.IntroContent
import com.example.jetpack.ui.fragment.intro.component.IntroIndicator
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.NavigationUtil
import com.example.jetpack.util.NavigationUtil.safeNavigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class IntroFragment : CoreFragment() {
    @Composable
    override fun ComposeView() {
        super.ComposeView()
        IntroLayout(
            onStart = {
                val destination = IntroFragmentDirections.toHome()
                safeNavigate(destination = destination)
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IntroLayout(
    onStart: () -> Unit = {}
) {

    val numberOfSlide = 3
    val pagerState = rememberPagerState(
        initialPage = Int.MAX_VALUE / numberOfSlide - (Int.MAX_VALUE / 2 % numberOfSlide),
        pageCount = { Int.MAX_VALUE }
    )

    // This launched effect helps us to scroll automatically
    LaunchedEffect(Unit) {
        while (true) {
            if (!pagerState.isScrollInProgress) {
                delay(2500)
                with(pagerState) {
                    val target = currentPage + 1
                    launch { pagerState.animateScrollToPage(target) }
                }
            } else {
                delay(1000)
            }
        }
    }

    CoreLayout(
        modifier = Modifier,
        content = {
            Column(modifier = Modifier.background(color = Background)) {
                Spacer(modifier = Modifier.height(20.dp))


                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(top = 40.dp, bottom = 32.dp)
                ) { index ->
                    when (index % numberOfSlide) {
                        0 -> IntroContent(
                            titleId = R.string.fake_title,
                            contentId = R.string.fake_message,
                            imageId = R.drawable.ic_nazi_eagle
                        )

                        1 -> IntroContent(
                            titleId = R.string.fake_title,
                            contentId = R.string.fake_message,
                            imageId = R.drawable.ic_nazi_eagle
                        )

                        2 -> IntroContent(
                            titleId = R.string.fake_title,
                            contentId = R.string.fake_message,
                            imageId = R.drawable.ic_nazi_eagle
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 0.dp, vertical = 12.dp)
                ) {
                    IntroIndicator(
                        currentPage = pagerState.currentPage % numberOfSlide,
                        pageSize = numberOfSlide,
                        modifier = Modifier.align(Alignment.Center),
                    )
                    Text(
                        text = stringResource(R.string.fake_title),
                        style = customizedTextStyle(20, 600),
                        color = PrimaryColor,
                        textDecoration = TextDecoration.None,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .clickable { onStart() }
                    )
                }
            }
        })
}


@Preview
@Composable
fun PreviewIntroLayout() {
    IntroLayout()
}