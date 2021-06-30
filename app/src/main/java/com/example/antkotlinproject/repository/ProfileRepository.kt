package com.example.antkotlinproject.repository

import com.example.antkotlinproject.data.model.User
import com.example.antkotlinproject.data.network.api.ProfileApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface ProfileRepository {
    fun fetchUserProfile(): Observable<User>
    fun fetchTeacherProfile(id: Int): Observable<User>
}

class ProfileRepositoryImpl(private val api: ProfileApi) : ProfileRepository {
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

}