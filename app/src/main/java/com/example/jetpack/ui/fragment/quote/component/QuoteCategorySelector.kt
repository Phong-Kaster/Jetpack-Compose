package com.example.jetpack.ui.fragment.quote.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.domain.enums.Category
import com.example.jetpack.domain.enums.Category.Companion.getSubcategories
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.ViewUtil

@Composable
fun CategoryItem(
    chosen: Boolean,
    category: Category,
    onClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .padding(horizontal = 10.dp)
            .background(color = Color.Transparent, shape = RoundedCornerShape(10.dp))
            .then(
                if (chosen) {
                    Modifier.border(
                        width = 1.dp,
                        color = PrimaryColor,
                        shape = RoundedCornerShape(10.dp)
                    )
                } else {
                    Modifier.border(
                        width = 1.dp,
                        color = Color.Transparent,
                        shape = RoundedCornerShape(10.dp)
                    )
                }
            )
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Icon(
            painter = painterResource(id = category.icon),
            contentDescription = null,
            modifier = Modifier.size(25.dp),
            tint = PrimaryColor
        )
        Text(
            text = stringResource(id = category.text),
            color = PrimaryColor,
            style = customizedTextStyle()
        )
    }
}


@Composable
fun CategorySelector(
    modifier: Modifier = Modifier,
    onClick: (Category?, Category?) -> Unit = { category: Category?, categoryChildren: Category? -> }
) {
    var expanded by remember { mutableStateOf(false) }
    var category: Category? by remember { mutableStateOf(null) }
    var categoryChild: Category? by remember { mutableStateOf(null) }

    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.fake_title),
            textAlign = TextAlign.End,
            color = PrimaryColor,
            style = customizedTextStyle(fontSize = 16, fontWeight = 700),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
        )

        Spacer(modifier = Modifier.height(15.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            items(items = Category.entries, itemContent = {
                if (it.isParent) {
                    CategoryItem(
                        chosen = category == it,
                        category = it,
                        onClick = {
                            expanded = false
                            category = it
                            categoryChild = null
                            onClick(category, null)
                        })
                }
            })
        }
        Divider(modifier = Modifier.padding(vertical = 16.dp))
        if (category != null && expanded) {
            LazyRow(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(
                    items = category!!.getSubcategories(),
                    itemContent = {
                        CategoryItem(
                            chosen = categoryChild == it,
                            category = it,
                            onClick = {
                                categoryChild = it
                                onClick(null, categoryChild)
                            })
                    }
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewCategoriesLayout() {
    ViewUtil.PreviewContent {
        CategorySelector(modifier = Modifier)
    }
}