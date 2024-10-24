package com.example.jetpack.ui.fragment.language.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.configuration.Language
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.OppositeBackground
import com.example.jetpack.ui.theme.OppositePrimaryColor
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.h16
import com.example.jetpack.util.ViewUtil


@Composable
fun LanguageList(
    list: List<Language>,
    chosenLanguage: Language,
    onClick: (Language) -> Unit = {},
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues()
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(18.dp),
        contentPadding = contentPadding
    ) {
        items(list, key = { language -> language.code }) { language ->
            LanguageItem(
                language = language,
                selected = language == chosenLanguage,
                onClick = { onClick(language) },
            )
        }
    }
}

@Composable
fun LanguageItem(
    language: Language,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(shape = RoundedCornerShape(15.dp))
            .background(color = if (selected) LocalTheme.current.primary else LocalTheme.current.background)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painterResource(language.drawable),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            contentScale = ContentScale.Fit,
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = stringResource(id = language.text),
            style = h16,
            color = if (selected) Color.Black else Color.White
        )
    }
}

@Preview
@Composable
fun PreviewLanguageItem() {
    ViewUtil.PreviewContent {
        LanguageItem(language = Language.German, selected = true)
        Spacer(modifier = Modifier.height(16.dp))
        LanguageItem(language = Language.German, selected = false)
    }
}