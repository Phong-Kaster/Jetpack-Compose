package com.example.jetpack.ui.fragment.quote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack.data.model.Quote
import com.example.jetpack.repository.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel
@Inject
constructor(
    private val quoteRepository: QuoteRepository
) : ViewModel() {

    private val _records = MutableStateFlow<ImmutableList<Quote>>(persistentListOf())
    val records = _records.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO){
            quoteRepository.findAllFlow().collectLatest {
                _records.value = it.toImmutableList()
            }
        }
    }

    fun insertOrUpdate(quote: Quote) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                quoteRepository.insertOrUpdate(quote)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    fun delete(quote: Quote) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                quoteRepository.delete(quote)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
}