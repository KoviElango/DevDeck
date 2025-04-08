package com.example.devdeck.util

import com.example.devdeck.model.GithubUser

//Represents the UI state for an individual GitHub user fetch operation.
sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    data class Success(val user: GithubUser) : UiState()
    data class Error(val message: String) : UiState()

}
