package com.example.devdeck.model

data class GithubUser(
    val login: String,
    val name: String?,
    val avatar_url: String,
    val bio: String?,
    val followers: Int,
    val following: Int
)
