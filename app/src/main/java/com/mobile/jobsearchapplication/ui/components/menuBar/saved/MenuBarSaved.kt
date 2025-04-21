package com.mobile.jobsearchapplication.ui.components.menuBar.saved

import androidx.appcompat.widget.MenuPopupWindow.MenuDropDownListView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Help
import androidx.compose.material.icons.automirrored.outlined.OpenInNew
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mobile.jobsearchapplication.ui.features.saved.SavedViewModel
import com.mobile.jobsearchapplication.ui.navigation.NavigationRoute.Companion.baseNavController

@Composable
fun MenuBarSaved(
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        listMenuBarSaved.forEach {
            ItemMenuSaved(it, { onTabSelected(it.route) }, it.route == selectedTab)
        }
    }
}

@Composable
fun ItemMenuSaved(
    item: MenuBarSavedModel,
    onClick: () -> Unit,
    isExpanded: Boolean,
    modifier: Modifier= Modifier
) {
    ExtendedFloatingActionButton(
        onClick = { onClick() },
        icon = { Icon(item.icon, "Icon menu saved") },
        text = { Text(text = item.title, style = MaterialTheme.typography.bodyMedium) },
        expanded = isExpanded,
        containerColor = Color(0xFFF5F5F5),
        contentColor = if (isExpanded) Color.Black else Color.Gray,
        shape = RoundedCornerShape(12.dp),
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = if (isExpanded) 6.dp else 2.dp,
            pressedElevation = 8.dp
        )
    )
}
