package com.example.jetpack.ui.fragment.kotlinflow

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.jetpack.R
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.ui.component.CoreTopBar
import com.example.jetpack.ui.theme.Background
import com.example.jetpack.ui.theme.customizedTextStyle
import com.example.jetpack.util.NavigationUtil.safeNavigateUp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * [7 Kotlin Flow Operators that you must know](https://medium.com/@myofficework000/7-kotlin-flow-operators-that-you-must-know-62eb726e3ff4)
 *
 * [Kotlin Flow: Comprehensive Guide with Compose Integration](https://medium.com/@ramadan123sayed/kotlin-flow-comprehensive-guide-with-compose-integration-7c640aece690)
 *
 * [Coroutines Cheat Sheet for Android Developers](https://android-dev-nexus.medium.com/coroutines-cheat-sheet-for-android-developers-6ee2049b268c)
 */
@AndroidEntryPoint
class KotlinFlowFragment : CoreFragment() {

    private val viewModel: KotlinFlowViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        viewModel.builderTypeFlow()
        runCoroutineAndGetResult()
        useJobForControlCoroutine()
    }

    /**
     * If you need a result from a coroutine, async is your friend.
     * async returns a Deferred object, which you can await to get the result.
     *
     * Deferred is a type of coroutine job that holds a result, and you can retrieve this result asynchronously.
     *
     * To get the result, you use await(), which suspends (pauses) the coroutine until the result is available.
     */
    private fun runCoroutineAndGetResult() {
        val deferredResult: Deferred<Int> = lifecycleScope.async {
            Log.d(TAG, "runCoroutineAndGetResult - start delaying 1.5 second ")
            delay(1500L)
            Log.d(TAG, "runCoroutineAndGetResult - finish ")
            1
        }
        lifecycleScope.launch {
            val result = deferredResult.await()
            print(result)
        }
    }

    private fun useJobForControlCoroutine(){
        val job = lifecycleScope.launch {
            delay(1500L)
            Log.d(TAG, "useJobForControlCoroutine - job has done !")
        }

        lifecycleScope.launch {
            delay(500L)
            job.cancel()
            Log.d(TAG, "useJobForControlCoroutine - job has cancelled !")
        }
    }

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        KotlinFlowLayout(
            onBack = { safeNavigateUp() }
        )
    }
}

@Composable
fun KotlinFlowLayout(
    onBack: () -> Unit = {}
) {
    CoreLayout(
        topBar = {
            CoreTopBar(
                title = stringResource(R.string.kotlin_flow),
                leftIcon = R.drawable.ic_back,
                onClickLeft = onBack
            )
        },
        backgroundColor = Background,
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "This fragment includes example about Flow, LifecycleScope & GlobalScope",
                    style = customizedTextStyle(
                        fontSize = 16,
                        fontWeight = 600,
                        color = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}

@Preview
@Composable
private fun PreviewKotlinFlow() {
    KotlinFlowLayout()
}