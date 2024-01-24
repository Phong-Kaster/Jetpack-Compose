package com.example.jetpack.ui.fragment.motionlayout

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ExperimentalMotionApi
import com.example.jetpack.R
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.ui.component.CoreTopBar2
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.util.NavigationUtil.safeNavigateUp
import dagger.hilt.android.AndroidEntryPoint

/**
 * Document: https://medium.com/mindful-engineering/after-going-through-this-blog-youll-achieve-this-kind-of-polished-animation-using-motionlayout-6b76ec41c6ab
 */
@AndroidEntryPoint
class MotionLayoutFragment : CoreFragment() {

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        MotionContent(
            onBack = { safeNavigateUp() },
        )
    }
}

@OptIn(ExperimentalMotionApi::class)
@Composable
fun MotionContent(
    onBack: ()->Unit = {}
) {
    CoreLayout(
        backgroundColor = Background,
        modifier = Modifier,
        topBar = {
            CoreTopBar2(
                onClick = onBack,
                title = stringResource(id = R.string.motion_layout)
            )
        },
    ) {

    }
}

@Preview
@Composable
fun PreviewMotionContent() {
    MotionContent()
}