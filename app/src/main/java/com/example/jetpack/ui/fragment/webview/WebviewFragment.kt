package com.example.jetpack.ui.fragment.webview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.jetpack.R
import com.example.jetpack.core.base.CoreFragment
import com.example.jetpack.core.base.CoreLayout
import com.example.jetpack.ui.component.CoreTopBar2
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.OppositePrimaryColor
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.NavigationUtil.safeNavigateUp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebviewFragment : CoreFragment() {

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        WebviewLayout(
            onBack = { safeNavigateUp() },
            isInternetConnected = isInternetConnected()
        )
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebviewLayout(
    onBack: () -> Unit = {},
    isInternetConnected: Boolean,
) {
    val url = "https://www.google.com/"
    var canGoBack by remember { mutableStateOf(false) }
    var webView: WebView? = null

    BackHandler(
        enabled = canGoBack,
        onBack = { webView?.goBack() }
    )

    CoreLayout(
        backgroundColor = Background,
        floatingActionButton = {
            if (isInternetConnected) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = stringResource(id = R.string.icon),
                    tint = OppositePrimaryColor,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(10.dp))
                        .clickable { webView?.goBack() }
                        .size(50.dp)
                        .background(color = PrimaryColor, shape = RoundedCornerShape(10.dp))
                )
            }
        },
        topBar = {
            CoreTopBar2(
                title = stringResource(id = R.string.webview),
                titleArrangement = Arrangement.Start,
                onLeftClick = onBack,
            )
        },
        content = {
            if (isInternetConnected) {
                AndroidView(
                    factory = { context: Context ->
                        WebView(context).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                            webViewClient = object : WebViewClient() {
                                override fun onPageStarted(
                                    view: WebView,
                                    url: String?,
                                    favicon: Bitmap?
                                ) {
                                    canGoBack = view.canGoBack()
                                }
                            }
                            settings.javaScriptEnabled = true
                            loadUrl(url)
                            webView = this
                        }
                    },
                    update = { webView = it }
                )
            } else {
                Column(verticalArrangement = Arrangement.Center) {
                    Text(
                        text = stringResource(R.string.no_internet_connection),
                        color = PrimaryColor,
                        style = customizedTextStyle(
                            fontSize = 14,
                            fontWeight = 500,
                            lineHeight = 18
                        )
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun PreviewWebviewLayoutHaveConnection() {
    WebviewLayout(
        isInternetConnected = false
    )
}

@Preview
@Composable
private fun PreviewWebviewLayoutNoConnection() {
    WebviewLayout(
        isInternetConnected = true
    )
}