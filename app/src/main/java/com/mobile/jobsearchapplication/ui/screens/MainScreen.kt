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
            composable(Screen.Home.route) { HomeScreen() }
            composable(Screen.PostJob.route) { PostScreen() }
            composable(Screen.Notifications.route) { NotificationsScreen() }
            composable(Screen.Account.route) { UserScreen(navController) }
 
        }
    }
}
