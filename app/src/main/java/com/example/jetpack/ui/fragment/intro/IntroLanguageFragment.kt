package com.example.jetpack.ui.fragment.intro

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.example.jetpack.core.LocalTheme
import com.example.jetpack.ui.component.CoreTopBar
import com.example.jetpack.ui.fragment.language.component.LanguageList
import com.example.jetpack.ui.theme.Background
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
                requireActivity().recreate()
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
            CoreTopBar(
                title = stringResource(R.string.fake_title),
                onClickLeft = onBack,
                rightIcon = R.drawable.ic_forward,
                leftIcon = R.drawable.ic_back,

                onClickRight = onConfirm
            )
        },
        backgroundColor = LocalTheme.current.background,
    ) {
        LanguageList(
            list = Language.getSortedList(),
            chosenLanguage = chosenLanguage,
            onClick = onChangeChosenLanguage,
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