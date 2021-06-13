package com.example.antkotlinproject.utils

import android.content.Context
import android.content.SharedPreferences

class PrefsHelper(private val context: Context) {

    private val PREFS_NAME = "SalesApp"
    private val TOKEN = "TOKEN"
    private val REFRESH_TOKEN = "REFRESH_TOKEN"
    private var prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    init {
        instance = this
    }

    fun saveToken(token: String?, refreshToken: String?) {
        prefs.edit().putString(TOKEN, token).apply()
        prefs.edit().putString(REFRESH_TOKEN, refreshToken).apply()
    }

    fun getToken(): String {
        return prefs.getString(TOKEN, "") ?: ""
    }

    fun getRefreshToken(): String {
        return  prefs.getString(REFRESH_TOKEN, "") ?: ""
    }

    companion object {
        lateinit var instance: PrefsHelper
    }
}