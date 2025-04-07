package com.example.devdeck.util

import com.example.devdeck.model.GithubUser

sealed class UiState {
    object Loading : UiState()
    data class Success(val user: GithubUser) : UiState()
    data class Error(val message: String) : UiState()
}
