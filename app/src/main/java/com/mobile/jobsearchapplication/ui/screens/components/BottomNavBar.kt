package com.mobile.jobsearchapplication.ui.screens.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

@Composable
fun BottomNavigationBar(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    val bottomNavItems = listOf(
        Icons.Filled.Home to "Home",
        Icons.Filled.AddCircle to "Đăng tin",
        Icons.Filled.Notifications to "Thông báo",
        Icons.Filled.AccountCircle to "Tài khoản"
    )

    NavigationBar {
        bottomNavItems.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.first, contentDescription = item.second) },
                label = { Text(item.second) },
                selected = selectedTab == index,
                onClick = { onTabSelected(index) }
            )
        }
    }
}
