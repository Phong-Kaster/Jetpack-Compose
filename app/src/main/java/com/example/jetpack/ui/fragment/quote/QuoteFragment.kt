package com.example.jetpack.ui.fragment.quote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.data.enums.Category
import com.example.jetpack.data.model.Quote
import com.example.jetpack.ui.component.CoreTopBar
import com.example.jetpack.ui.component.SolidButton
import com.example.jetpack.ui.fragment.quote.component.CategoriesLayout
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.TextColor1
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.NavigationUtil.safeNavigateUp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuoteFragment : CoreFragment() {

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        QuoteLayout(
            onBack = { safeNavigateUp() },
            onConfirm = { content: String, category: Category? ->
                val quote = Quote(uid = null, content = content, category = category)
            }
        )
    }
}

@Composable
fun QuoteLayout(
    onBack: () -> Unit = {},
    onConfirm: (String, Category?) -> Unit = { content: String, category: Category? -> }
) {
    var content by remember { mutableStateOf("") }
    var category by remember { mutableStateOf(null) }

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
                    onClick = {
                        onConfirm(content, category)
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
            ) {
                Text(
                    text = "Quote number: ",
                    color = TextColor1,
                    style = customizedTextStyle(fontSize = 14)
                )
                Spacer(modifier = Modifier.height(15.dp))
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = {
                        Text(
                            text = "Content",
                            style = customizedTextStyle(),
                            color = PrimaryColor
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Background,
                        focusedTextColor = TextColor1,
                        focusedPlaceholderColor = Background,
                        focusedIndicatorColor = PrimaryColor,
                        unfocusedIndicatorColor = PrimaryColor,
                        unfocusedContainerColor = Background
                    )
                )
                Spacer(modifier = Modifier.height(15.dp))
                CategoriesLayout()
            }
        }
    )
}

@Preview
@Composable
fun PreviewQuoteLayout() {
    QuoteLayout()
}