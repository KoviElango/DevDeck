package com.example.devdeck.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.devdeck.model.GithubUser
import com.example.devdeck.ui.components.FloatingSearchButton

//unique background colors for each profile
private val cardBackgrounds = listOf(
    Color(0xFF381010),
    Color(0xFF2E2519),
    Color(0xFF254246),
    Color(0xFF212247),
    Color(0xFF1D3C16),
    Color(0xFF421E36)
)

/**
 * Card-style profile details of a GitHub user
 *
 * @param user The GitHub user whose profile is displayed.
 * @param onFollowersClick Callback triggered when followers section is tapped.
 * @param onFollowingClick Callback triggered when following section is tapped.
 * @param onSearchClick Callback triggered when floating search button is pressed.
 */
@Composable
fun UserProfileScreen(
    user: GithubUser,
    onFollowersClick: () -> Unit,
    onFollowingClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    //this is to cycle through the background colors and assign a unique color to each profile
    val backgroundColor = cardBackgrounds[user.id % cardBackgrounds.size]

    Scaffold(
        containerColor = backgroundColor,
        floatingActionButton = {
            FloatingSearchButton(onClick = onSearchClick)
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(2.dp, Color.White),
                    colors = CardDefaults.cardColors(containerColor = backgroundColor),
                    elevation = CardDefaults.cardElevation(8.dp),
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(190.dp)
                                .padding(bottom = 16.dp)
                                .clip(CircleShape)
                                .border(4.dp, Color.White, CircleShape)
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(user.avatar_url),
                                contentDescription = "Avatar",
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        Text(
                            text = user.login,
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color.White
                        )

                        user.name?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White.copy(alpha = 0.9f),
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }

                        user.bio?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White.copy(alpha = 0.8f),
                                modifier = Modifier.padding(top = 12.dp)
                            )
                        }

                        FollowStatsSection(
                            followers = user.followers,
                            following = user.following,
                            onFollowersClick = onFollowersClick,
                            onFollowingClick = onFollowingClick
                        )
                    }
                }
            }
        }
    }
}

/**
 * follower and following stats with clickable feature.
 *
 * @param followers Number of followers.
 * @param following Number of following.
 * @param onFollowersClick Callback for tapping on followers.
 * @param onFollowingClick Callback for tapping on following.
 */

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
            Icon(Icons.Default.Group, contentDescription = "Followers", tint = Color.White)
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "$followers", color = Color.White)
        }
        TextButton(onClick = onFollowingClick) {
            Icon(Icons.Default.Person, contentDescription = "Following", tint = Color.White)
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "$following", color = Color.White)
        }
    }
}
