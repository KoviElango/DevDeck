package com.example.devdeck.viewmodel

import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devdeck.model.UserListItem
import com.example.devdeck.network.NetworkModule
import com.example.devdeck.util.ListUiState
import com.example.devdeck.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    // Holds UI state for single user fetch operations
    private val _state = MutableStateFlow<UiState>(UiState.Idle)
    val state: StateFlow<UiState> = _state

    // Holds UI state for pagination
    private val _listState = MutableStateFlow<ListUiState>(ListUiState.Loading)
    val listState: StateFlow<ListUiState> = _listState

    private var currentPage = 1
    private var _isLoadingMore = false
    val isLoadingMore: Boolean
        get() = _isLoadingMore

    private val loadedUsers = mutableListOf<UserListItem>()

    /**
     * Fetches a paginated list of followers or following for a given user.
     *
     * @param username GitHub username
     * @param isFollowers true for followers, false for following
     * @param isInitialLoad true if this is the first page or a refresh
     */
    fun fetchPagedUserList(username: String, isFollowers: Boolean, isInitialLoad: Boolean = true) {
        Log.d("PaginationDebug", "fetchPagedUserList called | page=$currentPage | initial=$isInitialLoad")
        if (_isLoadingMore) {
            Log.d("PaginationDebug", "Currently loading. Skipping new request.")
            return
        }

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
                    _listState.value = ListUiState.Success(loadedUsers.toList())
                    if (newUsers.isNotEmpty()) currentPage++
                } else {
                    _listState.value = ListUiState.Error("No users found.")
                }
            } catch (e: Exception) {
                _listState.value = ListUiState.Error("Error loading users.")
            } finally {
                _isLoadingMore = false
                Log.d("PaginationDebug", "Finished loading. isLoadingMore reset to false.")
            }
        }
    }

    /**
     * Fetches detailed information about a single GitHub user.
     *
     * @param username GitHub username
     */
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

    //Resets the single user UI state to idle.
    fun resetState() {
        _state.value = UiState.Idle
    }

    //Recent searches -we keep the state here but the logic is moved to RecentSearchManager
    private val recentSearchManager = RecentSearchManager()
    private val _recentSearches = MutableStateFlow<List<String>>(emptyList())

    val recentSearches: StateFlow<List<String>> = _recentSearches

    @RequiresApi(35)
    fun addRecentSearch(username: String) {
        _recentSearches.value = recentSearchManager.add(username)
    }

    fun removeRecentSearch(username: String) {
        _recentSearches.value = recentSearchManager.remove(username)
    }
}
