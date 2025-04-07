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
fun SearchScreen(viewModel: UserViewModel) {
    var username by remember { mutableStateOf("") }
    val state by viewModel.state.collectAsState()

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
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Observe the ViewModel's state and show result
        when (state) {
            is UiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            is UiState.Success -> {
                val user = (state as UiState.Success).user

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(user.avatar_url),
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(100.dp)
                            .padding(8.dp)
                    )
                    Text("Username: ${user.login}")
                    user.name?.let { Text("Name: $it") }
                    user.bio?.let { Text("Bio: $it") }
                    Text("Followers: ${user.followers}")
                    Text("Following: ${user.following}")
                }
            }

            is UiState.Error -> {
                Text(
                    text = (state as UiState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}
