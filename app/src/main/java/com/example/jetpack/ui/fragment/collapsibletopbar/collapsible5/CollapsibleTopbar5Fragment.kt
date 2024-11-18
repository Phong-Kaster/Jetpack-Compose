package com.example.jetpack.ui.fragment.collapsibletopbar.collapsible5

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.ui.fragment.collapsibletopbar.collapsible5.component.CollapsedTopbar5
import com.example.jetpack.ui.fragment.collapsibletopbar.collapsible5.component.ExpandedTopbar5
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.NavigationUtil.safeNavigateUp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollapsibleTopbar5Fragment : CoreFragment() {


    @Composable
    override fun ComposeView() {
        super.ComposeView()
        CollapsibleTopbar5Layout(
            onBack = { safeNavigateUp() },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsibleTopbar5Layout(
    onBack: () -> Unit = {},
) {
    val state = rememberLazyListState()
    val isCollapsed by remember { derivedStateOf { state.firstVisibleItemIndex > 0 } }

    val scrollBehavior =
        TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    CoreLayout(
        backgroundColor = LocalTheme.current.background,
        topBar = {
            CollapsedTopbar5(
                scrollBehavior = scrollBehavior,
                onBack = onBack,
                modifier = Modifier.fillMaxWidth(),
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        content = {
            LazyColumn(
                state = state,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                item(key = "ExpandedTopbar") {
                    ExpandedTopbar5(modifier = Modifier.fillMaxWidth())
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
        }
    )
}

@Preview
@Composable
private fun PreviewCollapsibleTopbar5() {
    CollapsibleTopbar5Layout()
}
