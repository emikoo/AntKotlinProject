package com.example.antkotlinproject.data.model

import com.google.gson.annotations.SerializedName

data class SubcategoryModel(
    val id: Int,
    val name: String,
    @SerializedName("course_subcategory")
    val courses: MutableList<SubcategoryCourseModel>
)