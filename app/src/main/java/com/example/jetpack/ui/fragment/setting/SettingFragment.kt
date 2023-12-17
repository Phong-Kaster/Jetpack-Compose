package com.example.jetpack.ui.fragment.setting

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import com.example.jetpack.database.enums.Star
import com.example.jetpack.ui.component.CoreBottomBar
import com.example.jetpack.ui.fragment.home.component.HomeTopBar
import com.example.jetpack.ui.fragment.setting.component.RateDialog
import com.example.jetpack.ui.fragment.setting.component.SettingItem
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.util.AppUtil
import com.example.jetpack.util.NavigationUtil.safeNavigate
import com.google.android.play.core.review.ReviewManagerFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : CoreFragment() {

    private val viewModel: SettingViewModel by viewModels()
    private var showRateDialog by mutableStateOf(false)

    private fun rate(star: Star, content: String) {
        if (star == Star.FOUR || star == Star.FIVE) {
            val manager = ReviewManagerFactory.create(requireContext())
            val request = manager.requestReviewFlow()
            request.addOnCompleteListener { task ->
                if (task.isSuccessful) { // open in-app review
                    val reviewInfo = request.result
                    val flow = manager.launchReviewFlow(requireActivity(), reviewInfo)
                    flow.addOnCompleteListener {
                        Toast.makeText(requireContext(), getString(R.string.thanks_for_your_feedback), Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        it.printStackTrace()
                    }
                } else { // open Google Play Store to review
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${requireContext().packageName}")))
                    Toast.makeText(requireContext(), getString(R.string.thanks_for_your_feedback), Toast.LENGTH_SHORT).show()
                }
                AppUtil.logcat(message = "addOnCompleteListener")
            }.addOnCanceledListener {
                AppUtil.logcat(message = "addOnCanceledListener")
            }.addOnFailureListener {
                AppUtil.logcat(message = "addOnFailureListener")
            }.addOnSuccessListener {
                AppUtil.logcat(message = "addOnSuccessListener")
            }
        } else {
            try {
                /*AdsProvider.disableAppResume()*/
                val intent = AppUtil.composeEmail(context = requireContext(), star =  star.value, feedback = content)
                requireContext().startActivity(intent)
            } catch (ex: Exception) {
                AppUtil.logcat(message = "${ex.message}")
                ex.printStackTrace()
            }
        }
    }

    @Composable
    override fun ComposeView() {
        super.ComposeView()

        RateDialog(
            enable = showRateDialog,
            onDismissRequest = { showRateDialog = !showRateDialog },
            onClose = { showRateDialog = false },
            onSubmit = { star: Star, content: String ->
                rate(star, content)
            },
        )

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
            },
            onRate = { showRateDialog = !showRateDialog }
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
    onRate: () -> Unit = {}
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

            SettingItem(
                icon = R.drawable.ic_star_disable,
                title = stringResource(id = R.string.rate),
                subtitle = null,
                onClick = onRate
            )
        }
    }
}

@Preview
@Composable
fun PreviewSettingLayout() {
    SettingLayout(languageFromCache = Language.English)
}