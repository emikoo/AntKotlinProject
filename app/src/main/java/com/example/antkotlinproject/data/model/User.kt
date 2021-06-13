package com.example.antkotlinproject.data.model

import com.google.gson.annotations.SerializedName

data class User(
    var username: String? = null,
    var password: String? = null,
    @SerializedName("is_owner")
    var isOwner: Boolean = true
)