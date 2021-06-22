package com.example.antkotlinproject.data.network.api

import com.example.antkotlinproject.data.model.CategoryModel
import com.example.antkotlinproject.data.model.CourseModel
import com.example.antkotlinproject.utils.ApiConstants.GET_CATEGORY
import com.example.antkotlinproject.utils.ApiConstants.GET_COURSES
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET

interface CourseApi {
    @GET(GET_CATEGORY)
    fun fetchCategory(): Call<MutableList<CategoryModel>>

    @GET(GET_COURSES)
    fun fetchCourses(): Call<MutableList<CourseModel>>

}