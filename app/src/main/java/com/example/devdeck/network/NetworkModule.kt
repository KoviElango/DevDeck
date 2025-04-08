package com.example.devdeck.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Singleton object for Retrofit instance
object NetworkModule {
    private const val BASE_URL = "https://api.github.com/"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: GitHubApiService = retrofit.create(GitHubApiService::class.java)
}
