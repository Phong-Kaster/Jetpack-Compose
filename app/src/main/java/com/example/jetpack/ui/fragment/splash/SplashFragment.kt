package com.example.jetpack.ui.fragment.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.jetpack.R
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.body14
import com.example.jetpack.util.NavigationUtil.safeNavigate
import com.example.jetpack.util.ViewUtil
import com.example.jetpack.util.ViewUtil.CenterBox
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class SplashFragment : CoreFragment() {

    private val viewModel: SplashViewModel by viewModels()

    @Composable
    override fun ComposeView() {
        super.ComposeView()

        LaunchedEffect(Unit){
            while (true){
                delay(2000)

                val destination = viewModel.enableIntro.value
            }
        }

        SplashLayout()
    }
}

@Composable
fun SplashLayout() {
    CoreLayout {
        CenterBox(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(40))
            )

            CircularProgressIndicator(
                color = PrimaryColor,
                modifier = Modifier
                    .size(32.dp)
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