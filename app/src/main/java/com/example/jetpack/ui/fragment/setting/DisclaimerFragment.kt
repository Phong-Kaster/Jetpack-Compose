package com.example.jetpack.ui.fragment.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.ui.component.CoreTopBar
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.TextColor1
import com.example.jetpack.util.NavigationUtil.safeNavigateUp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DisclaimerFragment : CoreFragment() {
    @Composable
    override fun ComposeView() {
        super.ComposeView()
        DisclaimerLayout(
            onBack = {
                safeNavigateUp()
            }
        )
    }
}

@Composable
fun DisclaimerLayout(
    onBack: () -> Unit = {}
) {
    CoreLayout(
        topBar = {
            CoreTopBar(
                title = stringResource(id = R.string.disclaimer),
                leftIcon = R.drawable.ic_back,
                onClickLeft = onBack
            )
        },
        backgroundColor = Background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(text = stringResource(id = R.string.fake_content), color = TextColor1)
        }
    }
}

@Preview
@Composable
fun PreviewDisclaimerLayout() {
    DisclaimerLayout()
}