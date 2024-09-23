package com.example.jetpack.domain.model

import android.util.Log

/**
 * # [Generics in Kotlin](https://medium.com/@ramadan123sayed/kotlin-generics-the-ultimate-guide-with-practical-examples-ca3f5ca557e7)
 * An example about Generic class
 */
class Boxy<X>(val content: X) {

    private val TAG = this.javaClass.simpleName

    fun getGenericContent(): X {
        return content
    }

    fun <X> showGenericList(list: List<X>) {
        list.forEachIndexed { index, element ->
            Log.d(TAG, "showGenericList with $index = $element ")
        }
    }
}