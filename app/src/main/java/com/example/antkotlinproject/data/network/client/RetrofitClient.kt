package com.example.notesapp.data.network.client

import com.example.antkotlinproject.data.model.TokenModel
import com.example.antkotlinproject.data.network.api.AuthApi
import com.example.antkotlinproject.data.network.api.CourseApi
import com.example.antkotlinproject.utils.ApiConstants.BASE_URL
import com.example.antkotlinproject.utils.PrefsHelper
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

fun provideRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .build()

fun provideOkHttpClient(
    httpLoggingInterceptor: HttpLoggingInterceptor,
    tokenAuthenticator: TokenAuthenticator
): OkHttpClient {
    return OkHttpClient()
        .newBuilder()
        .addInterceptor(httpLoggingInterceptor)
        .authenticator(tokenAuthenticator)
        .build()
}

fun provideHttpLoginingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}

fun provideAuthApi(retrofit: Retrofit) = retrofit.create(AuthApi::class.java)
fun provideCourseApi(retrofit: Retrofit) = retrofit.create(CourseApi::class.java)

fun provideTokenAuthenticator(preferences: PrefsHelper)
        = TokenAuthenticator(preferences)

fun provideHeadersInterceptor(preferences: PrefsHelper)
        = HeadersInterceptor(preferences)

class HeadersInterceptor(private val preferences: PrefsHelper) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = preferences.getToken()
        val request = chain.request().newBuilder()
        if (token.isNotEmpty())
            request.addHeader("Authorization", "Bearer $token")

        return chain.proceed(request.build())
    }
}

//Проблемный класс с вызовом refreshToken
private fun provideAuthApi(): AuthApi {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(provideHttpLoginingInterceptor()).build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AuthApi::class.java)
}

class TokenAuthenticator(
    private val preferences: PrefsHelper
    ) : Authenticator {

    val api = provideAuthApi()
    private val MAX_COUNT_OF_FALL_RESPONSE = 3
    override fun authenticate(route: Route?, response: Response): Request? {
        if (countOfFailedResponse(response) >= MAX_COUNT_OF_FALL_RESPONSE) {
            return null
        }

        synchronized(this) {
            val result = refreshToken(preferences)
            if (!result) return null
        }

        return response.request
            .newBuilder()
            .addHeader("Authorization", "Bearer ${preferences.getToken()}")
            .build()
    }

    private fun refreshToken(preferences: PrefsHelper): Boolean {
        var isUpdatedToken = false
        api.refreshToken(TokenModel(refreshToken = preferences.getRefreshToken()))
            .enqueue(object : Callback<TokenModel> {
                override fun onFailure(call: Call<TokenModel>, t: Throwable) { }

                override fun onResponse(
                    call: Call<TokenModel>,
                    response: retrofit2.Response<TokenModel>
                ) {
                    if (response.isSuccessful && response.body() != null) {

                        val token = response.body()?.accessToken ?: ""
                        token.let { preferences.saveToken(it, preferences.getRefreshToken()) }
                        isUpdatedToken = true
                    }
                }
            })
        return isUpdatedToken
    }

    private fun countOfFailedResponse(response: Response): Int {
        var count = 1
        while (response.priorResponse != null) {
            count += 1
        }
        return count
    }
}