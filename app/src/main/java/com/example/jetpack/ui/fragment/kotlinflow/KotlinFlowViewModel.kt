package com.example.jetpack.ui.fragment.kotlinflow

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [7 Kotlin Flow Operators that you must know](https://medium.com/@myofficework000/7-kotlin-flow-operators-that-you-must-know-62eb726e3ff4)
 *
 * [Kotlin Flow: Comprehensive Guide with Compose Integration](https://medium.com/@ramadan123sayed/kotlin-flow-comprehensive-guide-with-compose-integration-7c640aece690)
 */
@HiltViewModel
class KotlinFlowViewModel
@Inject
constructor() : ViewModel() {
    private val TAG = this.javaClass.simpleName

    init {
        reduce()
    }

    /**
     * ----------------------------------- Flow Builder Types ---------------------------------------*/

    /**
     * The flow builder is the primary way to create a flow.
     * It allows you to emit values asynchronously using the emit() function.
     */
    private val builderFlow: Flow<Int> = flow {
        for (i in 1..5) {
            delay(1000) // Simulate delay
            emit(i)     // Emit values
        }
    }

    fun builderTypeFlow() {
        Log.d(TAG, "collectNumberFlow: ")
        viewModelScope.launch(Dispatchers.IO) {
            builderFlow.collectLatest {
                Log.d(TAG, "numberFlow - collectLatest = $it")
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            builderFlow.first {
                Log.d(TAG, "numberFlow - first = $it")
                return@first true
            }
        }

    }


    /**
     * The flowOf builder creates a flow from a fixed set of values.
     */
    private val builderFlowOf = flowOf(1, 2, 3, 4, 5)
    private fun builderTypeFlowOf() {
        viewModelScope.launch(Dispatchers.IO) {
            builderFlowOf.collectLatest {
                Log.d(TAG, "flowOfValues - collectLatest = $it")
            }
        }
    }


    /**
     * The asFlow extension allows you to convert collections or sequences into flows
     */
    private val builderAsFlow = listOf(1, 2, 3, 4, 5).asFlow()
    private fun builderTypeAsFlow() {
        viewModelScope.launch(Dispatchers.IO) {
            builderAsFlow.collectLatest {
                Log.d(TAG, "listFlow - collectLatest = $it")
            }
        }
    }


    /**
     * ----------------------------------- Flow Operators ---------------------------------------*/

    /**
     * It collects the values from the flow and allows you to process them.
     */
    private fun collect() {
        viewModelScope.launch(Dispatchers.IO) {
            builderFlowOf.collect { println(it) } // Output: 1, 2, 3
        }
    }

    /**
     * Collects only the first value from the flow and then cancels further emissions.
     */
    private fun first() {
        viewModelScope.launch(Dispatchers.IO) {
            builderFlowOf.first() // Result: 1
        }
    }

    /**
     * Collects all emitted values into a list.
     */
    private fun toList() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = builderFlowOf.toList() // Result: [1, 2, 3]
            Log.d(TAG, "toList = $list")
        }
    }

    private fun reduce() {
        viewModelScope.launch(Dispatchers.IO) {
           val sumOfReduce = builderFlowOf.reduce { accumulator, value -> accumulator + value } // Result: 15
            Log.d(TAG, "reduce - sumOfReduce = $sumOfReduce")
        }
    }


    private fun fold() {
        viewModelScope.launch(Dispatchers.IO){
            val sumOfFold = builderFlowOf.fold(initial = 10, operation = { accumulator, value -> accumulator + value } ) // Result: 10 + 1+2+3+4+5
            Log.d(TAG, "fold - sumOfFold = $sumOfFold")
        }
    }
}