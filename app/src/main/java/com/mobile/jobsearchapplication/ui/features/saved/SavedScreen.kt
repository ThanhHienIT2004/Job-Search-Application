package com.mobile.jobsearchapplication.ui.features.saved

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mobile.jobsearchapplication.ui.base.BaseScreen
import com.mobile.jobsearchapplication.ui.components.bottomBar.BottomNavBarCustom
import com.mobile.jobsearchapplication.ui.components.bottomBar.MenuNavBar
import com.mobile.jobsearchapplication.ui.components.topBar.TitleTopBar


@Composable
fun SavedScreen(navController: NavController) {
    BaseScreen(
        actionsTop = {
            TitleTopBar(
                modifier = Modifier.padding(start = 32.dp),
                text = "Bài đăng của bạn"
            )
        },
        actionsBot = { MenuNavBar(navController) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
        ) {

        }
    }
}