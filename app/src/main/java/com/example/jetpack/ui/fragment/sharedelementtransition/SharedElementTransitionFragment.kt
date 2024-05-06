package com.example.jetpack.ui.fragment.sharedelementtransition

import androidx.compose.material3.Text
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
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.util.NavigationUtil.safeNavigateUp
import dagger.hilt.android.AndroidEntryPoint

/**
 * Shared Element Transition - https://developer.android.com/develop/ui/compose/animation/shared-elements
 * the library (androidx.compose.animation:1.0.7-alpha08) in gradle conflicts with constraint layout library, we should try later
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
            Text(
                text = "the library (androidx.compose.animation:1.0.7-alpha08) in gradle conflicts with constraint layout library, we should try later",
                color = PrimaryColor
            )

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