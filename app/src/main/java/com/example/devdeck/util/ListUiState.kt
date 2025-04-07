package com.example.devdeck.util

import com.example.devdeck.model.UserListItem

sealed class ListUiState {
    object Loading : ListUiState()
    data class Success(val users: List<UserListItem>) : ListUiState()
    data class Error(val message: String) : ListUiState()
}
