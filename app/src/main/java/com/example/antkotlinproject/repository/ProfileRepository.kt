package com.example.antkotlinproject.repository

import com.example.antkotlinproject.data.model.User
import com.example.antkotlinproject.data.network.api.ProfileApi
import com.example.antkotlinproject.utils.PrefsHelper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody

interface ProfileRepository {
    fun fetchUserProfile(): Observable<User>
    fun fetchTeacherProfile(id: Int): Observable<User>
    fun editUserProfile(data: User): Observable<User>
    fun changeImage(avatar: MultipartBody.Part): Observable<User>
}

class ProfileRepositoryImpl(private val api: ProfileApi, private val preferences: PrefsHelper) : ProfileRepository {
    override fun fetchUserProfile(): Observable<User> {
        return api.fetchUserProfile()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun fetchTeacherProfile(id: Int): Observable<User> {
        return api.fetchTeacherProfile(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun editUserProfile(data: User): Observable<User> {
        return api.editUserProfile(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun changeImage(avatar: MultipartBody.Part): Observable<User> {
        return api.changeImage(avatar, preferences.getIsStuff())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}