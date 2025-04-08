package com.example.devdeck.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
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

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF2A313C)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Enter GitHub Username", color = Color.White) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
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

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (username.isNotBlank()) {
                        viewModel.fetchUser(username)
                        hasNavigated = false
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF2A313C)
                )
            ) {
                Text("Search")
            }

            Spacer(modifier = Modifier.height(32.dp))

            when (state) {
                is UiState.Loading -> {
                   // CircularProgressIndicator(color = Color.White)
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
