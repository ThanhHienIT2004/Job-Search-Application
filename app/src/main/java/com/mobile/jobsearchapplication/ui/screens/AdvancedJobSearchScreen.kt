package com.mobile.jobsearchapplication.ui.screens


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.mobile.jobsearchapplication.ui.base.BaseScreen
import com.mobile.jobsearchapplication.ui.components.top_bar.BackButton
import com.mobile.jobsearchapplication.ui.components.bottom_bar.BottomNavBarCustom
import com.mobile.jobsearchapplication.ui.components.top_bar.FilterButton

@Composable
fun PostFilterScreen(navController: NavController, query: String) {
    var selectedFilter by remember { mutableStateOf("Tất cả") }  // Trạng thái lưu bộ lọc

    BaseScreen (
//        title = query,
        actionsTop = {
            FilterButton()
            BackButton(navController)
        },
        actionsBot = {
            BottomNavBarCustom(navController,hasUnreadNotifications = false)
        }
    )
    {

    }
}

