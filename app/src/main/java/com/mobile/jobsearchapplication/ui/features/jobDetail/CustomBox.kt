package com.mobile.jobsearchapplication.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomInfoBox(
    icon: ImageVector,
    contentDescription: String,
    text: String,
    iconTint: Color = Color.Gray,
    textColor: Color = Color.Black,
    fontSize: Int = 14
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = iconTint,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = text,
            fontSize = fontSize.sp,
            color = textColor
        )
    }
}

@Composable
fun CustomSectionBox(
    title: String,
    content: String,
    modifier: Modifier = Modifier
) {
    if (content.isNotEmpty()) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = content,
            fontSize = 14.sp,
            modifier = modifier
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}