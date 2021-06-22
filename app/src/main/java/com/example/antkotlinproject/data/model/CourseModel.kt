package com.example.antkotlinproject.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CourseModel(
    val name: String? = null,
    @SerializedName("lessons_count")
    val lessonsCount: Int? = null,
    val owner: Owner? = null,
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
    val id: Int? = null
) : Serializable

data class Owner(
    val username: String,
    val avatar: String
)

var getCourses = mutableListOf<CourseModel>().apply {
//    add(
//        CourseModel(
//            image = "https://novation-nn.ru/wp-content/uploads/2018/09/mat-na-stsene-i-v-literature-1.jpg",
//            name = "Литературные маты в констекстах", lesson = "5 уроков", teacher = "Лярва Подошвина"
//        )
//    )
//    add(
//        CourseModel(
//            image = "https://sun9-56.userapi.com/impf/c847217/v847217141/1f791c/IRbIcjkdvqQ.jpg?size=807x453&quality=96&sign=2894c00d9f418946982ae25f5b558541&type=album",
//            name = "Искусство быстрых убийств", lesson = "3 урока", teacher = "Кенширо Касуми"
//        )
//    )
//    add(
//        CourseModel(
//            image = "https://minecraftcheats.ru/wp-content/uploads/2018/03/Взлом-админки.jpg",
//            name = "Взлом Майнкрафта", lesson = "1 урок", teacher = "Павлик Умный"
//        )
//    )
}