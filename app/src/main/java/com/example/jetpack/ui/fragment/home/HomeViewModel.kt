package com.example.jetpack.ui.fragment.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack.domain.enums.HomeShortcut
import com.example.jetpack.domain.enums.SortOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * MVVM Architecture - https://github.com/akhilesh0707/Rick-and-Morty
 */
@HiltViewModel
class HomeViewModel
@Inject
constructor() : ViewModel() {

    private val _shortcuts = MutableStateFlow<ImmutableList<HomeShortcut>>(persistentListOf())
    val shortcuts = _shortcuts.asStateFlow()

    /*************************************************
     * shortcutsWithLifecycle
     */
    val shortcutsWithLifecycle = MutableStateFlow<ImmutableList<HomeShortcut>>(persistentListOf())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _shortcuts.value = HomeShortcut.entries.sortedBy { it.name }.toImmutableList()
        }

        viewModelScope.launch(Dispatchers.IO) {
            shortcutsWithLifecycle.value = HomeShortcut.entries.sortedBy { it.name }.toImmutableList()
        }
    }

    /*************************************************
     * searchWithKeyword
     */
    fun searchWithKeyword(keyword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = HomeShortcut.entries.filter { homeShortcut: HomeShortcut ->
                homeShortcut.name.lowercase().contains(keyword)
            }

            _shortcuts.value = list.toImmutableList()
            shortcutsWithLifecycle.value = list.toImmutableList()
        }
    }

    /*************************************************
     * resetShortcuts
     */
    fun resetShortcuts(){
        viewModelScope.launch(Dispatchers.IO){
            _shortcuts.value = HomeShortcut.entries.toImmutableList()
        }
    }

    /*************************************************
     * applySortOption
     */
    fun applySortOption(option: SortOption){
        viewModelScope.launch(Dispatchers.IO){
            _shortcuts.value = when(option){
                SortOption.Original -> HomeShortcut.entries.toImmutableList()
                SortOption.AlphabetAscending -> HomeShortcut.entries.sortedBy { it.name }.toImmutableList()
                SortOption.AlphabetDescending -> HomeShortcut.entries.sortedByDescending { it.name }.toImmutableList()
                SortOption.Inverted -> HomeShortcut.entries.reversed().toImmutableList()
                else -> HomeShortcut.entries.sortedBy { it.name }.toImmutableList()
            }
        }
    }
}