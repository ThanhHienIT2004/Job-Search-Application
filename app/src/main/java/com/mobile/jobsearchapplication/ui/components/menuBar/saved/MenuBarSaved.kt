package com.mobile.jobsearchapplication.ui.components.menuBar.saved

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun MenuBarSaved(
    navController: NavController,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = Modifier.fillMaxWidth().height(90.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        listMenuBarSaved.forEach {
            ItemMenuSaved(it, { onClick(it.route) }, it.route == queryRoute)
        }
    }
    HorizontalDivider(thickness = 0.8.dp)
}

@Composable
fun ItemMenuSaved(
    item: MenuBarSavedModel,
    onClick: () -> Unit,
    isExpanded: Boolean,
) {
    ExtendedFloatingActionButton(
        onClick = { onClick() },
        icon = { Icon(item.icon, "Icon menu saved") },
        text = { Text(text = item.title) },
        expanded = isExpanded,
        containerColor = if (isExpanded) Color(0xFF8FBCE5) else Color(0xFFF4F9FA),
        contentColor = if (isExpanded) Color.Black else Color.Gray,
        shape = RoundedCornerShape(12.dp),
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = if (isExpanded) 6.dp else 2.dp,
            pressedElevation = 8.dp
        )
    )
}