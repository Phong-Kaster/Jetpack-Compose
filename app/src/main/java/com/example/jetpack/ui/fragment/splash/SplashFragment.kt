package com.example.jetpack.ui.fragment.splash

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.example.jetpack.R
import com.example.jetpack.configuration.Constant
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.domain.enums.ApplicationLogo
import com.example.jetpack.ui.theme.Background2
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.NavigationUtil.safeNavigate
import com.example.jetpack.util.ViewUtil
import com.example.jetpack.util.ViewUtil.CenterBox
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 1. lay URI cua video
 * 2. tu URI lay ra thumbnail
 *
 */
@AndroidEntryPoint
class SplashFragment : CoreFragment() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val message= requireActivity().intent.getStringExtra(Constant.MESSAGE)
        Log.d(TAG, "onViewCreated - message = $message")
    }

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
    val applicationLogo: ApplicationLogo by remember { mutableStateOf(ApplicationLogo.generateRandomLogo()) }

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
                Text(
                    text = stringResource(R.string.app_name),
                    style = customizedTextStyle(color = Color.White),
                    modifier = Modifier
                )

                LinearProgressIndicator(
                    color = Color(0xFF004BDC),
                    trackColor = Color(0xFF9EFFFF),
                    strokeCap = StrokeCap.Round,
                )
            }
        },
        content = {
            CenterBox(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(applicationLogo.photo),
                    contentDescription = null,
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