package com.example.jetpack.ui.fragment.collapsibletopbar.collapsibletopbar

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.jetpack.R
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.ui.fragment.collapsibletopbar.collapsibletopbar.component.MealInfo
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.OppositePrimaryColor
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.NavigationUtil.safePopBackstack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollapsibleTopBarFragment : CoreFragment() {
    @Composable
    override fun ComposeView() {
        super.ComposeView()
        CollapsibleTopBarLayout(
            onBack = { safePopBackstack() },
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CollapsibleTopBarLayout(
    onBack: () -> Unit = {}
) {
    val listState = rememberLazyListState()

    val density = LocalDensity.current
    val statusBarTopPx = WindowInsets.statusBars.getTop(density)

    var topBarHeight by remember { mutableIntStateOf(0) }
    val stickyOffset by remember(statusBarTopPx) {
        derivedStateOf {
            val offset =
                listState.layoutInfo.visibleItemsInfo.firstOrNull { it.contentType == "name" }?.offset

            if (offset == null || offset > topBarHeight) {
                0
            } else {
                topBarHeight - offset
            }
        }
    }

    val isCollapsed by remember { derivedStateOf { stickyOffset != 0 } }
    var targetValue by remember { mutableFloatStateOf(0f) }
    val alpha by animateFloatAsState(
        targetValue = targetValue,
        animationSpec = tween(500, easing = FastOutSlowInEasing),
        label = "alphaAnimation"
    )

    LaunchedEffect(isCollapsed) { targetValue = if (isCollapsed) 1f else 0f }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Background)

    ) {
        // CONTENT
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
        ) {
            item(key = "overallAndPhoto") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .paint(
                            painter = painterResource(id = R.drawable.img_recipe_default),
                            contentScale = ContentScale.FillBounds
                        )
                        .height(250.dp)
                        .statusBarsPadding()
                ) {
                    MealInfo(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 10.dp)
                            .clip(shape = RoundedCornerShape(15.dp))
                            .background(color = Color.Black.copy(alpha = 0.5F))
                            .padding(horizontal = 0.dp, vertical = 16.dp)
                            .align(Alignment.BottomCenter)
                    )
                }
            }

            stickyHeader(
                key = "name",
                contentType = "name"
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset {
                            IntOffset(0, stickyOffset)
                        }
                        .zIndex(1f)
                        .background(color = PrimaryColor)
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 16.dp,
                            bottom = 16.dp
                        )
                        .statusBarsPadding()
                ) {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = customizedTextStyle(
                            fontSize = 24,
                            fontWeight = 600,
                            color = OppositePrimaryColor
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }

            item(key = "instruction") {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 16.dp)
                ) {
                    repeat(10) {
                        Text(
                            text = stringResource(id = R.string.fake_content),
                            style = customizedTextStyle(
                                fontSize = 14,
                                fontWeight = 500,
                                color = LocalTheme.current.textColor
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp)
                        )
                    }
                }
            }
        }


        // BUTTON BACK
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    drawRect(
                        color = PrimaryColor,
                        alpha = alpha
                    )
                }
                .padding(start = 16.dp, end = 16.dp, top = 30.dp, bottom = 0.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
                tint = PrimaryColor,
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .background(color = Background)
                    .padding(5.dp)
                    .size(15.dp)
                    .clickable { onBack() }
                    .onSizeChanged { topBarHeight = it.height }
            )


            /*Text(
                text = stringResource(id = R.string.app_name),
                style = customizedTextStyle(
                    fontSize = 24,
                    fontWeight = 600,
                    color = OppositePrimaryColor
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .alpha(alpha)
            )*/
        }
    }
}

@Preview
@Composable
private fun CollapsibleTopBar() {
    CollapsibleTopBarLayout()
}