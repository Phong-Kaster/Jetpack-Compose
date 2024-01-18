package com.example.jetpack.ui.fragment.setting

import android.content.ComponentName
import android.content.pm.PackageManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.example.jetpack.R
import com.example.jetpack.configuration.Logo
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.ui.component.CoreTopBar
import com.example.jetpack.ui.component.SolidButton
import com.example.jetpack.ui.fragment.setting.component.SettingIconList
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.OppositePrimaryColor
import com.example.jetpack.ui.theme.PrimaryColor
import com.example.jetpack.ui.theme.body16
import com.example.jetpack.util.NavigationUtil.safeNavigateUp
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingIconFragment : CoreFragment() {

    private val viewModel: SettingViewModel by viewModels()

    private fun updateApplicationLogo(newLogo: Logo) {
        val manager: PackageManager = requireContext().packageManager

        Logo.entries.forEach {
            val state =
                if (it == newLogo) PackageManager.COMPONENT_ENABLED_STATE_ENABLED else PackageManager.COMPONENT_ENABLED_STATE_DISABLED
            manager.setComponentEnabledSetting(
                ComponentName(requireActivity(), it.alias),
                state,
                PackageManager.DONT_KILL_APP
            )
        }
    }




    @Composable
    override fun ComposeView() {
        SettingIconLayout(
            logoFromCache = viewModel.chosenLogo.collectAsState().value,
            onBack = { safeNavigateUp() },
            onConfirm = {
                updateApplicationLogo(newLogo = it)
            }
        )
    }
}

@Composable
fun SettingIconLayout(
    logoFromCache: Logo,
    onBack: () -> Unit = {},
    onConfirm: (Logo) -> Unit = {}
) {
    var chosenLogo by remember { mutableStateOf(logoFromCache) }

    CoreLayout(
        topBar = {
            CoreTopBar(
                title = stringResource(id = R.string.disclaimer),
                leftIcon = R.drawable.ic_back,
                onClickLeft = onBack
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Background)
                    .padding(16.dp)
            ) {
                SolidButton(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = PrimaryColor,
                    text = stringResource(id = R.string.save),
                    textColor = OppositePrimaryColor,
                    textStyle = body16.copy(fontWeight = FontWeight(700)),
                    onClick = { onConfirm(chosenLogo) },
                    marginHorizontal = 0.dp,
                    marginVertical = 0.dp
                )
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Background)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            )
            {
                SettingIconList(
                    list = Logo.entries,
                    chosenLogo = chosenLogo,
                    onClick = { chosenLogo = it }
                )
            }
        }
    )
}

@Preview
@Composable
fun PreviewSettingIconLayout() {
    SettingIconLayout(
        logoFromCache = Logo.Origin,
        onConfirm = {},
        onBack = {}
    )
}