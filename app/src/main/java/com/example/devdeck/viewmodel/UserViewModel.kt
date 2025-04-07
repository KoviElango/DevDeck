package com.example.devdeck.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devdeck.model.GithubUser
import com.example.devdeck.network.GitHubApiService
import com.example.devdeck.network.NetworkModule
import com.example.devdeck.util.ListUiState
import com.example.devdeck.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val _state = MutableStateFlow<UiState>(UiState.Idle)

    val state: StateFlow<UiState> = _state

    fun fetchUser(username: String) {
        viewModelScope.launch {
            _state.value = UiState.Loading
            val response = NetworkModule.api.getUser(username)
            try {
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


    private val _listState = MutableStateFlow<ListUiState>(ListUiState.Loading)
    val listState: StateFlow<ListUiState> = _listState

    fun fetchUserList(username: String, isFollowers: Boolean) {
        viewModelScope.launch {
            _listState.value = ListUiState.Loading
            try {
                val response = if (isFollowers) {
                    NetworkModule.api.getFollowers(username)
                } else {
                    NetworkModule.api.getFollowing(username)
                }

                if (response.isSuccessful && response.body() != null) {
                    _listState.value = ListUiState.Success(response.body()!!)
                } else {
                    _listState.value = ListUiState.Error("No data found.")
                }
            } catch (e: Exception) {
                _listState.value = ListUiState.Error("Network error.")
            }
        }
    }

    fun resetState() {
        _state.value = UiState.Loading
    }
}
