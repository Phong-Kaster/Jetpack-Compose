package com.example.jetpack.ui.fragment.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel
@Inject
constructor(
    private val settingRepository: SettingRepository
) : ViewModel() {
    private val _chosenLanguage = MutableStateFlow(settingRepository.getLanguage())
    var chosenLanguage = _chosenLanguage.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            settingRepository.getLanguageFlow().collectLatest {
                _chosenLanguage.value = it
            }
        }
    }
}