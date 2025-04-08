package com.example.devdeck.util

import com.example.devdeck.model.GithubUser

//Represents the UI state for an individual GitHub user fetch operation.
sealed class UiState {
    data object Idle : UiState()
    data object Loading : UiState()
    data class Success(val user: GithubUser) : UiState()
    data class Error(val message: String) : UiState()

}
