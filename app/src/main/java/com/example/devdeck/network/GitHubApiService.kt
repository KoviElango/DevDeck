package com.example.devdeck.network

import com.example.devdeck.model.GithubUser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApiService {
    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): Response<GithubUser>
}