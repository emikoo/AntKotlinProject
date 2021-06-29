package com.example.antkotlinproject.data.network.api

import com.example.antkotlinproject.data.model.User
import com.example.antkotlinproject.utils.ApiConstants
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileApi {
    @GET(ApiConstants.GET_USER_PROFILE)
    fun fetchUserProfile(): Observable<User>
}
