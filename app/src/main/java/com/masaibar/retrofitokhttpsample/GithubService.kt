package com.masaibar.retrofitokhttpsample

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {
    @GET("/users/{user}")
    fun getUserInfo(@Path("user") user: String): Call<User>
}
