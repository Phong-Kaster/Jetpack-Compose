package com.example.jetpack.ui.fragment.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.jetpack.R
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.Background2
import com.example.jetpack.ui.theme.body14
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.NavigationUtil.safeNavigate
import com.example.jetpack.util.ViewUtil
import com.example.jetpack.util.ViewUtil.CenterBox
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : CoreFragment() {

    private val viewModel: SplashViewModel by viewModels()

    @Composable
    override fun ComposeView() {
        super.ComposeView()

        SplashLayout(
            goHome = {
                val enableIntro = viewModel.enableIntro.value
                val destination = if (enableIntro) {
                    R.id.fromSplashToIntro
                } else {
                    R.id.fromSplashToHome
                }
                safeNavigate(destination)
            }
        )
    }
}

@Composable
fun SplashLayout(
    goHome: () -> Unit = {}
) {
    var progress by remember { mutableFloatStateOf(0F) }
    val scope = rememberCoroutineScope()

    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.lottie_animation_loading)
    )

    LaunchedEffect(Unit) {
        scope.launch {
            for (index in 1..100) {
                if (index == 100) {
                    goHome()
                    break
                }
                progress = (index.toFloat() / 100)
                delay(5)
            }
        }
    }

    CoreLayout(
        backgroundColor = Background2,
        modifier = Modifier,
        bottomBar = {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
//                LottieAnimation(
//                    composition = composition,
//                    restartOnPlay = true,
//                    iterations = LottieConstants.IterateForever,
//                    contentScale = ContentScale.FillBounds,
//                    modifier = Modifier
//                        .size(80.dp)
//
//                )

                LinearProgressIndicator(
                    color = Color(0xFF004BDC),
                    trackColor = Color(0xFF9EFFFF),
                    strokeCap = StrokeCap.Round,
                )


                Text(
                    text = stringResource(R.string.app_name),
                    style = customizedTextStyle(color = Color.White),
                    modifier = Modifier
                )
            }
        },
        content = {
            CenterBox(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_nazi_eagle),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(160.dp)
                        .clip(RoundedCornerShape(40))
                )


            }
        }
    )
}

@Preview
@Composable
fun PreviewSplashLayout() {
    ViewUtil.PreviewContent {
        SplashLayout()
    }
}