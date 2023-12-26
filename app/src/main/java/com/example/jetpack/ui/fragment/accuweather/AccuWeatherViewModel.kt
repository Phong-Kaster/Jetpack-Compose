package com.example.jetpack.ui.fragment.accuweather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack.network.dto.LocationAuto
import com.example.jetpack.repository.WeatherRepositoryImplement
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccuWeatherViewModel
@Inject
constructor(
    private val weatherRepository: WeatherRepositoryImplement
) : ViewModel() {

    private val _locationAuto = MutableStateFlow<List<LocationAuto>>(listOf())
    val locationAuto = _locationAuto.asStateFlow()


    fun searchAutocomplete(keyword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _locationAuto.value = weatherRepository.searchAutocomplete(keyword = keyword)
            } catch (ex: Exception) {
                ex.printStackTrace()
                _locationAuto.value = emptyList()
            }
        }
    }
}