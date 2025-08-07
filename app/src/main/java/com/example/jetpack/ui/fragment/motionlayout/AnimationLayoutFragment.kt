package com.example.jetpack.ui.fragment.motionlayout

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetpack.R
import com.example.jetpack.core.base.CoreFragment
import com.example.jetpack.core.base.CoreLayout
import com.example.jetpack.ui.component.CoreTopBar2
import com.example.jetpack.ui.fragment.motionlayout.component.AnimationLayout1
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.util.NavigationUtil.safeNavigateUp
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Phong-Kaster
 *
 * @see [Animation using Motion Layout](https://medium.com/mindful-engineering/after-going-through-this-blog-youll-achieve-this-kind-of-polished-animation-using-motionlayout-6b76ec41c6ab)
 * @see [MotionLayout documentation](https://developer.android.com/codelabs/basic-android-kotlin-compose-functions)
 * @see [Animation with MotionLayout](https://github.com/Mindinventory/MarioInMotion?tab=readme-ov-file)
 * @see [Constraint Layout](https://github.com/androidx/constraintlayout/wiki)
 * @see [Compose ConstraintLayout DSL Syntax](https://github.com/androidx/constraintlayout/wiki/Compose-ConstraintLayout-DSL-Syntax)
 */
@AndroidEntryPoint
class AnimationLayoutFragment : CoreFragment() {

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        AnimationLayout(
            onBack = { safeNavigateUp() },
            onClick = {}
        )
    }
}

@Composable
fun AnimationLayout(
    onBack: () -> Unit = {},
    onClick: ()->Unit = {}
) {
    CoreLayout(
        topBar = {
            CoreTopBar2(
                onLeftClick = onBack,
                title = stringResource(id = R.string.motion_layout)
            )
        },
        backgroundColor = Background,
        modifier = Modifier,
        content = {
            AnimationLayout1(
                onClick = onClick
            )
        })

}

@Preview
@Composable
fun PreviewAnimationLayout() {
    AnimationLayout()
}