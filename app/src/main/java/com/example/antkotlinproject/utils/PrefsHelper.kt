package com.example.antkotlinproject.utils

import android.content.Context
import android.content.SharedPreferences

class PrefsHelper(private val context: Context) {

    private val PREFS_NAME = "SalesApp"
    private val TOKEN = "TOKEN"
    private val REFRESH_TOKEN = "REFRESH_TOKEN"
    private val USERNAME = "USERNAME"
    private val PASSWORD = "PASSWORD"
    private val USER_ID = "USER_ID"
    private var prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    init {
        instance = this
    }

    fun saveRefreshToken(refreshToken: String?) {
        prefs.edit().putString(REFRESH_TOKEN, refreshToken).apply()
    }

    fun saveAccessToken(token: String?) {
        prefs.edit().putString(TOKEN, token).apply()
    }

    fun getToken(): String {
        return prefs.getString(TOKEN, "") ?: ""
    }

    fun getRefreshToken(): String {
        return prefs.getString(REFRESH_TOKEN, "") ?: ""
    }

    fun saveUserId(id: Int) {
        prefs.edit().putInt(USER_ID, id).apply()
    }

    fun getUserId(): Int {
        return prefs.getInt(USER_ID, 0) ?: 0
    }

    fun clearUserData() {
        prefs.edit().clear().apply()
    }

    companion object {
        lateinit var instance: PrefsHelper
    }
}