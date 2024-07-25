package com.example.jetpack.ui.fragment.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack.domain.manager.MapManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel
@Inject
constructor(
    val mapManager: MapManager
) : ViewModel() {
    private var _latitude = MutableStateFlow(0.0)
    val latitude: StateFlow<Double> = _latitude.asStateFlow()


    private var _longitude = MutableStateFlow(0.0)
    val longitude: StateFlow<Double> = _longitude.asStateFlow()

    init {
        collectLatestLatitude()
        collectLatestLongitude()
    }

    private fun collectLatestLongitude() {
        viewModelScope.launch(Dispatchers.IO){
            mapManager.longitude.collectLatest {
                _longitude.value = it
            }
        }
    }

    private fun collectLatestLatitude() {
        viewModelScope.launch(Dispatchers.IO){
            mapManager.latitude.collectLatest {
                _latitude.value = it
            }
        }
    }
}