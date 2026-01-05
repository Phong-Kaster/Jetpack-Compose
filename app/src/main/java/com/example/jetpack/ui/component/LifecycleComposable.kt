package com.example.jetpack.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun LifecycleComposable(
    onDestroy: () -> Unit = {},
    onInitialized: () -> Unit = {},
    onCreated: () -> Unit = {},
    onStarted: () -> Unit = {},
    onResumed: () -> Unit = {},
) {

    // Life cycle owner
    val lifeCycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifeCycleOwner.lifecycle.currentStateFlow.collectAsState()


    LaunchedEffect(
        key1 = lifecycleState,
        block = {
            when (lifecycleState) {
                Lifecycle.State.DESTROYED -> { onDestroy() }
                Lifecycle.State.INITIALIZED -> { onInitialized() }
                Lifecycle.State.CREATED -> { onCreated() }
                Lifecycle.State.STARTED -> { onStarted() }
                Lifecycle.State.RESUMED -> { onResumed() }
            }
        }
    )
}