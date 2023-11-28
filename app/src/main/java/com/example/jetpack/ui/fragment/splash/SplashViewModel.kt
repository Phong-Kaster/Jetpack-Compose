package com.example.jetpack.ui.fragment.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SplashViewModel
constructor(
    private val settingRepository: SettingRepository
) : ViewModel() {
    private val _enableIntro = MutableStateFlow(settingRepository.enableIntro())
    var enableIntro = _enableIntro.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _enableIntro.value = settingRepository.enableIntro()
        }
    }
}