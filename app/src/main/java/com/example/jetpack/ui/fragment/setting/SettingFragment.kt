package com.example.jetpack.ui.fragment.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.example.jetpack.R
import com.example.jetpack.configuration.Language
import com.example.jetpack.configuration.Menu
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.ui.component.CoreBottomBar
import com.example.jetpack.ui.fragment.home.component.HomeTopBar
import com.example.jetpack.ui.fragment.setting.component.SettingItem
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.util.AppUtil
import com.example.jetpack.util.NavigationUtil.safeNavigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : CoreFragment() {

    private val viewModel: SettingViewModel by viewModels()

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        SettingLayout(
            languageFromCache = viewModel.chosenLanguage.collectAsState().value,
            onOpenLanguage = {
                val destination = SettingFragmentDirections.toLanguage()
                safeNavigate(destination)
            },
            onOpenPrivatePolicy = {
                AppUtil.openWebsite(context = requireContext(), "https://www.youtube.com/")
            },
            onOpenTermOfService = {
                AppUtil.openWebsite(context = requireContext(), "https://www.google.com/")
            },
            onOpenDisclaimer = {
                val destination = SettingFragmentDirections.toDisclaimer()
                safeNavigate(destination)
            }
        )
    }
}

@Composable
fun SettingLayout(
    languageFromCache: Language,
    onOpenLanguage: () -> Unit = {},
    onOpenDisclaimer: () -> Unit = {},
    onOpenTermOfService: () -> Unit = {},
    onOpenPrivatePolicy: () -> Unit = {},
) {
    CoreLayout(
        topBar = { HomeTopBar(name = stringResource(id = Menu.Setting.nameId)) },
        bottomBar = { CoreBottomBar() },
        backgroundColor = Background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            SettingItem(
                icon = R.drawable.ic_language,
                title = stringResource(id = R.string.language),
                subtitle = stringResource(id = languageFromCache.text),
                onClick = onOpenLanguage
            )

            SettingItem(
                icon = R.drawable.ic_private_policy,
                title = stringResource(id = R.string.private_policy),
                subtitle = null,
                onClick = onOpenPrivatePolicy
            )

            SettingItem(
                icon = R.drawable.ic_term_of_service,
                title = stringResource(id = R.string.term_of_service),
                subtitle = null,
                onClick = onOpenTermOfService
            )

            SettingItem(
                icon = R.drawable.ic_disclaimer,
                title = stringResource(id = R.string.disclaimer),
                subtitle = null,
                onClick = onOpenDisclaimer
            )
        }
    }
}

@Preview
@Composable
fun PreviewSettingLayout() {
    SettingLayout(languageFromCache = Language.English)
}