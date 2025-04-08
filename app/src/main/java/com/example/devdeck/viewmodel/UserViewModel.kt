package com.example.devdeck.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devdeck.model.GithubUser
import com.example.devdeck.model.UserListItem
import com.example.devdeck.network.NetworkModule
import com.example.devdeck.util.ListUiState
import com.example.devdeck.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val _state = MutableStateFlow<UiState>(UiState.Idle)
    val state: StateFlow<UiState> = _state

    private val _listState = MutableStateFlow<ListUiState>(ListUiState.Loading)
    val listState: StateFlow<ListUiState> = _listState

    private var currentPage = 1
    private var _isLoadingMore = false
    val isLoadingMore: Boolean
        get() = _isLoadingMore

    private val loadedUsers = mutableListOf<UserListItem>()

    fun fetchPagedUserList(username: String, isFollowers: Boolean, isInitialLoad: Boolean = true) {
        if (_isLoadingMore) return

        if (isInitialLoad) {
            currentPage = 1
            loadedUsers.clear()
        }

        _isLoadingMore = true
        if (isInitialLoad) {
            _listState.value = ListUiState.Loading
        }

        viewModelScope.launch {
            try {
                val response = NetworkModule.api.getUserList(
                    username = username,
                    type = if (isFollowers) "followers" else "following",
                    page = currentPage
                )

                if (response.isSuccessful && response.body() != null) {
                    val newUsers = response.body()!!
                    loadedUsers.addAll(newUsers)
                    _listState.value = ListUiState.Success(loadedUsers)
                    if (newUsers.isNotEmpty()) currentPage++
                } else {
                    _listState.value = ListUiState.Error("No users found.")
                }
            } catch (e: Exception) {
                _listState.value = ListUiState.Error("Error loading users.")
            } finally {
                _isLoadingMore = false
            }
        }
    }

    fun fetchUser(username: String) {
        viewModelScope.launch {
            _state.value = UiState.Loading
            try {
                val response = NetworkModule.api.getUser(username)
                if (response.isSuccessful && response.body() != null) {
                    _state.value = UiState.Success(response.body()!!)
                } else {
                    _state.value = UiState.Error("User not found.")
                }
            } catch (e: Exception) {
                _state.value = UiState.Error("Something went wrong.")
            }
        }
    }

    fun resetState() {
        _state.value = UiState.Idle
    }
}
