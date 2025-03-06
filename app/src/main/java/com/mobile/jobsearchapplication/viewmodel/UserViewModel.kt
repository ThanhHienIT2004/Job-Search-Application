package com.mobile.jobsearchapplication.viewmodel

import androidx.compose.foundation.clickable
import androidx.lifecycle.ViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mobile.jobsearchapplication.viewmodel.UserViewModel
import com.mobile.jobsearchapplication.ui.screens.components.*

import com.mobile.jobsearchapplication.model.MenuItem
import com.mobile.jobsearchapplication.model.BottomNavItem

class UserViewModel : ViewModel() {

    val menuItems = listOf(
        MenuItem(Icons.Filled.Person, "Hồ sơ"),
        MenuItem(Icons.Filled.Favorite, "Tin đã lưu"),
        MenuItem(Icons.Filled.Article, "Tin đã đăng"),
        MenuItem(Icons.Filled.Settings, "Cài đặt tài khoản"),
        MenuItem(Icons.Filled.ExitToApp, "Đăng xuất")
    )

    val bottomNavItems = listOf(
        BottomNavItem(Icons.Filled.Home, "Home"),
        BottomNavItem(Icons.Filled.AddCircle, "Đăng tin"),
        BottomNavItem(Icons.Filled.Notifications, "Thông báo"),
        BottomNavItem(Icons.Filled.AccountCircle, "Tài khoản")
    )
}
@Composable
fun MenuList(viewModel: UserViewModel, navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        viewModel.menuItems.forEach { item ->
            MenuItemRow(item, navController)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun MenuItemRow(item: MenuItem, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                println("Clicked on: ${item.title}") // Debug: Kiểm tra xem có nhận click không
                if (item.title == "Hồ sơ") {
                    println("Navigating to detailUserScreen") // Debug: Kiểm tra điều hướng
                    navController.navigate("detailUserScreen")
                }
            }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(item.icon, contentDescription = item.title, modifier = Modifier.size(32.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = item.title)
    }
}

