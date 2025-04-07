package com.example.devdeck.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.devdeck.model.GithubUser
import com.example.devdeck.ui.components.FloatingSearchButton


@Composable
fun UserProfileScreen(
    user: GithubUser,
    onFollowersClick: () -> Unit,
    onFollowingClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(user.avatar_url),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(128.dp)
                    .padding(bottom = 16.dp)
            )

            Text(text = user.login, style = MaterialTheme.typography.headlineSmall)

            user.name?.let {
                Text(text = it, style = MaterialTheme.typography.titleMedium)
            }

            user.bio?.let {
                Text(text = it, style = MaterialTheme.typography.bodyMedium)
            }

            FollowStatsSection(
                followers = user.followers,
                following = user.following,
                onFollowersClick = onFollowersClick,
                onFollowingClick = onFollowingClick
            )
        }

        FloatingSearchButton(
            onClick = onSearchClick,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}


@Composable
fun FollowStatsSection(
    followers: Int,
    following: Int,
    onFollowersClick: () -> Unit,
    onFollowingClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        TextButton(onClick = onFollowersClick) {
            Text(text = "$followers Followers")
        }

        TextButton(onClick = onFollowingClick) {
            Text(text = "$following Following")
        }
    }
}
