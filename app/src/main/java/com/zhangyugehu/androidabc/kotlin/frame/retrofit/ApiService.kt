package com.zhangyugehu.androidabc.kotlin.frame.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    companion object {
        const val PATH_USER = "user"
    }
    @GET("users/{$PATH_USER}/repos")
    fun listRepos(@Path(PATH_USER) user: String): Call<List<String>>
}