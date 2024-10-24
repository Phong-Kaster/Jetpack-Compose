package com.example.jetpack.ui.fragment.language

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.example.jetpack.R
import com.example.jetpack.configuration.Language
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.ui.component.CoreButton
import com.example.jetpack.ui.component.CoreTopBar
import com.example.jetpack.ui.component.SolidButton
import com.example.jetpack.ui.fragment.language.component.LanguageList
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.OppositePrimaryColor
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.body16
import com.example.jetpack.util.NavigationUtil.safeNavigateUp
import com.example.jetpack.util.ViewUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LanguageFragment : CoreFragment() {
    private val viewModel: LanguageViewModel by viewModels()

    @Composable
    override fun ComposeView() {
        var chosenLanguage by remember { mutableStateOf(viewModel.chosenLanguage.value) }
        LanguageLayout(
            chosenLanguage = chosenLanguage,
            onBack = { safeNavigateUp() },
            onChange = { chosenLanguage = it },
            onConfirm = {
                viewModel.setLanguage(language = chosenLanguage)
                safeNavigateUp()
                showToast(chosenLanguage.name)
                requireActivity().recreate()
            }
        )
    }
}

@Composable
fun LanguageLayout(
    chosenLanguage: Language = Language.English,
    onBack: () -> Unit = {},
    onConfirm: () -> Unit = {},
    onChange: (Language) -> Unit = {}
) {
    CoreLayout(
        topBar = {
            CoreTopBar(
                title = stringResource(id = R.string.language),
                leftIcon = R.drawable.ic_back,
                onClickLeft = onBack
            )
        },
        bottomBar = {
            Column(modifier = Modifier
                .fillMaxWidth()
                .background(color = LocalTheme.current.background)
                .padding(16.dp)
            ){
                SolidButton(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = LocalTheme.current.primary,
                    text = stringResource(id = R.string.save),
                    textColor = Color.Black,
                    textStyle = body16.copy(fontWeight = FontWeight(700)),
                    onClick = { onConfirm() },
                    marginHorizontal = 0.dp,
                    marginVertical = 0.dp
                )
            }
        },
        modifier = Modifier.background(color = LocalTheme.current.background)
    ) {
        LanguageList(
            list = Language.entries,
            chosenLanguage = chosenLanguage,
            onClick = { onChange(it) },
            modifier = Modifier
                .fillMaxSize()
                .background(color = LocalTheme.current.background)
                .padding(horizontal = 16.dp),
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