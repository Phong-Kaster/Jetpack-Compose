package com.example.jetpack.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

object NavigationUtil {

    /**
     * Pop everything up to the "destination_a" destination off the back stack
     * before navigating to the "destination_b" destination
     *   navController.navigate("destination_b") {
     *        popUpTo("destination_a") {
     *               inclusive = true
     *                saveState = true
     *            }
     *   }
     */
    fun Fragment.safeNavigate(destination: Int, bundle: Bundle? = null, navOptions: NavOptions? = null) {
        try {
            findNavController().navigate(destination, bundle, navOptions, null)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun Fragment.safeNavigate(destination: NavDirections) {
        try {
            findNavController().navigate(destination)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun Fragment.safeNavigateUp() {
        try {
            findNavController().navigateUp()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    /**
     * pop the current destination off the back stack and navigate to the previous destination
     * @param destination is the screen that we go to
     * @param inclusive is whether we remove the current destination from backstack before go to the destination
     */
    fun Fragment.safePopBackstack(destination: Int, inclusive: Boolean, saveState: Boolean) {
        try {
            findNavController().popBackStack(destination, inclusive = inclusive, saveState = saveState)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun Fragment.safePopBackstack(){
        try {
            findNavController().popBackStack()
        }catch (ex: Exception){
            ex.printStackTrace()
        }
    }
}