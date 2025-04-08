package com.example.devdeck.model

data class GithubUser(
    val login: String,
    val name: String?,
    val avatar_url: String,
    val bio: String?,
    val followers: Int,
    val following: Int,
    val id: Int
)

/**
 * Represents a GitHub user's detailed profile information.
 *
 * @property login GitHub username.
 * @property name Full name of the user (nullable).
 * @property avatar_url URL to the user's avatar image.
 * @property bio Short bio or description (nullable).
 * @property followers Number of followers.
 * @property following Number of users the user is following.
 * @property id Unique user ID.
 */