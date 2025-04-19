package com.mobile.jobsearchapplication.ui.components.dropdownMenu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DisplaySettings
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.GridOff
import androidx.compose.material.icons.outlined.GridOn
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun MenuGrid(
    onGridChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
    ) {
        SmallFloatingActionButton(
            onClick = { expanded = !expanded },
            containerColor = Color(0xFFEEEAEA),
            contentColor =  Color.Gray,
            shape = RoundedCornerShape(12.dp),
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 2.dp,
                pressedElevation = 8.dp
            )
        ) {
            Icon(
                Icons.Default.Settings, contentDescription = "More options",
                tint = if (!expanded) Color.Gray else Color.Black
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color(0xFFEEEAEA))
        ) {
            DropdownMenuItem(
                text = { Text("1 cột") },
                leadingIcon = { Icon(Icons.Outlined.GridOff, contentDescription = null) },
                onClick = { onGridChanged(1) }
            )
            DropdownMenuItem(
                text = { Text("2 cột") },
                leadingIcon = { Icon(Icons.Outlined.GridView, contentDescription = null) },
                onClick = { onGridChanged(2) }
            )
            DropdownMenuItem(
                text = { Text("3 cột") },
                leadingIcon = { Icon(Icons.Outlined.GridOn, contentDescription = null) },
                onClick = { onGridChanged(3) }
            )
        }
    }
}