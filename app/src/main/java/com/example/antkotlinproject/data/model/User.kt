package com.example.antkotlinproject.data.model

import com.google.gson.annotations.SerializedName

data class User(
    var username: String,
    @SerializedName("first_name")
    var firstName: String,
    @SerializedName("last_name")
    var lastName: String,
    var email: String,
    var password1: String,
    var password2: String,
    @SerializedName("is_stuff")
    var isStuff: Boolean = false
)