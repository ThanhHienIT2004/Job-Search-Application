package com.mobile.jobsearchapplication.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.mobile.jobsearchapplication.ui.base.BaseScreen
import com.mobile.jobsearchapplication.ui.components.bottom_bar.BottomNavBarCustom


@Composable
fun PostedScreen(navController: NavHostController) {
    BaseScreen(
        title = "Việc đã đăng",
        showBackButton = true,
        onBackClick = { navController.popBackStack() },
        showSearch = true, // Bật icon tìm kiếm
        navController = navController,
        actionsBot = { BottomNavBarCustom(navController,hasUnreadNotifications = false) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
        }
    }
}