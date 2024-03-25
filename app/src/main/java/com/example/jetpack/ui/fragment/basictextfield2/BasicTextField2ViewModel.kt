package com.example.jetpack.ui.fragment.basictextfield2

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.textAsFlow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BasicTextField2ViewModel
@Inject
constructor() : ViewModel() {
    // Hoist text field state to the ViewModel outside of composition
    @OptIn(ExperimentalFoundationApi::class)
    val username = TextFieldState()

    // Observe changes to username text field state
    @OptIn(ExperimentalFoundationApi::class, FlowPreview::class, ExperimentalCoroutinesApi::class)
    val isUsernameValid: StateFlow<Boolean> = username
        .textAsFlow()
        .debounce(500)
        .mapLatest { it.contains("a") }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

}