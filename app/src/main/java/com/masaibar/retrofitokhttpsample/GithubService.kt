package com.masaibar.retrofitokhttpsample

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {
    @GET("/users/{user}")
    fun getUserInfoAsObservable(@Path("user") user: String): Observable<User>
    fun getUserInfoAsCall(@Path("user") user: String): Call<User>
}
