package com.example.jetpack.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.jetpack.ui.theme.JetpackComposeTheme
import com.example.jetpack.util.NavigationUtil
import com.example.jetpack.util.NavigationUtil.safeNavigate
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale


val LocalLocale = staticCompositionLocalOf { Locale.getDefault() }
val LocalNavController = staticCompositionLocalOf<NavController?> { null }

@AndroidEntryPoint
open class CoreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireActivity()).apply {
            setContent {
                CompositionLocalProvider(
                    LocalNavController provides findNavController(),
                    LocalLocale provides requireActivity().resources.configuration.locales[0],
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
    open fun ComposeView() {}
}