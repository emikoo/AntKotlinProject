package com.example.antkotlinproject.data.model

import com.google.gson.annotations.SerializedName

data class SubcategoryCourseModel(
    val name: String,
    val owner: Int,
    @SerializedName("course_preview_image")
    val image: String,
    @SerializedName("lessons_count")
    val lessonsCount: Int,
    val price: Double
)