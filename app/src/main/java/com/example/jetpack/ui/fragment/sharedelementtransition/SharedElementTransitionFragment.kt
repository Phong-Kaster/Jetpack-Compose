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
    var showDetails by remember { mutableStateOf(false) }

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

        }
    )
}

@Preview
@Composable
private fun PreviewSharedElementTransition() {
    SharedElementTransitionLayout()
}