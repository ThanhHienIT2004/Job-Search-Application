package com.mobile.jobsearchapplication.ui.screens


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mobile.jobsearchapplication.model.Job
import com.mobile.jobsearchapplication.ui.screens.components.BackButton
import com.mobile.jobsearchapplication.ui.screens.components.BottomNavBarCustom
import com.mobile.jobsearchapplication.ui.screens.components.FilterButton
import com.mobile.jobsearchapplication.ui.screens.components.Screen

@Composable
fun PostFilterScreen(navController: NavController, query: String) {
    var selectedFilter by remember { mutableStateOf("Tất cả") }  // Trạng thái lưu bộ lọc

    BaseScreen (
        title = query,
        actionsTop = {
            FilterButton()
            BackButton(navController)
        },
        actionsBot = {
            BottomNavBarCustom(navController)
        }
    )
    {

    }
}

