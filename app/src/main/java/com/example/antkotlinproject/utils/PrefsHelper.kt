package com.example.antkotlinproject.utils

import android.content.Context
import android.content.SharedPreferences

class PrefsHelper(private val context: Context) {

    private val PREFS_NAME = "SalesApp"
    private val TOKEN = "TOKEN"
    private val REFRESH_TOKEN = "REFRESH_TOKEN"
    private var IS_STUFF = "IS_STUFF"
    private var FRAGMENT_CODE = "FRAGMENT_CODE"
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

    fun clearUserData() {
        prefs.edit().clear().apply()
    }

    fun saveIsStuff(isStuff: Boolean) {
        prefs.edit().putBoolean(IS_STUFF, isStuff).apply()
    }

    fun getIsStuff(): Boolean {
        return prefs.getBoolean(IS_STUFF, false)
    }

    fun saveFragmentCode(code: Int) {
        prefs.edit().putInt(FRAGMENT_CODE, code).apply()
    }

    fun getFragmentCode(): Int {
        return prefs.getInt(FRAGMENT_CODE, 0)
    }

    companion object {
        lateinit var instance: PrefsHelper
    }
}