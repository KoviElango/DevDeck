package com.example.devdeck.network

import com.example.devdeck.model.GithubUser
import com.example.devdeck.model.UserListItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {
    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): Response<GithubUser>

    @GET("users/{username}/{type}")
    suspend fun getUserList(
        @Path("username") username: String,
        @Path("type") type: String, // followers or following
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 30
    ): Response<List<UserListItem>>

}