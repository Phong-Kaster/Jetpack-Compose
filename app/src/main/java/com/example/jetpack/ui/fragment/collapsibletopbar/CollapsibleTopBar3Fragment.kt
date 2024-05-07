package com.example.jetpack.ui.fragment.collapsibletopbar

import androidx.compose.runtime.Composable
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollapsibleTopBar3Fragment : CoreFragment() {

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        CollapsibleTopBar3Layout()
    }
}

@Composable
fun CollapsibleTopBar3Layout() {
    CoreLayout(
        content = {}
    )
}
