package com.example.jetpack.ui.fragment.sharedelementtransition

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetpack.R
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.ui.component.CoreTopBar
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.util.NavigationUtil.safeNavigateUp
import dagger.hilt.android.AndroidEntryPoint

/**
 * Shared Element Transition - https://developer.android.com/develop/ui/compose/animation/shared-elements
 */
@AndroidEntryPoint
class SharedElementTransitionFragment : CoreFragment() {

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        SharedElementTransitionLayout(
            onBack = { safeNavigateUp() }
        )
    }
}


@Composable
fun SharedElementTransitionLayout(
    onBack: () -> Unit = {}
) {
    var showInfo by remember { mutableStateOf(false) }

    CoreLayout(
        backgroundColor = Background,
        topBar = {
            CoreTopBar(
                title = stringResource(R.string.shared_element_transition),
                leftIcon = R.drawable.ic_back,
                onClickLeft = onBack
            )
        },
        content = {
            /*SharedTransitionLayout {
                AnimatedContent(
                    targetState = showInfo,
                    label = "basicTransition",
                    content =
                    { targetState ->
                        when (targetState) {
                            true -> {
                                InfoContent(
                                    onBack = { showInfo = false },
                                    animatedVisibilityScope = this@AnimatedContent,
                                    sharedTransitionScope = this@SharedTransitionLayout
                                )
                            }

                            false -> {
                                MainContent(
                                    onShowDetails = { showInfo = true },
                                    animatedVisibilityScope = this@AnimatedContent,
                                    sharedTransitionScope = this@SharedTransitionLayout
                                )
                            }
                        }
                    })
            }*/
        }
    )
}

@Preview
@Composable
private fun PreviewSharedElementTransition() {
    SharedElementTransitionLayout()
}