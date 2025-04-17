package com.mobile.jobsearchapplication.ui.features.saved

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.material.OutlinedButton
import com.mobile.jobsearchapplication.ui.base.BaseScreen
import com.mobile.jobsearchapplication.ui.components.bottomBar.BottomNavBarCustom
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
        actionsBot = { BottomNavBarCustom(navController) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding)
                .background(Color(0xFF153A50))
        ) {
            item {
                Row {
                    repeat(3) {
                        ElevatedButton(
                            onClick = {},
                            colors = ButtonDefaults.elevatedButtonColors(Color(0xFFAAD0E2))
                        ) { }
                    }
                }
            }
        }
    }
}