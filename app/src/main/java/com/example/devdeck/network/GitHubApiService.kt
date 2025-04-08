package com.example.devdeck.network

import com.example.devdeck.model.GithubUser
import com.example.devdeck.model.UserListItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {

    /**
     * Fetch a GitHub user's detailed profile information.
     *
     * @param username GitHub username.
     * @return [Response] containing [GithubUser] data.
     */

    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String
    ): Response<GithubUser>

    /**
     * Fetch a paginated list of either followers or following for a GitHub user.
     *
     * @param username GitHub username.
     * @param type Either "followers" or "following".
     * @param page Page number for pagination.
     * @param perPage Number of items per page (default is 30).
     * @return [Response] containing a list of [UserListItem].
     */

    @GET("users/{username}/{type}")
    suspend fun getUserList(
        @Path("username") username: String,
        @Path("type") type: String, // followers or following
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 30
    ): Response<List<UserListItem>>

}