package com.example.antkotlinproject.data.network.api

import com.example.antkotlinproject.data.model.CategoryModel
import com.example.antkotlinproject.data.model.CourseModel
import com.example.antkotlinproject.data.model.SubcategoryModel
import com.example.antkotlinproject.utils.ApiConstants.GET_CATEGORY
import com.example.antkotlinproject.utils.ApiConstants.GET_COURSE
import com.example.antkotlinproject.utils.ApiConstants.GET_COURSES
import com.example.antkotlinproject.utils.ApiConstants.GET_SUBCATEGORY
import com.example.antkotlinproject.utils.ApiConstants.GET_SUBCATEGORY_COURSES
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface CourseApi {
    @GET(GET_CATEGORY)
    fun fetchCategory(): Observable<MutableList<CategoryModel>>

    @GET(GET_SUBCATEGORY_COURSES)
    fun fetchSubcategoryCourses(@Path("id") id: Int): Observable<SubcategoryModel>

    @GET(GET_COURSES)
    fun fetchCourses(): Observable<MutableList<CourseModel>>

    @GET(GET_COURSE)
    fun fetchCourse(@Path("id") id: Int): Observable<CourseModel>

    @GET(GET_SUBCATEGORY)
    fun fetchSubcategory(@Path("categoryId") categoryId: Int): Observable<CategoryModel>
}