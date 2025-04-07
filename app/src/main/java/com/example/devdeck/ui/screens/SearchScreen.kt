package com.example.devdeck.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.devdeck.util.UiState
import com.example.devdeck.viewmodel.UserViewModel


@Composable
fun SearchScreen(
    viewModel: UserViewModel,
    onNavigateToProfile: (String) -> Unit
) {
    var username by remember { mutableStateOf("") }
    val state by viewModel.state.collectAsState()

    var hasNavigated by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("GitHub Username") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (username.isNotBlank()) {
                    viewModel.fetchUser(username)
                    hasNavigated = false
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(32.dp))

        when (state) {
            is UiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
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
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            UiState.Idle -> { /* No-op */ }
        }
    }
}
