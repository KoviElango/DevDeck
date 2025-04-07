package com.example.devdeck.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.devdeck.ui.screens.SearchScreen
import com.example.devdeck.ui.screens.UserListScreen
import com.example.devdeck.ui.screens.UserProfileScreenWrapper
import com.example.devdeck.viewmodel.UserViewModel

@Composable
fun AppNavigation(navController: NavHostController, viewModel: UserViewModel) {
    NavHost(navController = navController, startDestination = "search") {

        composable("search") {
            SearchScreen(
                viewModel = viewModel,
                onNavigateToProfile = { username ->
                    navController.navigate("profile/$username")
                }
            )
        }

        composable("profile/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: return@composable

            UserProfileScreenWrapper(
                username = username,
                viewModel = viewModel,
                onFollowersClick = { login ->
                    navController.navigate("userlist/$login/followers")
                },
                onFollowingClick = { login ->
                    navController.navigate("userlist/$login/following")
                },
                onSearchClick = {
                    viewModel.resetState()
                    navController.navigate("search") {
                        popUpTo("search") { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable("userlist/{username}/{listType}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: return@composable
            val listType = backStackEntry.arguments?.getString("listType") ?: "followers"
            val isFollowers = listType == "followers"

            UserListScreen(
                username = username,
                isFollowers = isFollowers,
                viewModel = viewModel,
                onUserClick = { selectedUser ->
                    navController.navigate("profile/$selectedUser")
                },
                onSearchClick = {
                    viewModel.resetState()
                    navController.navigate("search") {
                        popUpTo("search") { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
