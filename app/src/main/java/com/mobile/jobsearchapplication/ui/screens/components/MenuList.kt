package com.mobile.jobsearchapplication.ui.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mobile.jobsearchapplication.viewmodel.UserViewModel

@Composable
fun MenuList(viewModel: UserViewModel = viewModel(), navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        viewModel.menuItems.forEach { item ->
            MenuItemRow(item, navController) // Truyền navController vào đây
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}


@Composable
fun MenuItemRow(item: com.mobile.jobsearchapplication.model.MenuItem, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (item.title == "Hồ sơ") {
                    navController.navigate("detail_user_screen") // Chuyển sang màn hình Hồ sơ
                }
            }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(item.icon, contentDescription = item.title, modifier = Modifier.size(32.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = item.title, fontSize = 18.sp)
    }
}

