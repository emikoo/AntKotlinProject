package com.example.antkotlinproject.repository

import androidx.lifecycle.MutableLiveData
import com.example.antkotlinproject.data.model.CategoryModel
import com.example.antkotlinproject.data.network.api.CourseApi
import com.example.antkotlinproject.data.network.client.ResponseResult
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface SearchRepository {
    fun fetchCategory(): MutableLiveData<ResponseResult<MutableList<CategoryModel>>>
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
}