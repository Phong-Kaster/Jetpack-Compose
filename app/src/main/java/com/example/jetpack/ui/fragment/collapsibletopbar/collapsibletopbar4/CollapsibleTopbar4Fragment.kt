package com.example.jetpack.ui.fragment.collapsibletopbar.collapsibletopbar4

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.domain.enums.HomeShortcut
import com.example.jetpack.ui.component.CoreTopBarWithHomeShortcut
import com.example.jetpack.ui.fragment.collapsibletopbar.collapsibletopbar4.component.ExpandedTopbar
import com.example.jetpack.ui.theme.customizedTextStyle
import dagger.hilt.android.AndroidEntryPoint

/**
 * # [Collapsing Toolbar in Jetpack Compose LazyColumn - Three Approaches](https://proandroiddev.com/collapsing-toolbar-in-jetpack-compose-lazycolumn-3-approaches-702684d61843)
 *
 * In this article, there are three different approaches to achieve the same effect in a Jetpack Compose LazyColumn.
 *
 * 1. Using Scaffold
 *
 * 2. Using Box
 *
 * 3. Using Scaffold with Material 3’s LargeTopAppBar
 */
@AndroidEntryPoint
class CollapsibleTopbar4Fragment : CoreFragment() {

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        CollapsibleTopbar4Layout()
    }
}

@Composable
fun CollapsibleTopbar4Layout(
    onBack: () -> Unit = {},
) {
    val state = rememberLazyListState()

    var collapsibleTopbarHeight by remember { mutableIntStateOf(0) }
    var expandedTopbarHeight by remember { mutableIntStateOf(0) }

    val isCollapsed by remember {
        derivedStateOf {
            state.firstVisibleItemIndex > 0
        }
    }

    CoreLayout(
        topBar = {
            AnimatedVisibility(visible = isCollapsed,
                content = {
                    CoreTopBarWithHomeShortcut(
                        iconLeft = R.drawable.ic_back,
                        homeShortcut = HomeShortcut.CollapsibleTopbar4,
                        onLeftClick = onBack,
                        iconRight = R.drawable.ic_share,
                        modifier = Modifier
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        LocalTheme.current.primary,
                                        LocalTheme.current.secondary,
                                        LocalTheme.current.secondary,
                                    )
                                )
                            )
                    )
                }
            )
        },
        backgroundColor = LocalTheme.current.background,
        content = {
            LazyColumn(
                state = state,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item(key = "ExpandedTopbar") {
                    ExpandedTopbar(
                        modifier = Modifier
                            .fillMaxWidth()
                   ,
                    )
                }

                item(key = "content1") {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.about), style = customizedTextStyle(
                                fontSize = 16,
                                fontWeight = 700,
                                color = LocalTheme.current.textColor
                            ), modifier = Modifier.fillMaxWidth()
                        )

                        Text(
                            text = stringResource(R.string.fake_content),
                            style = customizedTextStyle(
                                fontSize = 14,
                                fontWeight = 400,
                                color = LocalTheme.current.textColor
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                item(key = "content2") {
                    repeat(times = 10, action = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp, horizontal = 16.dp)
                        ) {
                            Text(
                                text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
                                style = customizedTextStyle(
                                    fontSize = 14,
                                    fontWeight = 400,
                                    color = LocalTheme.current.textColor
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    })
                }
            }
        },
    )
}

@Preview
@Composable
private fun PreviewCollapsibleTopbar4() {
    CollapsibleTopbar4Layout()
}