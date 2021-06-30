package com.example.antkotlinproject.data.network.api

import com.example.antkotlinproject.data.model.User
import com.example.antkotlinproject.utils.ApiConstants
import com.example.antkotlinproject.utils.ApiConstants.GET_TEACHER_PROFILE
import com.example.antkotlinproject.utils.ApiConstants.GET_USER_PROFILE
import io.reactivex.Maybe
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProfileApi {
    @GET(GET_USER_PROFILE)
    fun fetchUserProfile(): Observable<User>

    @GET(GET_TEACHER_PROFILE)
    fun fetchTeacherProfile(@Path("id") id: Int): Observable<User>

    @POST(GET_USER_PROFILE)
    fun editUserProfile(@Body data: User): Observable<User>
}
