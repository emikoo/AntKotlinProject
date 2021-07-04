package com.example.antkotlinproject.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CourseModel(
    val name: String? = null,
    @SerializedName("lessons_count")
    val lessonsCount: Int? = null,
    val owner: User? = null,
    val description: String? = null,
    @SerializedName("post_comments")
    val postComments: MutableList<CommentsModel>? = null,
    val date: String? = null,
    val price: Double? = null,
    @SerializedName("course_preview_image")
    val coursePreviewImage: String? = null,
    @SerializedName("course_preview_video")
    val coursePreviewVideo: String? = null,
    val category: Int? = null,
    val subcategoryId: Int? = null,
    val id: Int? = null
) : Serializable