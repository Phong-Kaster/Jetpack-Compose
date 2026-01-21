package com.example.jetpack.ui.fragment.meccacompass

import androidx.compose.runtime.Composable
import com.example.jetpack.core.base.CoreFragment
import com.example.jetpack.core.base.CoreLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeccaCompassFragment : CoreFragment() {
    @Composable
    override fun ComposeView() {
        super.ComposeView()
        MeccaCompass()
    }
}

@Composable
private fun  MeccaCompass(){
    CoreLayout(
        content = {

        }
    )
}