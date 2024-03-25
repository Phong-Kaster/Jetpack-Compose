package com.example.jetpack.ui.fragment.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.body14
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

        /*LaunchedEffect(Unit) {
            while (true) {
                delay(1000)
                val enableIntro = viewModel.enableIntro.value
                val destination = if (enableIntro) {
                    SplashFragmentDirections.fromSplashToIntro()
                } else {
                    SplashFragmentDirections.fromSplashToHome()
                }
                safeNavigate(destination)
            }
        }*/
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

    CoreLayout {
        CenterBox(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Background)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_nazi_eagle),
                contentDescription = null,
                tint = PrimaryColor,
                modifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(40))
            )

            /*LinearProgressIndicator(
                progress = progress,
                trackColor = IconColor,
                color = PrimaryColor,
                modifier = Modifier.align(BiasAlignment(0f, 0.85f))
            )*/
            LottieAnimation(
                composition = composition,
                restartOnPlay = true,
                iterations = LottieConstants.IterateForever,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(80.dp)
                    .align(BiasAlignment(0f, 0.85f))

            )


            Text(
                text = stringResource(R.string.fake_title),
                style = body14,
                modifier = Modifier.align(BiasAlignment(0f, 0.95f))
            )
        }
    }
}

@Preview
@Composable
fun PreviewSplashLayout() {
    ViewUtil.PreviewContent {
        SplashLayout()
    }
}