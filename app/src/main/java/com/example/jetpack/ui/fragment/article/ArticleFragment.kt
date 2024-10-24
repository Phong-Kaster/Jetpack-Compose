package com.example.jetpack.ui.fragment.article

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.configuration.Language
import com.example.jetpack.configuration.Menu
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.domain.enums.Subsetting
import com.example.jetpack.ui.component.CoreAlertDialog
import com.example.jetpack.ui.component.CoreBottomBar
import com.example.jetpack.ui.component.CoreExpandableFloatingButton
import com.example.jetpack.ui.component.CoreTextAnimationDialog
import com.example.jetpack.ui.component.SquareElement
import com.example.jetpack.ui.dialog.WheelTimePickerDialog
import com.example.jetpack.ui.fragment.home.component.HomeDialog
import com.example.jetpack.ui.fragment.home.component.HomeTopBar
import com.example.jetpack.ui.modifier.borderWithAnimatedGradient
import com.example.jetpack.ui.modifier.doublePulseEffect
import com.example.jetpack.ui.modifier.pulseEffect
import com.example.jetpack.ui.view.AnalogueClock
import com.example.jetpack.ui.view.AnimatedBorderCard
import com.example.jetpack.ui.view.AnimatedThemeSwitcher
import com.example.jetpack.ui.view.AtomicLoader
import com.example.jetpack.ui.view.ContextualFlowRowSample
import com.example.jetpack.ui.view.DNAHelix
import com.example.jetpack.ui.view.SubsettingElement
import com.example.jetpack.ui.view.WeatherSunrise
import dagger.hilt.android.AndroidEntryPoint

/**
 * Animate borders in Jetpack Compose - https://proandroiddev.com/animate-borders-in-jetpack-compose-ca359deed7d5
 */
@AndroidEntryPoint
class ArticleFragment : CoreFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val (even, odd) = numbers.partition { it % 2 == 0 }

        Log.d("TAG", "onCreateView - even = $even")
        Log.d("TAG", "onCreateView - odd = $odd")

        return super.onCreateView(inflater, container, savedInstanceState)
    }


    @Composable
    override fun ComposeView() {
        super.ComposeView()
        ArticleLayout()
    }
}

/**
 * For Columm:
 *     val scrollState = rememberScrollState()
 *     val fabExtended by remember { derivedStateOf { scrollState.value == 0 } }
 * For Lazy Column/ Lazy Grid/ Lazy Row
 *     val state = rememberLazyGridState()
 *     val fabExtended by remember { derivedStateOf { state.firstVisibleItemIndex == 0 } }
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArticleLayout() {
    // for lazy grid state
    val state = rememberLazyGridState()
    val extended by remember { derivedStateOf { state.firstVisibleItemIndex > 0 } }

    CoreLayout(
        topBar = { HomeTopBar(name = stringResource(id = Menu.Article.nameId)) },
        bottomBar = { CoreBottomBar() },
        floatingActionButton = {
            CoreExpandableFloatingButton(
                extended = extended,
                modifier = Modifier
            )
        },
        backgroundColor = LocalTheme.current.background,
    ) {
        // Remove overscroll effect for lazy grid
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null
        ) {
            LazyVerticalGrid(
                state = state,
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 32.dp)
                    .fillMaxSize()
            ) {
                item(key = "AnimatedBorder", span = { GridItemSpan(2) }) {
                    AnimatedBorderCard()
                }

                item(key = "WeatherSunrise", span = { GridItemSpan(2) }) {
                    WeatherSunrise(
                        modifier = Modifier
                            .borderWithAnimatedGradient(
                                colorBackground = LocalTheme.current.background,
                                width = 3.dp,
                                shape = RoundedCornerShape(25.dp)
                            )
                            .clip(shape = RoundedCornerShape(25.dp))
                    )
                }

                item(key = "DNAHelix", span = { GridItemSpan(2) }) {
                    DNAHelix(
                        firstColor = Color.Yellow,
                        secondColor = Color.Red,
                        lineBrush = { _, _ -> SolidColor(Color.White) },
                        cycleDuration = 15000,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .borderWithAnimatedGradient(
                                colorBackground = LocalTheme.current.background,
                                width = 3.dp,
                                shape = RoundedCornerShape(25.dp)
                            )
                            .clip(shape = RoundedCornerShape(25.dp))
                            .background(
                                color = LocalTheme.current.background,
                                shape = RoundedCornerShape(25.dp)
                            )
                            .padding(vertical = 16.dp, horizontal = 16.dp)
                    )
                }




                item(
                    key = "PulseEffect",
                    span = { GridItemSpan(1) },
                    content = {
                        IconButton(
                            onClick = {},
                            modifier = Modifier
                                .fillMaxWidth()
                                .borderWithAnimatedGradient(
                                    colorBackground = LocalTheme.current.background,
                                    width = 3.dp,
                                    shape = RoundedCornerShape(25.dp)
                                )
                                .clip(shape = RoundedCornerShape(25.dp))
                                .aspectRatio(1f)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_nazi_eagle),
                                contentDescription = "Icon",
                                tint = Color.White,
                                modifier = Modifier
                                    .pulseEffect(
                                        initialScale = 0f,
                                        targetScale = 1.2f,
                                        shape = CircleShape,
                                        brush = Brush.radialGradient(
                                            0.3f to Color.Yellow,
                                            0.6f to Color.Blue,
                                            1f to Color.Yellow
                                        )
                                    )
                                    .fillMaxWidth(0.8f)
                                    .aspectRatio(1f)
                                    .padding(16.dp)
                            )
                        }
                    }
                )

                item(
                    key = "DoublePulseEffect",
                    span = { GridItemSpan(1) },
                    content = {
                        IconButton(
                            onClick = {},
                            modifier = Modifier
                                .fillMaxWidth()
                                .borderWithAnimatedGradient(
                                    colorBackground = LocalTheme.current.background,
                                    width = 3.dp,
                                    shape = RoundedCornerShape(25.dp)
                                )
                                .clip(shape = RoundedCornerShape(25.dp))
                                .aspectRatio(1f)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_iron_cross_wehtmatch),
                                contentDescription = "Icon",
                                tint = Color.White,
                                modifier = Modifier
                                    .doublePulseEffect(
                                        initialScale = 0f,
                                        targetScale = 1f,
                                        shape = RoundedCornerShape(25.dp),
                                        brush = Brush.radialGradient(
                                            0.3f to Color.Yellow,
                                            0.6f to Color.Blue,
                                            1f to Color.Yellow
                                        ),
                                        duration = 3000,
                                    )
                                    .fillMaxWidth(0.8f)
                                    .aspectRatio(1f)
                                    .padding(16.dp)
                            )
                        }
                    }
                )

                item(
                    key = "AtomicLoader",
                    span = { GridItemSpan(1) },
                    content = {
                        AtomicLoader(
                            modifier = Modifier
                                .padding(5.dp)
                                .clip(shape = RoundedCornerShape(25.dp))
                                .width(150.dp)
                                .aspectRatio(1f)
                                .borderWithAnimatedGradient(
                                    colorBackground = LocalTheme.current.background,
                                    width = 3.dp,
                                    shape = RoundedCornerShape(25.dp)
                                )

                        )
                    }
                )

                item(
                    key = "AnalogueClock",
                    span = { GridItemSpan(1) },
                    content = {
                        AnalogueClock(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .borderWithAnimatedGradient(
                                    colorBackground = LocalTheme.current.background,
                                    width = 3.dp,
                                    shape = RoundedCornerShape(25.dp)
                                )
                        )
                    }
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewArticleLayout() {
    ArticleLayout()
}