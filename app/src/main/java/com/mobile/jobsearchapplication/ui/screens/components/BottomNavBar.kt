package com.mobile.jobsearchapplication.ui.screens.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

// Định nghĩa các màn hình
sealed class Screen(val route: String, val icon: ImageVector, val title: String) {
    object Home : Screen("home_screen", Icons.Filled.Home, "Trang chủ")
    object PostJob : Screen("post_screen", Icons.Filled.AddCircle, "Đăng tin")
    object Notifications : Screen("notifications", Icons.Filled.Notifications, "Thông báo")
    object Account : Screen("account", Icons.Filled.AccountCircle, "Tài khoản")
}

val bottomNavItems = listOf(Screen.Home, Screen.PostJob, Screen.Notifications, Screen.Account)

@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar {
        bottomNavItems.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }

}