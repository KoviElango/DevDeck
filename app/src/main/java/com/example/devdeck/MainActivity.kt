package com.example.devdeck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.devdeck.ui.navigation.AppNavigation
import com.example.devdeck.viewmodel.UserViewModel

class MainActivity : ComponentActivity() {

    private val userViewModel: UserViewModel by viewModels()

    private var startTime = 0L
    private val splashDuration = 1000L // Duration (1 seconds)

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        splashScreen.setKeepOnScreenCondition {
            System.currentTimeMillis() - startTime < splashDuration
        }

        startTime = System.currentTimeMillis()

        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            AppNavigation(navController = navController, viewModel = userViewModel)
        }
    }
}
