package com.example.jetpack.ui.fragment.kotlinflow

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.example.jetpack.R
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.ui.component.CoreTopBar
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.NavigationUtil.safeNavigateUp
import dagger.hilt.android.AndroidEntryPoint

/**
 * [7 Kotlin Flow Operators that you must know](https://medium.com/@myofficework000/7-kotlin-flow-operators-that-you-must-know-62eb726e3ff4)
 *
 * [Kotlin Flow: Comprehensive Guide with Compose Integration](https://medium.com/@ramadan123sayed/kotlin-flow-comprehensive-guide-with-compose-integration-7c640aece690)
 */
@AndroidEntryPoint
class KotlinFlowFragment : CoreFragment() {

    private val viewModel: KotlinFlowViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        viewModel.builderTypeFlow()
    }

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        KotlinFlowLayout(
            onBack = { safeNavigateUp() }
        )
    }
}

@Composable
fun KotlinFlowLayout(
    onBack: () -> Unit = {}
) {
    CoreLayout(
        topBar = {
            CoreTopBar(
                title = stringResource(R.string.kotlin_flow),
                leftIcon = R.drawable.ic_back,
                onClickLeft = onBack
            )
        },
        backgroundColor = Background,
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.kotlin_flow),
                    style = customizedTextStyle(
                        fontSize = 16,
                        fontWeight = 600,
                        color = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}

@Preview
@Composable
private fun PreviewKotlinFlow() {
    KotlinFlowLayout()
}