package com.mobile.jobsearchapplication.ui.features.filter


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.mobile.jobsearchapplication.ui.base.BaseScreen
import com.mobile.jobsearchapplication.ui.components.bottomBar.BottomNavBar
import com.mobile.jobsearchapplication.ui.components.topBar.BackButton
import com.mobile.jobsearchapplication.ui.components.topBar.FilterButton
import com.mobile.jobsearchapplication.ui.components.topBar.TitleTopBar

@Composable
fun PostFilterScreen(navController: NavController, query: String) {

    BaseScreen (
        actionsTop = {
            BackButton(navController, "home_screen")
            TitleTopBar(text = query)
        },
        actionsBot = {
            BottomNavBar(navController)
        }
    )
    {

    }
}

