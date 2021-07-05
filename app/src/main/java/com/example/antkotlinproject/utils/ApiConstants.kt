package com.example.antkotlinproject.utils

object ApiConstants {
    const val BASE_URL = "http://bobowka.pythonanywhere.com"
    const val REGISTER_URL = "/user/signup/"
    const val LOGIN_TOKEN_URL = "/user/signin/"
    const val REFRESH_TOKEN_URL = "/user/api/token/refresh/"
    const val GET_CATEGORIES = "/course/category/"
    const val GET_COURSES = "/course/course/"
    const val GET_USER_COURSES = "/user/usercourses"
    const val GET_COURSE = "/course/course/{id}"
    const val GET_USER_PROFILE = "/user/user/"
    const val GET_TEACHER_PROFILE = "/user/user/{id}"
    const val GET_SUBCATEGORY = "/course/category_subcategory/{categoryId}"
    const val GET_SUBCATEGORY_COURSES = "/course/subcategory_courses/{id}"
    const val CREATE_COURSE = "/course/course/create"
    const val CREATE_COURSE_ACCESS = "/course/courseaccess/create"
}