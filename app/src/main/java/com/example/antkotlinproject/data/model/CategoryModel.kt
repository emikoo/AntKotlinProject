package com.example.antkotlinproject.data.model

import com.google.gson.annotations.SerializedName

data class CategoryModel(
    val id: Int,
    val name: String,
    @SerializedName("category_subcategory")
    val subCategories: MutableList<CategoryModel>,
    @SerializedName("course_subcategory")
    val courses: MutableList<CourseModel>? = null
)