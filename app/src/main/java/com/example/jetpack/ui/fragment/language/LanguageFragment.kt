package com.example.jetpack.ui.fragment.language

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.R
import com.example.jetpack.configuration.Language
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.ui.component.BasicTopBarWithBackButton
import com.example.jetpack.ui.fragment.language.component.LanguageSelector
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.util.ViewUtil
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LanguageFragment : CoreFragment() {

    private var selectedLanguage by mutableStateOf(Language.English)

    @Composable
    override fun ComposeView() {
        LanguageLayout(
            selectedLanguage = selectedLanguage,
            onSelectedLanguageChange = { selectedLanguage = it },
            onConfirm = {}
        )
    }
}

@Composable
fun LanguageLayout(
    selectedLanguage: Language = Language.English,
    onSelectedLanguageChange: (Language) -> Unit = {},
    onConfirm: () -> Unit = {},
) {
    CoreLayout(
        topBar = {
            BasicTopBarWithBackButton(
                title = stringResource(R.string.fake_title),
                rightContent = {
                    IconButton(
                        onClick = onConfirm,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            contentDescription = stringResource(id = R.string.fake_title),
                            tint = PrimaryColor,
                        )
                    }
                }
            )
        },
        backgroundColor = Background,
    ) {
        LanguageSelector(
            languages = Language.getSortedList(),
            selectedLanguage = selectedLanguage,
            onSelectLanguage = onSelectedLanguageChange,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp, horizontal = 16.dp)
        )
    }
}

@Preview
@Composable
fun PreviewLanguageLayout() {
    ViewUtil.PreviewContent {
        LanguageLayout()
    }
}