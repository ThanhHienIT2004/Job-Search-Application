package com.mobile.jobsearchapplication.ui.features.posted

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.mobile.jobsearchapplication.ui.base.BaseScreen
import com.mobile.jobsearchapplication.ui.components.bottomBar.BottomNavBarCustom


@Composable
fun PostedScreen(navController: NavHostController) {
    BaseScreen(
//        title = "Việc đã đăng",
//        showBackButton = true,
//        onBackClick = { navController.popBackStack() },
//        navController = navController,
        actionsBot = {
            BottomNavBarCustom(navController) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
        }
    }
}