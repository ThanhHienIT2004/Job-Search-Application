package com.mobile.jobsearchapplication.ui.components.menu_bar

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mobile.jobsearchapplication.ui.features.user.UserViewModel

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
    val viewModel: MenuBarViewModel = viewModel()
    val menuBarState by viewModel.menuBarState.collectAsState()

    viewModel.listMainMenuBarUser.forEach { item ->
        Card(
            modifier = Modifier
                .padding()
                .clickable {
                    viewModel.onMainMenuBar(item)
                }
                .then(
                    if (item == menuBarState.onMainMenuBar)
                        Modifier.border(1.dp, Color.Black, CircleShape)
                    else
                        Modifier
                ),
            colors = CardDefaults.cardColors(Color(0xFFF3F7FF)),
            elevation = CardDefaults.elevatedCardElevation(4.dp),
            shape = CircleShape
        ) {
            Column(
                Modifier.padding(12.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    item.icon, contentDescription = item.title,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun MenuItemUser(
    navController: NavController
) {
    val viewModel: MenuBarViewModel = viewModel()
    val menuBarState by viewModel.menuBarState.collectAsState()

    viewModel.getItemMenu(menuBarState.onMainMenuBar).forEach { item ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp, 8.dp)
                .clickable {
                    if (item.action != null) { item.action.invoke() }
                    if (item.route != null) { navController.navigate(item.route) }
                },
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.elevatedCardElevation()
        ) {
            Row(
                modifier = Modifier.padding(12.dp, 18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(item.icon, contentDescription = item.title, modifier = Modifier.size(32.dp))
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = item.title, fontSize = 18.sp)
            }
        }
    }
}


