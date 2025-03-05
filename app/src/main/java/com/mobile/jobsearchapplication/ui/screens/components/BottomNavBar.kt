package com.mobile.jobsearchapplication.ui.screens.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.mobile.jobsearchapplication.viewmodel.UserViewModel

@Composable
fun BottomNavigationBar(viewModel: UserViewModel, selectedTab: Int, onTabSelected: (Int) -> Unit) {
    NavigationBar {
        viewModel.bottomNavItems.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = selectedTab == index,
                onClick = { onTabSelected(index) }
            )
        }
    }
}
