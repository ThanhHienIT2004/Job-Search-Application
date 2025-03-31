package com.mobile.jobsearchapplication.data.viewmodel

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
import com.mobile.jobsearchapplication.data.viewmodel.UserViewModel
import com.mobile.jobsearchapplication.ui.components.*

import com.mobile.jobsearchapplication.data.model.MenuItem
import com.mobile.jobsearchapplication.data.model.BottomNavItem
import com.mobile.jobsearchapplication.ui.components.MenuItemRow

class UserViewModel : ViewModel() {

    val menuItems = listOf(
        MenuItem(Icons.Filled.Person, "Hồ sơ"),
        MenuItem(Icons.Filled.Favorite, "Tin đã lưu"),
        MenuItem(Icons.Filled.Article, "Tin đã đăng"),
        MenuItem(Icons.Filled.Settings, "Cài đặt tài khoản"),
        MenuItem(Icons.Filled.ExitToApp, "Đăng xuất")
    )
}
@Composable
fun MenuList(viewModel: UserViewModel, navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        viewModel.menuItems.forEach { item ->
            MenuItemRow(item,navController)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

