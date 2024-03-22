package com.example.jetpack.util.inputtransformation


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.insert


@OptIn(ExperimentalFoundationApi::class)
object VerificationCodeTransformation : OutputTransformation {
    override fun TextFieldBuffer.transformOutput() {
    // Pad the text with placeholder chars if too short
        val padCount = 6 - this.length
        repeat(padCount) {
            this.append('·')
        }

        // 123 456
        if (this.length > 3) this.insert(3, " ")
    }
}