package com.example.devdeck.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.devdeck.ui.components.FloatingSearchButton
import com.example.devdeck.util.ListUiState
import com.example.devdeck.viewmodel.UserViewModel

private val listBackground = Color(0xFF2A313C)

@Composable
fun UserListScreen(
    username: String,
    isFollowers: Boolean,
    viewModel: UserViewModel,
    onUserClick: (String) -> Unit,
    onSearchClick: () -> Unit
) {
    val state by viewModel.listState.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(username, isFollowers) {
        viewModel.fetchPagedUserList(username, isFollowers, isInitialLoad = true)
    }
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleIndex ->
                val totalItems = listState.layoutInfo.totalItemsCount
                if (lastVisibleIndex != null && lastVisibleIndex >= totalItems - 5) {
                    viewModel.fetchPagedUserList(username, isFollowers, isInitialLoad = false)
                }
            }
    }

    Scaffold(
        containerColor = listBackground,
        floatingActionButton = {
            FloatingSearchButton(onClick = onSearchClick)
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = if (isFollowers) "$username's Followers" else "$username's Following",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            when (val currentState = state) {
                is ListUiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }

                is ListUiState.Error -> {
                    Text(
                        text = currentState.message,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                is ListUiState.Success -> {
                    val users = currentState.users
                    LazyColumn(state = listState) {
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
                                Text(
                                    text = user.login,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.White
                                )
                            }
                        }

                        if (viewModel.isLoadingMore) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(color = Color.White)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
