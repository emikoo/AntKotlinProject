package com.example.antkotlinproject.data.model

data class CategoryModel(
    val title: String
)

fun getCategories(): MutableList<CategoryModel> {
    return mutableListOf<CategoryModel>().apply {
        add(CategoryModel(title = "Школьные предметы"))
        add(CategoryModel(title = "Программирование"))
        add(CategoryModel(title = "Иностранные языки"))
        add(CategoryModel(title = "Кулинария"))
        add(CategoryModel(title = "Наука"))
        add(CategoryModel(title = "Политика"))
        add(CategoryModel(title = "Дизайн"))
        add(CategoryModel(title = "Личностный рост"))
        add(CategoryModel(title = "Фотография и видео"))
        add(CategoryModel(title = "Финансы и бухгалтерский учет"))
    }
}
