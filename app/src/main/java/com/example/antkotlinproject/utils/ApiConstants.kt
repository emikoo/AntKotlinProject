package com.example.antkotlinproject.utils

object ApiConstants {
    const val BASE_URL = "http://bobowka.pythonanywhere.com"
    const val REGISTER_URL = "/user/signup/"
    const val LOGIN_TOKEN_URL = "/user/signin/"
    const val REFRESH_TOKEN_URL = "/user/api/token/refresh/"
    const val GET_CATEGORY = "/course/category/"
    const val GET_COURSES = "/course/course/"
    const val GET_COURSE = "/course/course/{id}"
    const val GET_USER_PROFILE = "/user/user/{id}"
}