package com.example.devdeck.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.devdeck.util.UiState
import com.example.devdeck.viewmodel.UserViewModel

@Composable
fun UserProfileScreenWrapper(
    username: String,
    viewModel: UserViewModel,
    onFollowersClick: (String) -> Unit,
    onFollowingClick: (String) -> Unit,
    onSearchClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(username) {
        viewModel.fetchUser(username)
    }

    when (state) {
        is UiState.Loading -> CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        is UiState.Success -> {
            val user = (state as UiState.Success).user
            UserProfileScreen(
                user = user,
                onFollowersClick = { onFollowersClick(user.login) },
                onFollowingClick = { onFollowingClick(user.login) },
                onSearchClick = onSearchClick
            )
        }
        is UiState.Error -> {
            Text(text = (state as UiState.Error).message)
        }
        UiState.Idle -> { /* No-op */ }
    }
}

