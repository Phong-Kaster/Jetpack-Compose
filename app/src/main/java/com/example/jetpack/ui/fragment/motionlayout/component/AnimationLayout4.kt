package com.example.jetpack.ui.fragment.motionlayout.component

import android.util.Log
import androidx.compose.animation.core.FloatExponentialDecaySpec
import androidx.compose.animation.core.animateDecay
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.example.jetpack.R
import com.example.jetpack.ui.fragment.motionlayout.toolbarstate.FixedScrollFlagState
import com.example.jetpack.ui.fragment.motionlayout.toolbarstate.ToolbarState
import com.example.jetpack.ui.fragment.motionlayout.toolbarstate.scrollflag.ExitUntilCollapsedState
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.customizedTextStyle
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

private val MinToolbarHeight = 96.dp
private val MaxToolbarHeight = 176.dp

private val ContentPadding = 8.dp
private val Elevation = 4.dp
private val ButtonSize = 24.dp
private const val Alpha = 0.75f

private val ExpandedPadding = 1.dp
private val CollapsedPadding = 5.dp

private val ExpandedCostaRicaHeight = 20.dp
private val CollapsedCostaRicaHeight = 16.dp

private val ExpandedWildlifeHeight = 32.dp
private val CollapsedWildlifeHeight = 24.dp

private val MapHeight = CollapsedCostaRicaHeight * 2

@Composable
private fun rememberToolbarState(toolbarHeightRange: IntRange): ToolbarState {
    return rememberSaveable(saver = ExitUntilCollapsedState.Saver) {
        ExitUntilCollapsedState(toolbarHeightRange)
    }
}

/**
 * @see [Collapsing Toolbar in Jetpack Compose|‘LazyColumn’ version — Part 1](https://medium.com/kotlin-and-kotlin-for-android/collapsing-toolbar-in-jetpack-compose-lazycolumn-version-f1b0a7924ffe)
 * @see [Collapsing Toolbar in Jetpack Compose|‘LazyColumn’ version — Part 2](https://medium.com/kotlin-and-kotlin-for-android/collapsing-toolbar-in-jetpack-compose-lazycolumn-version-84dff2fdb461)
 */
@Composable
fun AnimationLayout4() {
    val lazyState: LazyListState = rememberLazyListState()

    val toolbarHeightRange: IntRange =
        with(LocalDensity.current) { MinToolbarHeight.roundToPx()..MaxToolbarHeight.roundToPx() }
    val toolbarState: ToolbarState = rememberToolbarState(toolbarHeightRange)

    val scope = rememberCoroutineScope()
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                toolbarState.scrollTopLimitReached =
                    lazyState.firstVisibleItemIndex == 0 || lazyState.firstVisibleItemScrollOffset == 0
                toolbarState.scrollOffset = toolbarState.scrollOffset - available.y
                return Offset.Zero
            }

            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                if (available.y > 0) {
                    scope.launch {
                        animateDecay(
                            initialValue = toolbarState.height + toolbarState.offset,
                            initialVelocity = available.y,
                            animationSpec = FloatExponentialDecaySpec()
                        ) { value, velocity ->
                            toolbarState.scrollTopLimitReached =
                                lazyState.firstVisibleItemIndex == 0 || lazyState.firstVisibleItemScrollOffset == 0
                            toolbarState.scrollOffset =
                                toolbarState.scrollOffset - (value - (toolbarState.height + toolbarState.offset))
                            if (toolbarState.scrollOffset == 0f) scope.coroutineContext.cancelChildren()
                        }
                    }
                }

                return super.onPostFling(consumed, available)
            }
        }
    }

    Box(modifier = Modifier.nestedScroll(connection = nestedScrollConnection)) {
        CollapsingToolbar(
            progress = toolbarState.progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(with(LocalDensity.current) { toolbarState.height.toDp() })
                .graphicsLayer { translationY = toolbarState.offset }
        )

        LazyItems(
            lazyState = lazyState,
            innerPadding = PaddingValues(bottom = if (toolbarState is FixedScrollFlagState) MinToolbarHeight else 0.dp),
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { translationY = toolbarState.height + toolbarState.offset }
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = { scope.coroutineContext.cancelChildren() }
                    )
                },
        )
    }
}

@Composable
fun LazyItems(
    innerPadding: PaddingValues,
    lazyState: LazyListState = rememberLazyListState(),
    modifier: Modifier
) {
    LazyColumn(
        state = lazyState,
        modifier = modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(color = Background)
    ) {
        items(15) { item ->
            Text(
                text = "${stringResource(id = R.string.fake_title)} $item",
                style = MaterialTheme.typography.headlineMedium,
                color = PrimaryColor,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}


/**
 * @param progress: Value between 0f and 1f, where 1f corresponds to its expanded state and 0f corresponds to its collapsed state.
 */
@Composable
fun CollapsingToolbar(
    progress: Float,
    modifier: Modifier = Modifier
) {

    val logoPadding = with(LocalDensity.current) {
        lerp(CollapsedPadding.toPx(), ExpandedPadding.toPx(), progress).toDp()
    }

    val eagleHeight = with(LocalDensity.current) {
        lerp(25.dp.toPx(), 100.dp.toPx(), progress).toDp()
    }

    Surface(
        shadowElevation = 5.dp,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = if (progress == 1F) Background else PrimaryColor)
                .padding(horizontal = ContentPadding)
                .statusBarsPadding()
        ) {
            CollapsingToolbarLayout(progress = progress) {
                Icon(
                    painter =
                        if (progress == 1F) painterResource(id = R.drawable.ic_nazi_eagle)
                        else painterResource(id = R.drawable.ic_nazi_symbol),
                    contentDescription = null,
                    tint = if(progress == 1F) PrimaryColor else Color.Black,
                    modifier = Modifier
                        .padding(logoPadding)
                        .height(eagleHeight)
                        .wrapContentWidth(),
                )

                Text(
                    text = "The Third Reich",
                    style = if (progress == 1F) customizedTextStyle(fontSize = 12, fontWeight = 500)
                            else customizedTextStyle(fontSize = 16, fontWeight = 600),
                    color = if (progress == 1F) PrimaryColor else Color.Black,
                    modifier = Modifier
                        .padding(logoPadding)
                        .wrapContentWidth()
                )
            }
        }
    }
}

@Composable
private fun CollapsingToolbarLayout(
    progress: Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->

        val placeables = measurables.map {
            it.measure(constraints)
        }

        //constraints.maxHeight * 0.5 - eagle.height * 0.6).toInt(),
        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight
        ) {
            // eagle position
            val eagle = placeables[0]
            eagle.placeRelative(
                x = lerp(
                    start = 0,
                    stop = (constraints.maxWidth * 0.5 - eagle.width * 0.5).toInt(),
                    fraction = progress
                ),
                y = lerp(
                    start = 0,
                    stop = (constraints.maxHeight * 0.5 - eagle.height * 0.6).toInt(),
                    fraction = progress
                )
            )

            // the third reich
            val theThirdReich = placeables[1]
            theThirdReich.placeRelative(
                x = lerp(
                    start = eagle.width,
                    stop = (constraints.maxWidth * 0.5 - theThirdReich.width * 0.5).toInt(),
                    fraction = progress
                ),
                y = lerp(
                    start = (eagle.height * 0.15).toInt(),
                    stop = (eagle.height + theThirdReich.height).toInt(),
                    fraction = progress
                )
            )
        }
    }
}

@Preview
@Composable
fun PreviewAnimationLayout4() {
    AnimationLayout4()
}