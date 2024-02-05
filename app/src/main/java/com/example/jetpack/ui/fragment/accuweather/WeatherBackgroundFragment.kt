package com.example.jetpack.ui.fragment.accuweather

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.example.jetpack.R
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.ui.component.CoreTopBar2
import com.example.jetpack.ui.component.OutlineButton
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.NavigationUtil.safePopBackstack
import dagger.hilt.android.AndroidEntryPoint

var imageLoader: ImageLoader? = null

@AndroidEntryPoint
class WeatherBackgroundFragment : CoreFragment() {

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        WeatherBackgroundLayout(
            onBack = { safePopBackstack() }
        )
    }
}


@Composable
fun WeatherBackgroundLayout(
    onBack: () -> Unit = {}
) {
    val context = LocalContext.current
    val backgroundColor = remember { Animatable(Color(0xFF122432)) }

    var enable by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = enable, block = {
        Log.d("PHONG", "enable: $enable")
        if (enable) {
            backgroundColor.animateTo(Color(0xFF66AFE0), animationSpec = tween(1500))
        } else {
            backgroundColor.animateTo(Color(0xFF122432), animationSpec = tween(1500))
        }
    })

    if (imageLoader == null) {
        imageLoader = ImageLoader.Builder(context).components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }.build()
    }

    CoreLayout(
        backgroundColor = backgroundColor.value,
        topBar = {
            CoreTopBar2(
                onClick = onBack,
                title = stringResource(id = R.string.accu_weather)
            )
        },
        bottomBar = {
            OutlineButton(
                text = "Change to sunny",
                textColor = PrimaryColor,
                textStyle = customizedTextStyle(fontWeight = 700),
                borderStroke = BorderStroke(width = 1.dp, color = PrimaryColor),
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    enable = !enable
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = backgroundColor.value)
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1F),
                    contentDescription = null,
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(context)
                            .data(data = R.drawable.gif_rain)
                            .apply(block = { size(Size.ORIGINAL) })
                            .build(), imageLoader = imageLoader!!
                    )
                )
            }
        }
    )
}


@Preview
@Composable
fun PreviewWeatherBackgroundFragment() {
    WeatherBackgroundLayout()
}