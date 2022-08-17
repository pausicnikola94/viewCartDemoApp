package com.example.viewdemoapp.ui.common

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

fun Activity.navigate(dest: Int, navCont: Int, args: Bundle? = null) {
    try { findNavController(navCont).navigate(dest, args) } catch (exception: Exception) {}
}

fun Fragment.navigate(dest: Int, args: Bundle? = null) {
    try { findNavController().navigate(dest, args) } catch (exception: Exception) {}
}

fun Fragment.popBackStack() {
    try { findNavController().popBackStack() } catch (exception: Exception) {}
}

fun Fragment.popBackStack(dest: Int, inclusive: Boolean) {
    try { findNavController().popBackStack(dest, inclusive) } catch (exception: Exception) {}
}
