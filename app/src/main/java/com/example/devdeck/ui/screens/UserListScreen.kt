package com.example.devdeck.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.devdeck.ui.components.FloatingSearchButton
import com.example.devdeck.util.ListUiState
import com.example.devdeck.viewmodel.UserViewModel

@Composable
fun UserListScreen(
    username: String,
    isFollowers: Boolean,
    viewModel: UserViewModel,
    onUserClick: (String) -> Unit,
    onSearchClick: () -> Unit
) {
    val state by viewModel.listState.collectAsState()

    // Fetch the list when screen appears
    LaunchedEffect(username, isFollowers) {
        viewModel.fetchUserList(username, isFollowers)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().padding(16.dp)) {
            Text(
                text = if (isFollowers) "$username's Followers" else "$username's Following",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            when (state) {
                is ListUiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is ListUiState.Error -> {
                    Text(
                        text = (state as ListUiState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                is ListUiState.Success -> {
                    val users = (state as ListUiState.Success).users
                    LazyColumn {
                        items(users) { user ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onUserClick(user.login) }
                                    .padding(vertical = 8.dp)
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(user.avatar_url),
                                    contentDescription = "Avatar",
                                    modifier = Modifier
                                        .size(48.dp)
                                        .padding(end = 16.dp)
                                )
                                Text(text = user.login, style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                    }
                }
            }
        }

        FloatingSearchButton(
            onClick = onSearchClick,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}
