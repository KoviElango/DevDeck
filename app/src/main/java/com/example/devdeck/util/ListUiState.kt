package com.example.devdeck.util

import com.example.devdeck.model.UserListItem

//Represents the UI state for a list of users (followers or following).
sealed class ListUiState {
    data object Loading : ListUiState()
    data class Success(val users: List<UserListItem>) : ListUiState()
    data class Error(val message: String) : ListUiState()
}
