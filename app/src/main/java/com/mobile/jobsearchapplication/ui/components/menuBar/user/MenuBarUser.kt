package com.mobile.jobsearchapplication.ui.components.menuBar.user

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mobile.jobsearchapplication.ui.navigation.NavigationRoute.Companion.baseNavController

@Composable
fun MenuBarUser(
    isMainMenuBar: Boolean,
    navController: NavController
) {
    if (isMainMenuBar) {
        MainMenuItemUser()
    } else {
        MenuItemUser(navController)
    }
}

@Composable
fun MainMenuItemUser() {
    val viewModel: MenuBarUserViewModel = viewModel()
    val menuBarState by viewModel.menuBarState.collectAsState()

    viewModel.listMainMenuBarUser.forEach { item ->
        Card(
            modifier = Modifier
                .padding()
                .clickable {
                    viewModel.onMenuBarUser(item)
                }
                .then(
                    if (item == menuBarState.onMainMenuBar)
                        Modifier.border(1.dp, Color.Gray, CircleShape)
                    else
                        Modifier
                ),
            colors = CardDefaults.cardColors(Color(0xFFF3F7FF)),
            elevation = CardDefaults.elevatedCardElevation(4.dp),
            shape = CircleShape
        ) {
            Icon(
                item.icon, contentDescription = item.title,
                modifier = Modifier.padding(12.dp).size(24.dp)
            )
        }
    }
}

@Composable
fun MenuItemUser(
    navController: NavController
) {
    val viewModel: MenuBarUserViewModel = viewModel()
    val menuBarState by viewModel.menuBarState.collectAsState()

    viewModel.getMenuItemUser(menuBarState.onMainMenuBar).forEach { item ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp, 8.dp)
                .clickable {
                    if (item.action != null) { item.action!!.invoke() }
                    if (item.route != null) { navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                    } }
                },
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.elevatedCardElevation(10.dp)
        ) {
            Row(
                modifier = Modifier.padding(12.dp, 18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(item.icon, contentDescription = item.title, modifier = Modifier.size(32.dp))
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = item.title, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}


