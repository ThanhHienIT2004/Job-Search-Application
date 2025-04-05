package com.mobile.jobsearchapplication.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.mobile.jobsearchapplication.ui.base.BaseScreen
import com.mobile.jobsearchapplication.ui.components.bottom_bar.BottomNavBarCustom


@Composable
fun PostedScreen(navController: NavController) {
    BaseScreen(
        title = "Tin đã đăng",
        actionsTop = {
            SearchBar(
                navController = navController,
                onMenuClicked = {
                    println("Menu clicked")
                }
            )
        },
        actionsBot = { BottomNavBarCustom(navController) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
        }
    }
}