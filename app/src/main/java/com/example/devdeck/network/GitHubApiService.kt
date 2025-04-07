package com.example.devdeck.network

import com.example.devdeck.model.GithubUser
import com.example.devdeck.model.UserListItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApiService {
    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): Response<GithubUser>

    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username: String
    ): Response<List<UserListItem>>

    @GET("users/{username}/following")
    suspend fun getFollowing(
        @Path("username") username: String
    ): Response<List<UserListItem>>

}