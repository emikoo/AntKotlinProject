package com.example.antkotlinproject.data.model

import com.google.gson.annotations.SerializedName

data class LessonModel(
    val id: Int,
    val name: String,
    @SerializedName("lesson_video_material")
    val video: String,
    val description: String,
    @SerializedName("lesson_materials")
    val materials: MutableList<String>,
    val course: CourseModel
)