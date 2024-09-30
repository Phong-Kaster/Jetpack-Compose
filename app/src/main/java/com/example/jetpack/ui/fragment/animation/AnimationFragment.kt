package com.example.jetpack.ui.fragment.animation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.ui.component.CoreTopBar
import com.example.jetpack.ui.fragment.animation.component.InfiniteRepeatableAnimationExample
import com.example.jetpack.ui.fragment.animation.component.KeyframeAnimationExample
import com.example.jetpack.ui.fragment.animation.component.SpringAnimationExample
import com.example.jetpack.ui.fragment.animation.component.TweenAnimationExample
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.util.NavigationUtil.safeNavigateUp
import dagger.hilt.android.AndroidEntryPoint

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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                TweenAnimationExample()
                SpringAnimationExample()
                KeyframeAnimationExample()
                InfiniteRepeatableAnimationExample()
            }
        }
    )
}

@Preview
@Composable
private fun PreviewAnimationLayout() {
    AnimationLayout()
}