package com.example.jetpack.ui.fragment.quote

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.example.jetpack.R
import com.example.jetpack.core.base.CoreFragment
import com.example.jetpack.core.base.CoreLayout
import com.example.jetpack.domain.enums.Category
import com.example.jetpack.domain.model.Quote
import com.example.jetpack.ui.component.CoreTopBar
import com.example.jetpack.ui.component.SolidButton
import com.example.jetpack.ui.fragment.quote.component.CategorySelector
import com.example.jetpack.ui.fragment.quote.component.QuoteTextField
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.TextColor1
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.NavigationUtil.safeNavigateUp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate
import java.util.Date


@AndroidEntryPoint
class QuoteFragment : CoreFragment() {

    private val viewModel: QuoteViewModel by viewModels()

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        QuoteLayout(
            records = viewModel.records.collectAsState().value,
            onBack = { safeNavigateUp() },
            onConfirm = { content: String,
                          value: Float,
                          category: Category?,
                          categoryChild: Category? ->

                val quote = Quote(
                    uid = null,
                    content = content,
                    value = value,
                    category = category ?: categoryChild,
                    createAt = Date(),
                    createAtEpochDay = LocalDate.now().toEpochDay()
                )

                viewModel.insertOrUpdate(quote)

            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuoteLayout(
    records: ImmutableList<Quote> = persistentListOf(),
    onBack: () -> Unit = {},
    onConfirm: (String, Float, Category?, Category?) -> Unit = { content: String,
                                                                 value: Float,
                                                                 category: Category?,
                                                                 categoryChild: Category? ->
    }
) {
    var content by remember { mutableStateOf("") }
    var value by remember { mutableFloatStateOf(0F) }
    var chosenCategory by remember { mutableStateOf<Category?>(null) }
    var chosenCategoryChild by remember { mutableStateOf<Category?>(null) }


    /**
     * For Basic Text Field 2
     */
    val digitsOnly = rememberTextFieldState()

    CoreLayout(
        topBar = {
            CoreTopBar(
                title = "Quote",
                leftIcon = R.drawable.ic_back,
                onClickLeft = onBack
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Background)
            ) {
                SolidButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.save),
                    textStyle = customizedTextStyle(fontSize = 14, fontWeight = 700),
                    onClick = {
                        onConfirm(content, value, chosenCategory, chosenCategoryChild)
                    },
                    shape = RoundedCornerShape(10.dp),
                )
            }
        },
        modifier = Modifier,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Background)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .navigationBarsPadding()
                    .imePadding()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Basic Text Field 1",
                    style = customizedTextStyle(
                        fontSize = 18,
                        fontWeight = 700,
                        color = Color.Cyan
                    ),
                    modifier = Modifier.padding(vertical = 10.dp)
                )
                Text(
                    text = "Quote number: ${records.size}",
                    color = TextColor1,
                    style = customizedTextStyle(fontSize = 14)
                )
                Spacer(modifier = Modifier.height(15.dp))
                QuoteTextField(
                    title = "Content",
                    onTextChange = { content = it }
                )
                Spacer(modifier = Modifier.height(15.dp))
                QuoteTextField(
                    title = "Value",
                    onTextChange = {
                        value =
                            try {
                                it.toFloat()
                            } catch (ex: Exception) {
                                0F
                            }
                    }
                )
                Spacer(modifier = Modifier.height(15.dp))
                CategorySelector(
                    modifier = Modifier,
                    onClick = { category: Category?, categoryChild: Category? ->
                        chosenCategory = category
                        chosenCategoryChild = categoryChild
                    }
                )
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun PreviewQuoteLayout() {
    QuoteLayout(
    )
}