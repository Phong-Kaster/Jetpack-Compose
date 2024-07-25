package com.example.jetpack.ui.fragment.quote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack.domain.model.Quote
import com.example.jetpack.data.repository.QuoteRepository
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

/**
 * this class use Basic Text Field 2
 *  A TextField of Dreams(Part 1) - https://proandroiddev.com/basictextfield2-a-textfield-of-dreams-1-2-0103fd7cc0ec
 *  A TextField of Dreams(Part 2) - https://proandroiddev.com/basictextfield2-a-textfield-of-dreams-2-2-fdc7fbbf9ffb
 */
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