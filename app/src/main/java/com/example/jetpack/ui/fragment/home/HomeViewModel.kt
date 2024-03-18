package com.example.jetpack.ui.fragment.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack.configuration.Menu
import com.example.jetpack.data.enums.HomeShortcut
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor() : ViewModel() {

    private val _shortcuts = MutableStateFlow<ImmutableList<HomeShortcut>>(persistentListOf())
    val shortcuts = _shortcuts.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO){
            _shortcuts.value = HomeShortcut.entries.toImmutableList()
        }
    }

    fun searchWithKeyword(keyword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = HomeShortcut.entries.filter { homeShortcut: HomeShortcut ->
                homeShortcut.name.lowercase().contains(keyword)
            }

            _shortcuts.value = list.toImmutableList()
        }
    }

    fun resetShortcuts(){
        viewModelScope.launch(Dispatchers.IO){
            _shortcuts.value = HomeShortcut.entries.toImmutableList()
        }
    }
}