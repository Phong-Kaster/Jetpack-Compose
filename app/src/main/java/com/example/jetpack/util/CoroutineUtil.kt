package com.example.jetpack.util

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * This class merge 2 or more suspend functions into one result. There are 5 ways to wait for asynchronous functions
 * 1. naiveWay
 * 2. asyncWay
 * 3. asyncAllWay
 * 4. asyncAllWithCancel
 * 5. fixedMergeAsyncWay
 *
 * Read the article below for more information:
 *
 * [Kotlin Coroutines: how to merge asynchronous results in Kotlin?](https://medium.com/@jtlalka/kotlin-coroutines-how-to-merge-asynchronous-results-in-kotlin-674c3079edba)
 */
object CoroutineUtil {

    suspend fun <R1 : Any, R2 : Any> mergeAsync(
        a: Deferred<Result<R1>>,
        b: Deferred<Result<R2>>
    ): Result<Pair<R1, R2>> {
        var aResult: Result<R1>? = null
        var bResult: Result<R2>? = null

        coroutineScope {
            launch { aResult = a.await().onFailure { b.cancel() } }
            launch { bResult = b.await().onFailure { a.cancel() } }
        }
        return runCatching {
            Pair(aResult?.getOrThrow(), bResult?.getOrThrow()).let {
                Pair(requireNotNull(it.first), requireNotNull(it.second))
            }
        }
    }

    suspend fun <R1 : Any, R2 : Any, R3 : Any> mergeAsync(
        a: Deferred<Result<R1>>,
        b: Deferred<Result<R2>>,
        c: Deferred<Result<R3>>
    ): Result<Triple<R1, R2, R3>> {
        var aResult: Result<R1>? = null
        var bResult: Result<R2>? = null
        var cResult: Result<R3>? = null
        val cancelJobs = { listOf(a, b, c).map { it.cancel() } }

        coroutineScope {
            launch { aResult = a.await().onFailure { cancelJobs() } }
            launch { bResult = b.await().onFailure { cancelJobs() } }
            launch { cResult = c.await().onFailure { cancelJobs() } }
        }
        return runCatching {
            Triple(aResult?.getOrThrow(), bResult?.getOrThrow(), cResult?.getOrThrow()).let {
                Triple(requireNotNull(it.first), requireNotNull(it.second), requireNotNull(it.third))
            }
        }
    }

    suspend fun <R1 : Any, R2 : Any, R3 : Any, R4: Any> mergeAsync(
        a: Deferred<Result<R1>>,
        b: Deferred<Result<R2>>,
        c: Deferred<Result<R3>>,
        d: Deferred<Result<R4>>,
    ): Result<TuplesUtil.Fourth<R1, R2, R3, R4>> {
        var aResult: Result<R1>? = null
        var bResult: Result<R2>? = null
        var cResult: Result<R3>? = null
        var dResult: Result<R4>? = null
        val cancelJobs = { listOf(a, b, c, d).map { it.cancel() } }

        coroutineScope {
            launch { aResult = a.await().onFailure { cancelJobs() } }
            launch { bResult = b.await().onFailure { cancelJobs() } }
            launch { cResult = c.await().onFailure { cancelJobs() } }
            launch { dResult = d.await().onFailure { cancelJobs() } }
        }
        return runCatching {
            TuplesUtil.Fourth(aResult?.getOrThrow(), bResult?.getOrThrow(), cResult?.getOrThrow(), dResult?.getOrThrow()).let {
                TuplesUtil.Fourth(requireNotNull(it.first), requireNotNull(it.second), requireNotNull(it.third), requireNotNull(it.fourth))
            }
        }
    }
}