package com.example.antkotlinproject.utils

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.antkotlinproject.R
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

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

fun showAlertDialog(context: Context, action: () -> Unit) {
    AlertDialog.Builder(context)
        .setTitle("Звонок")
        .setMessage("Вы действительно хотите позвонить ?")
        .setPositiveButton(
            "Да"
        ) { dialog, _ ->
            action()
            dialog.dismiss()
        }
        .setNegativeButton("Нет", null)
        .setIcon(android.R.drawable.ic_dialog_alert)
        .show()
}

fun String.toLesson() = "$this уроков"
fun String.toAt() = "@$this"

fun File.toImageRequestBody(name: String): MultipartBody.Part {
    return asRequestBody("image/*".toMediaTypeOrNull()).let {
        MultipartBody.Part.createFormData(
            name,
            this.name,
            it
        )
    }
}