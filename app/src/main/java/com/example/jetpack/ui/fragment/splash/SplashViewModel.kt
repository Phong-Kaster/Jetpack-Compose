package com.example.jetpack.ui.fragment.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack.data.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel
@Inject
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

    fun setEnableIntro(boolean: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            settingRepository.setEnableIntro(boolean)
        }
    }

}