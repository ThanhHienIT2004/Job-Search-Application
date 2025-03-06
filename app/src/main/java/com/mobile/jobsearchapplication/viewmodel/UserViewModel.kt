package com.mobile.jobsearchapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

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
}
