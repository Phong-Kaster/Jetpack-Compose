package com.example.jetpack.ui.fragment.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack.domain.enums.HomeShortcut
import com.example.jetpack.domain.enums.SortOption
import com.example.jetpack.util.CoroutineUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
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

    private val TAG = "HomeViewModel"

    private val _shortcuts = MutableStateFlow<ImmutableList<HomeShortcut>>(persistentListOf())
    val shortcuts = _shortcuts.asStateFlow()

    /*************************************************
     * shortcutsWithLifecycle
     */
    val shortcutsWithLifecycle = MutableStateFlow<ImmutableList<HomeShortcut>>(persistentListOf())

    init {
        sortListOfShortcut()
        sortListOfLifecycleShortcut()
        sampleMergeTwoAsychFunctions()
    }

    /**
     * sortListOfShortcut() and sortListOfLifecycleShortcut()
     * - These are just sorting operations on in-memory lists.
     * They don't perform I/O operations and are CPU-lightweight.
     * Dispatchers.Default would be more appropriate.
     */
    private fun sortListOfShortcut() {
        viewModelScope.launch(Dispatchers.Default) {
            _shortcuts.value = HomeShortcut.entries.sortedBy { it.name }.toImmutableList()
        }
    }

    private fun sortListOfLifecycleShortcut() {
        viewModelScope.launch(Dispatchers.IO) {
            shortcutsWithLifecycle.value =
                HomeShortcut.entries.sortedBy { it.name }.toImmutableList()
        }
    }

    /*************************************************
     * searchWithKeyword - Simple string filtering and sorting.
     * Again, this is a CPU operation, not I/O.
     * Use Dispatchers.Default.
     */
    fun searchWithKeyword(keyword: String) {
        viewModelScope.launch(Dispatchers.Default) {
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
    fun resetShortcuts() {
        viewModelScope.launch(Dispatchers.Default) {
            _shortcuts.value = HomeShortcut.entries.toImmutableList()
        }
    }

    /*************************************************
     * applySortOption  - Similar to above, purely computational. Use Dispatchers.Default.
     */
    fun applySortOption(option: SortOption) {
        viewModelScope.launch(Dispatchers.Default) {
            _shortcuts.value = when (option) {
                SortOption.Original -> HomeShortcut.entries.toImmutableList()
                SortOption.AlphabetAscending -> HomeShortcut.entries.sortedBy { it.name }
                    .toImmutableList()

                SortOption.AlphabetDescending -> HomeShortcut.entries.sortedByDescending { it.name }
                    .toImmutableList()

                SortOption.Inverted -> HomeShortcut.entries.reversed().toImmutableList()
                else -> HomeShortcut.entries.sortedBy { it.name }.toImmutableList()
            }
        }
    }

    /*************************************************
     * this is an example that use merge 2 asynchronous functions
     */
    suspend fun funcA(): Result<String> = runCatching {
        delay(timeMillis = 400)
        return@runCatching "Hello"
    }

    suspend fun funcB(): Result<Int> = runCatching {
        delay(timeMillis = 100)
        throw RuntimeException("Error")
    }

    fun sampleMergeTwoAsychFunctions() {
        viewModelScope.launch(Dispatchers.IO) {
            val message1 = async { funcA() }
            val message2 = async { funcA() }
            val message3 = async { funcA() }
            val message4 = async { funcA() }

            CoroutineUtil.mergeAsync(message1, message2, message3, message4)
                .onSuccess {
                    Log.d(
                        TAG,
                        "Success: ${it.first} ${it.second} ${it.third} ${it.fourth}"
                    )
                }
                .onFailure { Log.d(TAG, "Error: $it") }
        }
    }
}