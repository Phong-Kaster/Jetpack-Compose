package com.example.jetpack.ui.fragment.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack.configuration.Language
import com.example.jetpack.configuration.Logo
import com.example.jetpack.data.repository.SettingRepository
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
    private val _chosenLanguage = MutableStateFlow(Language.English)
    var chosenLanguage = _chosenLanguage.asStateFlow()

    private val _chosenLogo = MutableStateFlow(Logo.Origin)
    var chosenLogo = _chosenLogo.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            settingRepository.getLanguageFlow().collectLatest {
                _chosenLanguage.value = it
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            _chosenLogo.value = settingRepository.getLogo()
        }
    }
}