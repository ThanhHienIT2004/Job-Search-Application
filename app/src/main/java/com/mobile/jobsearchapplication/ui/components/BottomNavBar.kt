@file:JvmName("BottomNavBarKt")

package com.mobile.jobsearchapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

// Định nghĩa các màn hình
sealed class Screen(val route: String, val icon: ImageVector, val title: String) {
    object Home : Screen("home_screen", Icons.Filled.Home, "Trang chủ")
    object PostJob : Screen("post_screen", Icons.Filled.AddCircle, "Đăng tin")
    object Notifications : Screen("notifications", Icons.Filled.Notifications, "Thông báo")
    object Account : Screen("account", Icons.Filled.AccountCircle, "Tài khoản")
}

val bottomNavItems = listOf(Screen.Home, Screen.PostJob, Screen.Notifications, Screen.Account)

@Composable
fun BottomNavBarCustom(navController: NavController) {
    val currRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        // Bottom Bar
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .align(Alignment.BottomCenter),
            color = Color(0xFFE0D4F5)
        ) {}

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            bottomNavItems.forEach { screen ->
                if (screen.route == currRoute)
                    CurrIconBottomNav(screen.route, screen.icon, screen.title, navController)
                else
                    IconBottonNav(screen.route, screen.icon, screen.title, navController)
            }
        }
    }
}

@Composable
fun IconBottonNav(route: String, icon: ImageVector, label: String, navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = { navController.navigate((route)) {
                popUpTo(navController.graph.startDestinationId) { saveState = true }
                launchSingleTop = true
                restoreState = true
            } },
            modifier = Modifier
                .size(48.dp) // Tăng kích thước để chứa cả icon + text
                .clip(CircleShape)
                .background(Color(0xFFFBFBFC))
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(30.dp),
                tint = Color(0xFF562B88)
            )

        }
        Text(
            text = label,
            color = Color(0xFF562B88),
            fontSize = 10.sp,
        )
    }
}

@Composable
fun CurrIconBottomNav(route: String, icon: ImageVector, label: String, navController: NavController) {
    Box (
        modifier = Modifier
            .fillMaxHeight()
            .wrapContentWidth(Alignment.CenterHorizontally)
    ) {
        IconButton(
            onClick = { navController.navigate((route)) {
                popUpTo(navController.graph.startDestinationId) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
            },
            modifier = Modifier
                .size(56.dp) // Đặt kích thước cho nút tròn
                .offset(y = (-10).dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(1.dp, Color(0xFF6C37B4), CircleShape)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(44.dp),
                tint = Color(0xFF340E64)
            )
        }

        Text(
            text = label,
            color = Color(0xFF562B88),
            fontSize = 12.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Prev() {
    MaterialTheme {
        val navController = rememberNavController()
        BottomNavBarCustom(navController)
    }
}