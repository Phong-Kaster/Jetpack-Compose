package com.example.jetpack.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.Density
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.jetpack.data.repository.SettingRepository
import com.example.jetpack.ui.theme.DarkCustomizedTheme
import com.example.jetpack.ui.theme.JetpackComposeTheme
import com.example.jetpack.ui.theme.LightCustomizedTheme
import com.example.jetpack.util.AppUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject


val LocalLocale = staticCompositionLocalOf { Locale.getDefault() }
val LocalNavController = staticCompositionLocalOf<NavController?> { null }
val LocalTheme = compositionLocalOf { DarkCustomizedTheme }

@AndroidEntryPoint
open class CoreFragment : Fragment(), CoreBehavior {

    @Inject
    lateinit var settingRepository: SettingRepository
    protected open val TAG: String = this.javaClass.simpleName

    /** Dark Mode*/
    private var enableDarkMode by mutableStateOf(true)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        makeStatusBarTransparent()
        setupDarkMode()
        return ComposeView(requireActivity()).apply {
            setViewCompositionStrategy(strategy = ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                CompositionLocalProvider(
                    LocalNavController provides findNavController(),
                    LocalLocale provides requireActivity().resources.configuration.locales[0],
                    LocalDensity provides Density(LocalDensity.current.density, 1f),
                    LocalTheme provides if (enableDarkMode) DarkCustomizedTheme else LightCustomizedTheme,
                    *compositionLocalProvider().toTypedArray()
                ) {
                    JetpackComposeTheme {
                        ComposeView()
                    }
                }
            }
        }
    }

    @Composable
    protected open fun compositionLocalProvider(): List<ProvidedValue<*>> {
        return listOf()
    }

    @Composable
    open fun ComposeView() {
    }

    override fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun isInternetConnected(): Boolean {
        return AppUtil.isInternetConnected(context = requireContext())
    }

    override fun hideNavigationBar() {
        AppUtil.hideNavigationBar(window = requireActivity().window)
    }

    override fun trackEvent(name: String) {}

    override fun showLoading() {}
    override fun makeStatusBarTransparent() {
        /*with(requireActivity().window) {
            setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }*/
    }

    private fun setupDarkMode() {
        lifecycleScope.launch {
            settingRepository.enableDarkModeFlow().collect {
                enableDarkMode = it
            }
        }
    }
}