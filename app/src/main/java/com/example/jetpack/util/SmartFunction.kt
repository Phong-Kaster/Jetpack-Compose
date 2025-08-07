package com.example.jetpack.util

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import com.example.jetpack.R
import java.io.IOException

/**
 * This utility object, SmartFunction, provides several extension functions
 * for handling common validation and error-handling scenarios. It includes
 * functionality for generating user-friendly error messages from exceptions,
 * validating email addresses, conditional logic expressions, and displaying
 * toast messages in a Context. These methods enhance Kotlin's functional style
 * and simplify developers' code when dealing with everyday tasks in Android development.
 * 
 * @version 1.0
 * @since 2023-10-20
 * @author Phong-Kaster
 */
object SmartFunction {

    /**
     * Extension function for Throwable to provide user-friendly error messages.
     * Returns a string message based on the specific type of Throwable.
     *
     * Usage:
     * catch (e: Exception) {
     *     showToast(e.getUserFriendlyMessage())
     * }
     *
     * @return A user-friendly error message.
     */
    fun Throwable.getUserFriendlyMessage(): String = when (this) {
        is IOException -> "Please check your internet connection."
        is NullPointerException -> "Null Pointer Exception"
        is ArrayIndexOutOfBoundsException -> "Array Index Bound Exception"
        is ClassNotFoundException -> "Class Not Found Exception"
        else -> "Something went wrong. Try again later."
    }

    /**
     * Extension function to validate an email address.
     * Utilizes the Android Patterns utility to check if the string is a valid email address format.
     *
     * Usage:
     * val isValid = emailString.isEmailValid()
     *
     * @return True if the email format is valid, otherwise false.
     * @throws [IllegalArgumentException] if the provided string is not an email format.
     */
    fun String.isEmailValid(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()

    
    /**
     * If the given condition is valid then returns the Valid input otherwise null.
     *
     * Usage:
     * val result = enableMicrophone.then(true) -> if enableMicrophone is true, return true, otherwise return null
     * val value = isValidInput.then("It's OK") -> if isValidInput is true, return "It's OK", otherwise return null
     *
     * @return The value if the condition is true, otherwise null.
     * @param value The value to be returned if the Boolean is true.
     */
    fun <T> Boolean.then(value: T): T? = if (this) value else null

    
    /**
     * Shows a toast with a string message.
     *
     * Usage:
     * context.showToast("Custom message here")
     *
     * @param message The message to be displayed in the toast. Defaults to "Hello" if not specified.
     */
    fun Context.showToast(message: String = "Hello") = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()


    /**
     * Shows a toast with a message from the resources.
     *
     * Usage:
     * context.showToast(R.string.your_message_id)
     *
     * @param message The resource ID of the string to be displayed in the toast. Defaults to the app name if not specified.
     */
    fun Context.showToast(message: Int = R.string.app_name) = Toast.makeText(this, this.getString(message), Toast.LENGTH_SHORT).show()

    /**
     * Extension function for nullable Int to determine if it is null or zero.
     *
     * This function checks if the integer value is either null or equals zero.
     *
     * Usage:
     * val isAgeZero = age.isNullOrZero()
     *
     * @return True if the integer is null or zero, otherwise false.
     */
    fun Int?.isNullOrZero(): Boolean = this == null || this == 0

    
    /**
     * Extension function for nullable String to provide a default representation if blank or null.
     *
     * This function checks if the string is null or blank, and if so, returns a dash ("—").
     * If the string is not null and not blank, it returns the original string.
     *
     * Usage:
     * val displayName = username.orDash()
     *
     * @return The original string if it's not null or blank, otherwise a dash ("—").
     */
    fun String?.orDash(): String = if (this.isNullOrBlank()) "—" else this
}