package com.example.antkotlinproject.data.network.api

import com.example.antkotlinproject.data.model.TokenModel
import com.example.antkotlinproject.data.model.User
import com.example.antkotlinproject.utils.ApiConstants.GET_USER_PROFILE
import com.example.antkotlinproject.utils.ApiConstants.LOGIN_TOKEN_URL
import com.example.antkotlinproject.utils.ApiConstants.REFRESH_TOKEN_URL
import com.example.antkotlinproject.utils.ApiConstants.REGISTER_URL
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthApi {
    @POST(REGISTER_URL)
    fun regUser(@Body data: User): Call<User>

    @POST(LOGIN_TOKEN_URL)
    fun login(@Body data: Map<String, String>): Call<TokenModel>

    @POST(REFRESH_TOKEN_URL)
    fun refreshToken(@Body data: TokenModel): Call<TokenModel>
}