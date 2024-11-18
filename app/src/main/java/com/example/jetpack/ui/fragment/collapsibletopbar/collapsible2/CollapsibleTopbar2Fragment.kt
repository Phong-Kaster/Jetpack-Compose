package com.example.jetpack.ui.fragment.collapsibletopbar.collapsible2

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.ui.component.CoreTopBarWithScrollBehavior
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.ui.view.ButtonBookmark
import com.example.jetpack.ui.view.ButtonFavorite
import com.example.jetpack.ui.view.ButtonShare
import com.example.jetpack.ui.view.ButtonTextSetting
import com.example.jetpack.util.NavigationUtil.safeNavigateUp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollapsibleTopbar2Fragment : CoreFragment() {
    @Composable
    override fun ComposeView() {
        super.ComposeView()
        CollapsibleTopbar2Layout(
            onBack = { safeNavigateUp() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsibleTopbar2Layout(
    onBack: () -> Unit = {},
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)

    CoreLayout(
        backgroundColor = Background,
        topBar = {
            CoreTopBarWithScrollBehavior(
                scrollBehavior = scrollBehavior,
                backgroundColor = Background,
                scrolledContainerColor = Color(0xFFDE0000),
                navigationIconContent = {
                    IconButton(
                        onClick = onBack,
                        content = {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = stringResource(R.string.icon),
                                tint = LocalTheme.current.textColor
                            )
                        })
                },
                content = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 10.dp,
                            alignment = Alignment.CenterHorizontally
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_nazi_swastika),
                            contentDescription = stringResource(id = R.string.icon),
                            tint = LocalTheme.current.textColor,
                            modifier = Modifier.size(24.dp)
                        )

                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = customizedTextStyle(color = LocalTheme.current.textColor).toSpanStyle()) {
                                    append(text = stringResource(id = R.string.wikipedia))
                                    append(text = "\n")
                                    append(text = stringResource(id = R.string.flag_of_nazi_germany))
                                }
                            },
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.White,
                            modifier = Modifier
                        )

                    }

                })
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Background,
                actions = {
                    ButtonFavorite(onClick = { })
                    ButtonBookmark(isBookmarked = true, onClick = {})
                    ButtonShare(onClick = { })
                    ButtonTextSetting(onClick = { })
                }
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
            ) {
                item(key = "flag") {
                    Image(
                        painter = painterResource(id = R.drawable.ic_nazi_germany_flag),
                        contentDescription = stringResource(id = R.string.icon),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }

                item(key = "content") {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.the_flag_of_nazi_germany),
                            style = customizedTextStyle(fontSize = 18, color = LocalTheme.current.textColor),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Text(
                            text = stringResource(R.string.after_rejecting_many_suggestions),
                            style = customizedTextStyle(fontSize = 18, color = LocalTheme.current.textColor),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Text(
                            text = stringResource(R.string.after_rejecting_many_suggestions),
                            style = customizedTextStyle(fontSize = 18, color = LocalTheme.current.textColor),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Text(
                            text = stringResource(R.string.i_myself_meanwhile_after_innumerable),
                            style = customizedTextStyle(fontSize = 18, color = LocalTheme.current.textColor),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Text(
                            text = stringResource(R.string.after_hitler_was_appointed),
                            style = customizedTextStyle(fontSize = 18, color = LocalTheme.current.textColor),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Text(
                            text = stringResource(R.string.on_15_september_1935_one_year_after_the_death),
                            style = customizedTextStyle(fontSize = 18, color = LocalTheme.current.textColor),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                }
            }
        }
    )
}

@Preview
@Composable
private fun PreviewArticleReadLayout() {
    CollapsibleTopbar2Layout()
}