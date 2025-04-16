package com.mobile.jobsearchapplication.ui.components.topBar

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun IconNotification(
    modifier: Modifier = Modifier
){
    Icon(
        imageVector = Icons.Default.Notifications,
        contentDescription = "Notifications Icon",
        tint = Color.White,
        modifier = modifier.size(28.dp)
    )
}