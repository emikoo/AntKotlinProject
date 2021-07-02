package com.example.antkotlinproject.repository

import com.example.antkotlinproject.data.model.CategoryModel
import com.example.antkotlinproject.data.model.CourseModel
import com.example.antkotlinproject.data.model.SubcategoryModel
import com.example.antkotlinproject.data.model.User
import com.example.antkotlinproject.data.network.api.CourseApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface CourseRepository {
    fun fetchCategory(): Observable<MutableList<CategoryModel>>
    fun fetchCourses(): Observable<MutableList<CourseModel>>
    fun fetchCourse(id: Int): Observable<CourseModel>
    fun fetchSubcategory(categoryId: Int): Observable<CategoryModel>
    fun fetchSubcategoryCourses(subcategoryId: Int): Observable<SubcategoryModel>
    fun createCourse(name: String, description: String, categoryId: Int,
                     lessonsCount: Int, price: Double, image: MultipartBody.Part): Observable<CourseModel>
}

class CourseRepositoryImpl(private val api: CourseApi) : CourseRepository {
    override fun fetchCategory(): Observable<MutableList<CategoryModel>> {
        return api.fetchCategory()
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

    override fun fetchSubcategory(categoryId: Int): Observable<CategoryModel> {
        return api.fetchSubcategory(categoryId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun fetchSubcategoryCourses(subcategoryId: Int): Observable<SubcategoryModel> {
        return api.fetchSubcategoryCourses((subcategoryId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun createCourse(
        name: String, description: String, categoryId: Int, lessonsCount: Int, price: Double,
        image: MultipartBody.Part
    ): Observable<CourseModel> {
        return api.createCourse(name, description, categoryId, lessonsCount,
            price, image)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}