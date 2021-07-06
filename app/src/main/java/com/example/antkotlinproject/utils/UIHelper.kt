package com.example.antkotlinproject.utils

import android.app.AlertDialog
import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.example.antkotlinproject.R
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

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
        .setIcon(R.drawable.ic_phone)
        .show()
}

fun String.toLesson() = "$this уроков"
fun String.toAt() = "@$this"
fun String.toSom() = "$this сом"

fun File.toImageRequestBody(name: String): MultipartBody.Part {
    return asRequestBody("image/*".toMediaTypeOrNull()).let {
        MultipartBody.Part.createFormData(
            name,
            this.name,
            it
        )
    }
}

fun File.toVideoRequestBody(name: String): MultipartBody.Part {
    return asRequestBody("video/*".toMediaTypeOrNull()).let {
        MultipartBody.Part.createFormData(
            name,
            this.name,
            it
        )
    }
}

fun showCongratsDialog(context: Context, layoutInflater: LayoutInflater, text: Int) {
    val alert = AlertDialog.Builder(context, R.style.DialogStyle)
    val inflater = layoutInflater.inflate(R.layout.alert_congrats, null)
    alert.setView(inflater)
    val message: TextView = inflater.findViewById(R.id.message)
    message.setText(text)
    val dialog = alert.create()
    dialog.show()
    Handler().postDelayed(Runnable {
        if (dialog.isShowing) {
            dialog.dismiss()
        }
    }, 3000)
}