@file:JvmName("BottomNavBarKt")

package com.mobile.jobsearchapplication.ui.components.bottomBar

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mobile.jobsearchapplication.ui.theme.LightBlue

// Định nghĩa các màn hình
sealed class Screen(val route: String, val icon: ImageVector) {
    object Home : Screen("home_screen", Icons.Filled.Home)
    object PostedJob : Screen("posted_screen", Icons.Filled.Article)
    object PostJob : Screen("post_screen", Icons.Filled.AddCircle)
    object Notifications : Screen("notificationsState", Icons.Filled.Notifications)
    object Account : Screen("account", Icons.Filled.AccountCircle)
}

val bottomNavItems = listOf(
    Screen.Home,
    Screen.PostedJob,
    Screen.PostJob,
    Screen.Notifications,
    Screen.Account
)

@Composable
fun BottomNavBarCustom(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val currRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    Surface(
        color = LightBlue,
        shape = RoundedCornerShape(32.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(50.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent),
            horizontalArrangement = Arrangement.SpaceEvenly, // Căn đều các icon
            verticalAlignment = Alignment.CenterVertically
        ) {
            bottomNavItems.forEach { screen ->
                if (screen.route == currRoute) {
                    CurrIconBottomNav(screen.route, screen.icon, navController)
                } else {
                    IconBottomNav(screen.route, screen.icon, navController)
                }
            }
        }
    }
}

@Composable
fun IconBottomNav(
    route: String,
    icon: ImageVector,
    navController: NavController
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.2f else 1f,
        animationSpec = tween(200),
        label = "scaleAnimation"
    )

    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .scale(scale)
            .background(Color.Transparent)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    }
                )
            }
    ) {
        IconButton(
            onClick = {
                isPressed = true
                navController.navigate(route) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = route,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}


@Composable
fun CurrIconBottomNav(
    route: String,
    icon: ImageVector,
    navController: NavController
) {
    val scale by animateFloatAsState(
        targetValue = 1.2f, // Phóng to khi chọn
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "scaleAnimation"
    )

    Box(
        modifier = Modifier
            .size(50.dp) // Kích thước tăng nhẹ để tạo điểm nhấn
            .clip(CircleShape)
            .background(Color.White) // Làm nổi bật icon được chọn
            .scale(scale),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = {
                navController.navigate(route) {
                    restoreState = true
                }
            },
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = route,
                tint = LightBlue,
                modifier = Modifier.size(28.dp) // Tăng nhẹ icon để tạo sự khác biệt
            )
        }
    }
}
