package com.example.devdeck.ui.screens

import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.devdeck.ui.components.RecentSearchList
import com.example.devdeck.util.UiState
import com.example.devdeck.viewmodel.UserViewModel

@RequiresApi(35)
@Composable
fun SearchScreen(
    viewModel: UserViewModel,
    onNavigateToProfile: (String) -> Unit
) {
    var username by remember { mutableStateOf("") }
    val state by viewModel.state.collectAsState()
    var hasNavigated by remember { mutableStateOf(false) }
    val recentSearches by viewModel.recentSearches.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF2A313C)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("GitHub Username", color = Color.White) },
                    singleLine = true,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.Gray,
                        cursorColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        if (username.isNotBlank()) {
                            viewModel.addRecentSearch(username)
                            viewModel.fetchUser(username)
                            hasNavigated = false
                        }
                    },
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xFF2A313C)
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        modifier = Modifier
                            .size(32.dp),
                        tint = Color(0xFF2A313C)
                    )
                }

            }

            Spacer(modifier = Modifier.height(24.dp))

            RecentSearchList(
                items = recentSearches,
                onItemClick = { selected -> username = selected },
                onItemRemove = { removed -> viewModel.removeRecentSearch(removed) }
            )

            when (state) {
                is UiState.Loading -> {
                    CircularProgressIndicator(color = Color.White)
                }

                is UiState.Success -> {
                    val user = (state as UiState.Success).user
                    if (!hasNavigated) {
                        LaunchedEffect(Unit) {
                            hasNavigated = true
                            onNavigateToProfile(user.login)
                        }
                    }
                }

                is UiState.Error -> {
                    Text(
                        text = (state as UiState.Error).message,
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                UiState.Idle -> Unit
            }
        }
    }
}
