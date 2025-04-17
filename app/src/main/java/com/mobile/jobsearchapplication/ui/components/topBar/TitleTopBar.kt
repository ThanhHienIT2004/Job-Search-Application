package com.mobile.jobsearchapplication.ui.components.topBar

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TitleTopBar(
    modifier: Modifier = Modifier,
    text: String = ""
) {
    Text(
        text = text,
        fontSize = 32.sp, fontWeight = FontWeight.Bold,
        modifier = modifier.padding(start = 10.dp),
        color = Color.White
    )
}