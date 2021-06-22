package com.example.antkotlinproject.repository

import androidx.lifecycle.MutableLiveData
import com.example.antkotlinproject.data.model.CategoryModel
import com.example.antkotlinproject.data.model.CourseModel
import com.example.antkotlinproject.data.network.api.CourseApi
import com.example.antkotlinproject.data.network.client.ResponseResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface SearchRepository {
    fun fetchCategory(): MutableLiveData<ResponseResult<MutableList<CategoryModel>>>
    fun fetchCourses(): MutableLiveData<ResponseResult<MutableList<CourseModel>>>
    fun fetchCourse(id: Int): MutableLiveData<ResponseResult<CourseModel>>
}

class SearchRepositoryImpl(private val api: CourseApi) : SearchRepository {
    override fun fetchCategory(): MutableLiveData<ResponseResult<MutableList<CategoryModel>>> {
        val data: MutableLiveData<ResponseResult<MutableList<CategoryModel>>> = MutableLiveData(ResponseResult.loading())
        api.fetchCategory().enqueue(object : Callback<MutableList<CategoryModel>> {

            override fun onFailure(call: Call<MutableList<CategoryModel>>, t: Throwable) {
                data.value = ResponseResult.error(t.message)
            }

            override fun onResponse(call: Call<MutableList<CategoryModel>>, response: Response<MutableList<CategoryModel>>) {
                data.value =
                    if (response.isSuccessful) ResponseResult.success(response.body())
                    else ResponseResult.error(response.message())
            }
        })
        return data
    }

    override fun fetchCourses(): MutableLiveData<ResponseResult<MutableList<CourseModel>>> {
        val data: MutableLiveData<ResponseResult<MutableList<CourseModel>>> = MutableLiveData(ResponseResult.loading())
        api.fetchCourses().enqueue(object : Callback<MutableList<CourseModel>> {

            override fun onFailure(call: Call<MutableList<CourseModel>>, t: Throwable) {
                data.value = ResponseResult.error(t.message)
            }

            override fun onResponse(call: Call<MutableList<CourseModel>>, response: Response<MutableList<CourseModel>>) {
                data.value =
                    if (response.isSuccessful) ResponseResult.success(response.body())
                    else ResponseResult.error(response.message())
            }
        })
        return data
    }

    override fun fetchCourse(id: Int): MutableLiveData<ResponseResult<CourseModel>> {
        val data: MutableLiveData<ResponseResult<CourseModel>> = MutableLiveData(ResponseResult.loading())
        api.fetchCourse(id).enqueue(object : Callback<CourseModel> {
            override fun onFailure(call: Call<CourseModel>, t: Throwable) {
                data.value = ResponseResult.error(t.message)
            }

            override fun onResponse(call: Call<CourseModel>, response: Response<CourseModel>) {
                data.value =
                    if (response.isSuccessful) ResponseResult.success(response.body())
                    else ResponseResult.error(response.message())
            }
        })
        return data
    }
}