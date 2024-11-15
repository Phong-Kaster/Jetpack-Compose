package com.example.jetpack.ui.fragment.intro

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.example.jetpack.R
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.ui.fragment.intro.component.IntroContent
import com.example.jetpack.ui.fragment.intro.component.IntroIndicator
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.NavigationUtil.safeNavigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class IntroFragment : CoreFragment() {
    // this view model survives only in the lifecycle of Intro Graph. If this graph is killed,
    // this view is also killed
    private val viewModel by hiltNavGraphViewModels<IntroViewModel>(R.id.introGraph)


    @Composable
    override fun ComposeView() {
        super.ComposeView()
        IntroLayout(
            onStart = {
                viewModel.setEnableIntro(enable = false)

//                val destination = IntroFragmentDirections.fromIntroToHome()
//                safeNavigate(destination = destination)

                val destination = R.id.fromIntroToHome
                safeNavigate(destination)
            }
        )
    }
}

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

    LaunchedEffect(key1 = pagerState.settledPage) {
        launch {
            delay(1000)
            with(pagerState) {
                val target = if (settledPage < pageCount - 1) settledPage + 1 else 0
                animateScrollToPage(
                    page = target,
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                )
            }
        }
    }

    CoreLayout(
        modifier = Modifier,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = LocalTheme.current.background)
            ) {

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
                            imageId = R.drawable.ic_iron_cross_bundeswehr
                        )

                        1 -> IntroContent(
                            titleId = R.string.fake_title,
                            contentId = R.string.fake_message,
                            imageId = R.drawable.ic_nazi_wehtmatch
                        )

                        2 -> IntroContent(
                            titleId = R.string.fake_title,
                            contentId = R.string.fake_message,
                            imageId = R.drawable.ic_iron_cross
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    IntroIndicator(
                        currentPage = pagerState.currentPage % numberOfSlide,
                        pageSize = numberOfSlide,
                        modifier = Modifier.align(Alignment.Center),
                    )
                    Text(
                        text = stringResource(R.string.let_s_go),
                        style = customizedTextStyle(20, 600),
                        color = LocalTheme.current.onPrimary,
                        textDecoration = TextDecoration.None,
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(20.dp))
                            .clickable { onStart() }
                            .background(color = LocalTheme.current.primary)
                            .padding(horizontal = 15.dp, vertical = 5.dp)
                            .align(Alignment.CenterEnd)

                    )
                }

                Spacer(modifier = Modifier.height(60.dp))
            }
        })
}


@Preview
@Composable
fun PreviewIntroLayout() {
    IntroLayout()
}