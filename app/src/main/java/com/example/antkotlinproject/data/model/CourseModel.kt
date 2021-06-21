package com.example.antkotlinproject.data.model

data class CourseModel(
    val image: String,
    val name: String,
    val description: String? = null,
    val rang: String? = null,
    val lesson: String,
    val teacherId: Int? = null,
    val teacher: String
)

var getCourses = mutableListOf<CourseModel>().apply {
    add(
        CourseModel(
            image = "https://novation-nn.ru/wp-content/uploads/2018/09/mat-na-stsene-i-v-literature-1.jpg",
            name = "Литературные маты в констекстах", lesson = "5 уроков", teacher = "Лярва Подошвина"
        )
    )
    add(
        CourseModel(
            image = "https://sun9-56.userapi.com/impf/c847217/v847217141/1f791c/IRbIcjkdvqQ.jpg?size=807x453&quality=96&sign=2894c00d9f418946982ae25f5b558541&type=album",
            name = "Искусство быстрых убийств", lesson = "3 урока", teacher = "Кенширо Касуми"
        )
    )
    add(
        CourseModel(
            image = "https://minecraftcheats.ru/wp-content/uploads/2018/03/Взлом-админки.jpg",
            name = "Взлом Майнкрафта", lesson = "1 урок", teacher = "Павлик Умный"
        )
    )
}