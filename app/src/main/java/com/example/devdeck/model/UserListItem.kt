package com.example.devdeck.model

data class UserListItem(
    val login: String,
    val avatar_url: String
)

/**
 * Represents a simplified GitHub user item used in follower/following lists.
 *
 * @property login GitHub username.
 * @property avatar_url URL to the user's avatar image.
 */