package com.example.jetpack.ui.fragment.animation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.base.CoreFragment
import com.example.jetpack.core.base.CoreLayout
import com.example.jetpack.ui.component.CoreTopBar
import com.example.jetpack.ui.fragment.animation.component.DrawBehindExample
import com.example.jetpack.ui.fragment.animation.component.DrawWithContentExmaple
import com.example.jetpack.ui.fragment.animation.component.InfiniteRepeatableAnimationExample
import com.example.jetpack.ui.fragment.animation.component.KeyframeAnimationExample
import com.example.jetpack.ui.fragment.animation.component.SpringAnimationExample
import com.example.jetpack.ui.fragment.animation.component.TweenAnimationExample
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.util.NavigationUtil.safeNavigateUp
import dagger.hilt.android.AndroidEntryPoint

/**
 * [Affine transformations in 5 minutes](https://www.youtube.com/watch?v=AheaTd_l5Is)
 */
@AndroidEntryPoint
class AnimationFragment : CoreFragment() {

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        AnimationLayout(
            onBack = { safeNavigateUp() }
        )
    }
}

@Composable
fun AnimationLayout(
    onBack: () -> Unit = {}
) {
    CoreLayout(
        topBar = {
            CoreTopBar(
                title = stringResource(R.string.animation),
                leftIcon = R.drawable.ic_back,
                onClickLeft = onBack
            )
        },
        backgroundColor = Background,
        content = {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item(key = "TweenAnimationExample") { TweenAnimationExample() }
                item(key = "SpringAnimationExample") { SpringAnimationExample() }
                item(key = "KeyframeAnimationExample") { KeyframeAnimationExample() }
                item(key = "InfiniteRepeatableAnimationExample") { InfiniteRepeatableAnimationExample() }
                item(key = "DrawBehindExample") { DrawBehindExample() }
                item(key = "drawWithContent") { DrawWithContentExmaple() }
            }
        }
    )
}

@Preview
@Composable
private fun PreviewAnimationLayout() {
    AnimationLayout()
}