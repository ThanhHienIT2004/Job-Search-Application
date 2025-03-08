package com.mobile.jobsearchapplication.ui.screens

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.mobile.jobsearchapplication.ui.screens.components.*

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Home.route) { HomeScreen(navController) }
            composable(Screen.PostJob.route) { PostScreen() }
<<<<<<< Updated upstream
            composable(Screen.Notifications.route) { NotificationsScreen(navController) }
=======
            composable(Screen.Notifications.route) { NotificationsScreen() }
>>>>>>> Stashed changes
            composable(Screen.Account.route) { UserScreen(navController) }
            composable("detail_user_screen") { DetailUserScreen(navController) }
            composable("login_register") { LoginRegisterScreen(navController) }
            composable("detail_job_screen") { JobDetailScreen(navController) }

        }
    }
}
