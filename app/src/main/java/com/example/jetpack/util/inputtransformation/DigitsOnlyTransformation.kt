package com.example.jetpack.util.inputtransformation

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.ui.text.input.KeyboardType
import androidx.core.text.isDigitsOnly

/**
 * this InputTransformation object only accepts
 * digits & set keyboard options with type number by default
 */
object DigitsOnlyTransformation : InputTransformation {
    override val keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    override fun TextFieldBuffer.transformInput() {
        if (!this.asCharSequence().isDigitsOnly()) {
            this.revertAllChanges()
        }
    }

    /*override fun transformInput(
        originalValue: TextFieldCharSequence,
        valueWithChanges: TextFieldBuffer
    ) {
        if (!valueWithChanges.asCharSequence().isDigitsOnly()) {
            valueWithChanges.revertAllChanges()
        }
    }*/
}