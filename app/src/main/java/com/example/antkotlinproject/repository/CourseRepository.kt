package com.example.antkotlinproject.repository

import com.example.antkotlinproject.data.model.*
import com.example.antkotlinproject.data.network.api.CourseApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface CourseRepository {
    fun fetchCategory(): Observable<MutableList<CategoryModel>>
    fun fetchSubcategory(): Observable<MutableList<CategoryModel>>
    fun fetchCourses(): Observable<MutableList<CourseModel>>
    fun fetchCourse(id: Int): Observable<CourseModel>
    fun fetchUserCourses(): Observable<MutableList<CourseModel>>
    fun fetchSubcategory(categoryId: Int): Observable<CategoryModel>
    fun fetchSubcategoryCourses(subcategoryId: Int): Observable<CategoryModel>
    fun createCourse(name: String, description: String, categoryId: Int,
                     lessonsCount: Int, price: Double, subcategoryId: Int,
                     image: MultipartBody.Part, video: MultipartBody.Part): Observable<CourseModel>
    fun createAccessCourse(data: CourseAccessModel): Observable<CourseAccessModel>
}

class CourseRepositoryImpl(private val api: CourseApi) : CourseRepository {
    override fun fetchCategory(): Observable<MutableList<CategoryModel>> {
        return api.fetchCategory()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun fetchSubcategory(): Observable<MutableList<CategoryModel>> {
        return api.fetchSubcategory()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun fetchCourses(): Observable<MutableList<CourseModel>> {
        return api.fetchCourses()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun fetchCourse(id: Int): Observable<CourseModel> {
        return api.fetchCourse(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun fetchUserCourses(): Observable<MutableList<CourseModel>> {
        return api.fetchUserCourses()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun fetchSubcategory(categoryId: Int): Observable<CategoryModel> {
        return api.fetchSubcategory(categoryId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun fetchSubcategoryCourses(subcategoryId: Int): Observable<CategoryModel> {
        return api.fetchSubcategoryCourses((subcategoryId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun createCourse(
        name: String, description: String, categoryId: Int, lessonsCount: Int, price: Double,
        subcategoryId: Int,
        image: MultipartBody.Part, video: MultipartBody.Part

    ): Observable<CourseModel> {
        return api.createCourse(name = name, description = description, categoryId = categoryId,
            lessonsCount = lessonsCount, price = price, course_preview_image = image,
            subcategoryId = subcategoryId, course_preview_video = video)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun createAccessCourse(data: CourseAccessModel): Observable<CourseAccessModel> {
        return api.createAccessCourse(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}