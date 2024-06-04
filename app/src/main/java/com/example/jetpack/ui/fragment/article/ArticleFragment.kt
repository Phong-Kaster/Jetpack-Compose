package com.example.jetpack.ui.fragment.article

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.configuration.Language
import com.example.jetpack.configuration.Menu
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.ui.component.CoreAlertDialog
import com.example.jetpack.ui.component.CoreBottomBar
import com.example.jetpack.ui.component.CoreDialog
import com.example.jetpack.ui.component.CoreExpandableFloatingButton
import com.example.jetpack.ui.component.SquareElement
import com.example.jetpack.ui.fragment.home.component.HomeDialog
import com.example.jetpack.ui.fragment.home.component.HomeTopBar
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.PrimaryColor
import dagger.hilt.android.AndroidEntryPoint

/**
 * Animate borders in Jetpack Compose - https://proandroiddev.com/animate-borders-in-jetpack-compose-ca359deed7d5
 */
@AndroidEntryPoint
class ArticleFragment : CoreFragment() {

    private var showAlertDialog by mutableStateOf(false)
    private var showDialog by mutableStateOf(false)

    @Composable
    override fun ComposeView() {
        super.ComposeView()

        ArticleLayout(
            onOpenAlertDialog = { showAlertDialog = true },
            onOpenDialog = { showDialog = true },
        )

        HomeDialog(
            enable = showDialog,
            onDismissRequest = { showDialog = false },
        )

        CoreAlertDialog(
            enable = showAlertDialog,
            onDismissRequest = { showAlertDialog = false },
        )
    }
}

/**
 * For Columm:
 *     val scrollState = rememberScrollState()
 *     val fabExtended by remember { derivedStateOf { scrollState.value == 0 } }
 * For Lazy Column/ Lazy Grid/ Lazy Row
 *     val state = rememberLazyGridState()
 *     val fabExtended by remember { derivedStateOf { state.firstVisibleItemIndex == 0 } }
 */
@Composable
fun ArticleLayout(
    onOpenAlertDialog: () -> Unit = {},
    onOpenDialog: () -> Unit = {},
) {
    // for lazy grid state
    val state = rememberLazyGridState()
    val extended by remember { derivedStateOf { state.firstVisibleItemIndex > 0 } }

    CoreLayout(
        topBar = { HomeTopBar(name = stringResource(id = Menu.Article.nameId)) },
        bottomBar = { CoreBottomBar() },
        floatingActionButton = {
            CoreExpandableFloatingButton(
                extended = extended,
                modifier = Modifier
            )
        },
        backgroundColor = Background
    ) {
        LazyVerticalGrid(
            state = state,
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 32.dp)
                .fillMaxSize()
        ) {
            item(key = "key1", span = { GridItemSpan(2) }) {
                Text(text = stringResource(id = R.string.fake_content), color = PrimaryColor)
            }

            item {
                val colorBg = Color(0xFF2C3141)
                val colors =
                    listOf(
                        Color(0xFFFF595A),
                        Color(0xFFFFC766),
                        Color(0xFF35A07F),
                        Color(0xFF35A07F),
                        Color(0xFFFFC766),
                        Color(0xFFFF595A)
                    )

                val brush = Brush.linearGradient(colors)

                Canvas(modifier = Modifier.fillMaxWidth().height(200.dp).background(colorBg)) {
                    drawRoundRect(
                        brush = brush,
                        cornerRadius = CornerRadius(x = 20.dp.toPx(), y = 20.dp.toPx()
                        )
                    )

                    drawRoundRect(
                        color = colorBg,
                        topLeft = Offset(1.dp.toPx(), 1.dp.toPx()),
                        size = Size(
                            width = size.width - 2.dp.toPx(),
                            height = size.height - 2.dp.toPx()
                        ),
                        cornerRadius = CornerRadius(
                            x = 19.dp.toPx(),
                            y = 19.dp.toPx()
                        )
                    )
                }
            }

            items(
                items = Language.entries,
                key = { item: Language -> item.name },
                itemContent = {language: Language ->
                    SquareElement(
                        language = language,
                        onClick = {
                            when(language) {
                                Language.English -> onOpenAlertDialog()
                                else -> onOpenDialog()
                            }
                        })
                })
        }
    }
}

@Preview
@Composable
fun PreviewArticleLayout() {
    ArticleLayout()
}