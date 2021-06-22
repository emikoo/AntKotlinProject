package com.example.antkotlinproject.data.model

import com.google.gson.annotations.SerializedName

data class CommentsModel(
    val comment: String? = null,
    val course: MutableList<CourseModel>? = null,
    val owner: String? = null,
    @SerializedName("created_date")
    val createdDate: String? = null
)