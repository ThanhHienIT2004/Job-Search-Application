package com.mobile.jobsearchapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mobile.jobsearchapplication.viewmodel.UserViewModel
import com.mobile.jobsearchapplication.ui.screens.components.*

@Composable
fun UserScreen(navController: NavController, viewModel: UserViewModel = viewModel()) {
    BaseScreen (
        title = "Hồ sơ của bạn",
        actionsBot = { BottomNavBarCustom(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            MenuList(viewModel, navController)
        }
    }
}
