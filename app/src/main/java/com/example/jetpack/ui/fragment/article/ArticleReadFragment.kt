package com.example.jetpack.ui.fragment.article

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.ui.component.CoreTopBarWithScrollBehavior
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.NavigationUtil.safeNavigateUp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleReadFragment : CoreFragment() {
    @Composable
    override fun ComposeView() {
        super.ComposeView()
        ArticleReadLayout(
            onBack = { safeNavigateUp() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleReadLayout(
    onBack: () -> Unit = {},
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)

    CoreLayout(
        backgroundColor = Background,
        topBar = {
            CoreTopBarWithScrollBehavior(
                scrollBehavior = scrollBehavior,
                backgroundColor = Color.Red,
                navigationIconContent = {
                    IconButton(
                        onClick = onBack,
                        content = {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = stringResource(R.string.fake_message),
                                tint = Color.White
                            )
                        })
                },
                content = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_nazi_symbol),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(36.dp)
                        )
                        Text(
                            text = stringResource(R.string.fake_title),
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.White,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                })
        },
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
            ) {
                item {
                    repeat(20) {
                        Text(
                            text = stringResource(id = R.string.fake_content),
                            style = customizedTextStyle()
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
    ArticleReadLayout()
}