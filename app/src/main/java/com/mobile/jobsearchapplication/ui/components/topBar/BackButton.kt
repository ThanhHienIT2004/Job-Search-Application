package com.mobile.jobsearchapplication.ui.components.topBar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.ui.features.home.HomeScreen
import com.mobile.jobsearchapplication.ui.navigation.NavigationRoute.Companion.baseNavController

@Composable
fun BackButton(
    navController: NavController,
    route: String,
    modifier: Modifier = Modifier
) {
    Icon(
        painter = painterResource(R.drawable.ic_back),
        contentDescription = "Back Button",
        modifier = modifier
            .padding(start = 10.dp)
            .size(32.dp)
            .clickable { navController.navigate(route) {
                popUpTo(navController.graph.startDestinationId) {
                    saveState = false
                }
                launchSingleTop = true
            } },

        tint = Color.White
     )
}