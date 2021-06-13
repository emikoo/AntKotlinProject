package com.example.antkotlinproject.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.antkotlinproject.R
import com.google.android.material.snackbar.Snackbar

fun showActionSnackbar(
    view: View,
    message: String,
    actionTitle: String,
    action: () -> Unit,
    context: Context
) {
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction(actionTitle) {
        action()
    }.setActionTextColor(ContextCompat.getColor(context, R.color.color_dark_green)).show()
}

var toast: Toast? = null
fun showToast(context: Context, message: String) {
    if (toast != null) toast?.cancel()
    toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
    toast?.show()
}


