package com.example.jetpack.ui.fragment.tutorial

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.jetpack.core.base.LocalTheme
import com.example.jetpack.ui.component.CoreBottomBar
import com.example.jetpack.ui.fragment.home.component.HomeTopBar
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.ui.fragment.tutorial.component.LocalTutorial
import com.example.jetpack.ui.fragment.tutorial.component.TutorialNaziBadge
import com.example.jetpack.ui.fragment.tutorial.component.TutorialNaziEagle
import com.example.jetpack.ui.fragment.tutorial.component.TutorialStep1
import com.example.jetpack.ui.fragment.tutorial.component.TutorialStep2
import com.example.jetpack.ui.fragment.tutorial.component.TutorialStep3
import com.example.jetpack.util.DateUtil
import com.example.jetpack.util.DateUtil.formatWithPattern
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

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
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Background)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = LocalTheme.current.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = customizedTextStyle(
                            fontSize = 16,
                            fontWeight = 600,
                            color = Color.White
                        ),
                    )


                    Text(
                        text = Date().formatWithPattern(pattern = DateUtil.PATTERN_EEE_MMM_dd_YYYY),
                        style = customizedTextStyle(
                            fontSize = 11,
                            fontWeight = 600,
                            color = Color.White
                        ),
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.ic_nazi_eagle),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .onGloballyPositioned {
                            tutorial.naziEagleRect = it.boundsInRoot()
                        }
                )
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    BasicText(
                        text = "Phong Kaster",
                        style = customizedTextStyle(
                            fontSize = 18,
                            fontWeight = 700,
                            color = LocalTheme.current.primary
                        ),
                        modifier = Modifier
                            .wrapContentWidth()
                    )

                    Image(
                        painter = painterResource(R.drawable.ic_bundeswehr),
                        contentDescription = stringResource(R.string.icon),
                        modifier = Modifier
                            .size(24.dp)
                            .onGloballyPositioned {
                                tutorial.naziBadgeRect = it.boundsInRoot()
                            },
                    )
                }

                Text(
                    text = stringResource(R.string.fake_content),
                    style = customizedTextStyle(
                        fontSize = 14,
                        fontWeight = 400,
                        color = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )


    if (tutorial.enable) {
        AnimatedContent(
            targetState = tutorial.currentTutorial,
            label = "Tutorial"
        ) { currentTutorial ->
//            when (currentTutorial) {
//                0 -> TutorialStep1()
//                1 -> TutorialStep2()
//                2 -> TutorialStep3()
//            }
            when (currentTutorial) {
                0 -> TutorialNaziBadge()
                1 -> TutorialNaziEagle()

            }
        }
    }
}

@Preview
@Composable
fun PreviewTutorial() {
    TutorialLayout()
}