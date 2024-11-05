package com.example.jetpack.ui.fragment.kotlinflow

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack.util.FlowUtil
import com.example.jetpack.util.TuplesUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
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
 *
 * [Kotlin combine more than 5 flows](https://medium.com/@duaaawan/kotlin-combine-more-than-5-flows-354112c2883e)
 */
@HiltViewModel
class KotlinFlowViewModel
@Inject
constructor() : ViewModel() {
    private val TAG = this.javaClass.simpleName


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
            val sumOfReduce = flowOf(
                1,
                2,
                3,
                4,
                5
            ).reduce { accumulator, value -> accumulator + value } // Result: 15
            Log.d(TAG, "reduce - sumOfReduce = $sumOfReduce")
        }
    }


    private fun fold() {
        viewModelScope.launch(Dispatchers.IO) {
            val sumOfFold = builderFlowOf.fold(
                initial = 10,
                operation = { accumulator, value -> accumulator + value }) // Result: 10 + 1+2+3+4+5
            Log.d(TAG, "fold - sumOfFold = $sumOfFold")
        }
    }

    private fun combine() {
        viewModelScope.launch(Dispatchers.IO) {
            val flow1 = flowOf(1, 2, 3, 4)
            val flow2 = flowOf(1, 2, 3, 0)
            flow1.combine(
                flow = flow2,
                transform = { a, b -> Pair(a, b) }
            ).collect { Log.d(TAG, "combine = $it") }
        }
    }

    private fun combine3Flows() {
        viewModelScope.launch(Dispatchers.IO) {
            val flow1 = flowOf(1, 2, 3, 4)
            val flow2 = flowOf(1, 2, 3, 0)
            val flow3 = flowOf(2, 4, 6, 8)
            val flow4 = flowOf(2, 4, 6, 8)
            combine(
                flow = flow1,
                flow2 = flow2,
                flow3 = flow3,
                flow4 = flow4,
                transform = { a, b, c, d -> Triple(a, b, c) }
            ).collectLatest {
                Log.d(TAG, "combine3Flows = $it")
            }
        }
    }

    fun combine4Flows() {
        Log.d(TAG, "combine4Flows ------------------------- ")
        viewModelScope.launch(Dispatchers.IO) {
            val flow = flowOf(1, 2, 3, 4)
            val flow2 = flowOf(1, 2, 3, 0)
            val flow3 = flowOf(2, 4, 6, 8)
            val flow4 = flowOf(2, 4, 6, 8)

            combine(
                flow = flow,
                flow2 = flow2,
                flow3 = flow3,
                flow4 = flow4,
                transform = { flow, flow2, flow3, flow4 -> TuplesUtil.Fourth(flow, flow2, flow3, flow4) }
            ).collectLatest { (flow1Status, flow2Status, flow3Status, flow4Status) ->
                Log.d(TAG, "combine4Flows - flow1 = ${flow1Status}, flow2 = $flow2Status, flow3 = $flow3Status, flow4 = $flow4Status")
            }
        }
    }

    fun combine6Flows() {
        Log.d(TAG, "combine6Flows ------------------------- ")
        viewModelScope.launch(Dispatchers.IO) {
            val flow = flowOf(1, 2, 3, 4)
            val flow2 = flowOf(1, 2, 3, 0)
            val flow3 = flowOf(2, 4, 6, 8)
            val flow4 = flowOf(2, 4, 6, 8)
            val flow5 = flowOf(3, 5, 7, 9)
            val flow6 = flowOf(0, 2, 4, 6)

            FlowUtil.combine(
                flow = flow,
                flow2 = flow2,
                flow3 = flow3,
                flow4 = flow4,
                flow5 = flow5,
                flow6 = flow6,
                transform = { flowStream, flow2Stream, flow3Stream, flow4Stream, flow5Stream, flow6Stream -> TuplesUtil.Sixth(flowStream, flow2Stream, flow3Stream, flow4Stream, flow5Stream, flow6Stream) }
            ).collectLatest { (flow1Status, flow2Status, flow3Status, flow4Status, flow5Status, flow6Status) ->
                Log.d(TAG, "combine6Flows = ${flow1Status},${flow2Status},${flow3Status}, ${flow4Status}, ${flow5Status}, $flow6Status")
            }
        }
    }
}