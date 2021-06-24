package com.example.antkotlinproject.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    var id: Int? = null,
    var phone: Int? = null,
    val avatar: String? = null,
    var username: String? = null,
    @SerializedName("first_name")
    var firstName: String? = null,
    @SerializedName("last_name")
    var lastName: String? = null,
    var email: String? = null,
    var password1: String? = null,
    var password2: String? = null,
    @SerializedName("is_stuff")
    var isStuff: Boolean
) : Serializable