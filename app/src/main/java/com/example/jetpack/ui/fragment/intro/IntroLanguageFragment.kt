package com.example.jetpack.ui.fragment.intro

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.example.jetpack.R
import com.example.jetpack.configuration.Language
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.ui.component.BasicTopBarWithBackButton
import com.example.jetpack.ui.fragment.language.component.LanguageSelector
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.util.NavigationUtil.safeNavigate
import com.example.jetpack.util.ViewUtil
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class IntroLanguageFragment : CoreFragment() {
    // this view model survives only in the lifecycle of Intro Graph. If this graph is killed,
    // this view is also killed
    private val viewModel by hiltNavGraphViewModels<IntroViewModel>(R.id.introGraph)


    @Composable
    override fun ComposeView() {
        var chosenLanguage by remember { mutableStateOf(viewModel.chosenLanguage.value) }
        LanguageLayout(
            chosenLanguage = chosenLanguage,
            onChangeChosenLanguage = { chosenLanguage = it },
            onBack = { requireActivity().finish() },
            onConfirm = {
                viewModel.setLanguage(chosenLanguage)

                val destination = R.id.fromIntroLanguageToIntro
                safeNavigate(destination)
            }
        )
    }
}

@Composable
fun LanguageLayout(
    chosenLanguage: Language = Language.English,
    onChangeChosenLanguage: (Language) -> Unit = {},
    onConfirm: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    CoreLayout(
        topBar = {
            BasicTopBarWithBackButton(
                title = stringResource(R.string.fake_title),
                onBack = onBack,
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
            selectedLanguage = chosenLanguage,
            onSelectLanguage = onChangeChosenLanguage,
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