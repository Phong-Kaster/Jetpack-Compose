package com.example.jetpack.ui.fragment.collapsibletopbar.collapsible3

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.ui.fragment.collapsibletopbar.collapsible3.component.PlantImage
import com.example.jetpack.ui.fragment.collapsibletopbar.collapsible3.component.PlantInformation
import com.example.jetpack.ui.fragment.collapsibletopbar.collapsible3.header.CombinableHeader
import com.example.jetpack.ui.fragment.collapsibletopbar.collapsible3.header.ToolbarState
import com.example.jetpack.ui.fragment.collapsibletopbar.collapsible3.state.Dimens
import com.example.jetpack.ui.fragment.collapsibletopbar.collapsible3.state.ScreenScroller
import com.example.jetpack.ui.theme.Background
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollapsibleTopbar3Fragment : CoreFragment() {

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        CollapsibleTopbar3Layout()
    }
}

@Composable
fun CollapsibleTopbar3Layout() {

    val scrollState = rememberScrollState()
    var plantScroller by remember { mutableStateOf(ScreenScroller(scrollState, Float.MIN_VALUE)) }
    val transitionState =
        remember(plantScroller) { plantScroller.toolbarTransitionState }
    val toolbarState = plantScroller.getToolbarState(LocalDensity.current)

    // Transition that fades in/out the header with the image and the Toolbar
    val transition = rememberTransition(transitionState, label = "")
    val toolbarAlpha = transition.animateFloat(
        transitionSpec = { spring(stiffness = Spring.StiffnessLow) },
        label = "toolbarAlpha",
        targetValueByState = { toolbarTransitionState ->
            if (toolbarTransitionState == ToolbarState.HIDDEN) 0f
            else 1f
        })
    val contentAlpha = transition.animateFloat(
        transitionSpec = { spring(stiffness = Spring.StiffnessLow) },
        label = "contentAlpha",
        targetValueByState = { toolbarTransitionState ->
            if (toolbarTransitionState == ToolbarState.HIDDEN) 1f
            else 0f
        })

    val toolbarHeightPx = with(LocalDensity.current) {
        Dimens.PlantDetailAppBarHeight.roundToPx().toFloat()
    }
    val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.value + delta
                toolbarOffsetHeightPx.value =
                    newOffset.coerceIn(-toolbarHeightPx, 0f)
                return Offset.Zero
            }
        }
    }


    CoreLayout(
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(nestedScrollConnection)
            ) {
                Column(
                    modifier = Modifier
                        .background(color = Background)
                        .systemBarsPadding()
                        .verticalScroll(scrollState)
                ) {
                    PlantImage(
                        imageHeight = with(LocalDensity.current) {
                            val candidateHeight =
                                Dimens.PlantDetailAppBarHeight + toolbarOffsetHeightPx.value.toDp()
                            // FIXME: Remove this workaround when https://github.com/bumptech/glide/issues/4952
                            // is released
                            maxOf(candidateHeight, 1.dp)
                        },
                        modifier = Modifier.alpha(contentAlpha.value)
                    )

                    PlantInformation(
                        onNamePosition = {
                            // Comparing to Float.MIN_VALUE as we are just interested on the original
                            // position of name on the screen
                            if (plantScroller.namePosition == Float.MIN_VALUE) {
                                plantScroller = plantScroller.copy(namePosition = it)
                            }
                        },
                        toolbarState = toolbarState,
                        modifier = Modifier
                    )
                }


                CombinableHeader(
                    title = stringResource(id = R.string.flag_of_nazi_germany),
                    toolbarState = toolbarState,
                    toolbarAlpha = { toolbarAlpha.value },
                    contentAlpha = { contentAlpha.value }
                )
            }
        }
    )
}


@Preview
@Composable
private fun PreviewCollapsibleTopbar3() {
    CollapsibleTopbar3Layout()
}
