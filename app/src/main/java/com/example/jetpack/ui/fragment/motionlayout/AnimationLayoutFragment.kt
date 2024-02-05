package com.example.jetpack.ui.fragment.motionlayout

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.ui.fragment.motionlayout.component.AnimationLayout4
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
        )
    }
}

@Composable
fun AnimationLayout(
    onBack: () -> Unit = {}
) {
    /*CoreLayout(
        topBar = {
            CoreTopBar2(
                onClick = onBack,
                title = stringResource(id = R.string.motion_layout)
            )
        },
        backgroundColor = Background,
        modifier = Modifier,
        content = {

        })*/

    AnimationLayout4()
}

@Preview
@Composable
fun PreviewAnimationLayout() {
    AnimationLayout()
}