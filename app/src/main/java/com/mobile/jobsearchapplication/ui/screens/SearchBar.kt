package com.mobile.jobsearchapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController


@Composable
fun SearchBar(
    navController: NavController,
    onMenuClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.End
    ) {
//        // Icon Menu
//        IconButton(onClick = onMenuClicked) {
//            Icon(
//                imageVector = Icons.Default.Menu,
//                contentDescription = "Menu",
//                tint = Color.White
//            )
//        }

        // Icon Search
        IconButton(onClick = {
            navController.navigate("search_screen") {
                launchSingleTop = true
                restoreState = true
            }

        }) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.White
            )
        }
    }
}


