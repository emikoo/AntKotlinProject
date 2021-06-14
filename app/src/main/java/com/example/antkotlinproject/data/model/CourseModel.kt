package com.example.antkotlinproject.data.model

data class CourseModel(
    val image: String,
    val name: String,
    val lesson: String,
    val teacher: String
)

fun getCourses(): MutableList<CourseModel> {
    return mutableListOf<CourseModel>().apply {
        add(CourseModel("https://novation-nn.ru/wp-content/uploads/2018/09/mat-na-stsene-i-v-literature-1.jpg",
            "Литературные маты в констекстах", "5 уроков", "Лярва Подошвина"))
        add(CourseModel(
            "https://sun9-56.userapi.com/impf/c847217/v847217141/1f791c/IRbIcjkdvqQ.jpg?size=807x453&quality=96&sign=2894c00d9f418946982ae25f5b558541&type=album",
            "Искусство быстрых убийств", "3 урока", "Кенширо Касуми"))
        add(CourseModel("https://minecraftcheats.ru/wp-content/uploads/2018/03/Взлом-админки.jpg",
        "Взлом Майнкрафта", "1 урок", "Павлик Умный"))
    }
}