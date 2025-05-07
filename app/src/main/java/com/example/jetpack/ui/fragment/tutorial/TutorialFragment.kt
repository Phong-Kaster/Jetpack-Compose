package com.example.jetpack.ui.fragment.tutorial

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.example.jetpack.R
import com.example.jetpack.core.base.CoreFragment
import com.example.jetpack.core.base.CoreLayout
import com.example.jetpack.ui.component.CoreBottomBar
import com.example.jetpack.ui.fragment.home.component.HomeTopBar
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.ui.fragment.tutorial.component.LocalTutorial
import com.example.jetpack.ui.fragment.tutorial.component.TutorialStep1
import com.example.jetpack.ui.fragment.tutorial.component.TutorialStep2
import com.example.jetpack.ui.fragment.tutorial.component.TutorialStep3
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TutorialFragment : CoreFragment() {

    private val viewModel: TutorialViewModel by viewModels()

    @Composable
    override fun compositionLocalProvider(): List<ProvidedValue<*>> {
        return listOf(LocalTutorial provides viewModel.tutorial)
    }

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        TutorialLayout(
            onDoneTutorial = {
                showToast(getString(R.string.fake_title))
            }
        )
    }
}

@Composable
fun TutorialLayout(
    onDoneTutorial: () -> Unit = {}
) {
    val tutorial = LocalTutorial.current
    CoreLayout(
        topBar = { HomeTopBar(name = stringResource(R.string.tutorial)) },
        bottomBar = { CoreBottomBar() },
        content = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Background)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_german),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .onGloballyPositioned {
                            tutorial.photoRect = it.boundsInRoot()
                        }
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(id = R.string.fake_message),
                    color = PrimaryColor,
                    style = customizedTextStyle(),
                    modifier = Modifier.onGloballyPositioned {
                        tutorial.titleRect = it.boundsInRoot()
                    }
                )
            }
        }
    )


    if (tutorial.enable) {
        AnimatedContent(
            targetState = tutorial.currentTutorial,
            label = "Tutorial"
        ) { currentTutorial ->
            when (currentTutorial) {
                0 -> TutorialStep1()
                1 -> TutorialStep2()
                2 -> TutorialStep3()
                /*tutorial.TOTAL_TUTORIALS -> onDoneTutorial()*/
            }
        }
    }
}

@Preview
@Composable
fun PreviewTutorial() {
    TutorialLayout()
}