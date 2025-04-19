package com.mobile.jobsearchapplication.ui.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mobile.jobsearchapplication.ui.theme.LightBlue
import com.mobile.jobsearchapplication.ui.theme.LightPurple

@Composable
fun BaseScreen(
    actionsTop: @Composable (RowScope.() -> Unit)? = null,
    actionsBot: @Composable (RowScope.() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            actionsTop?.let {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                        .height(72.dp)
                        .background(brush = Brush.linearGradient(
                            colors = listOf(LightBlue, LightPurple)
                        ))
                        .offset(y = 16.dp)
                    ,
                ) {
                    it()
                }
            }
        },
        bottomBar = {
            actionsBot?.let {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .height(64.dp)
                        .background(Color.Transparent)
                    ,
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    it()
                }
            }
        }
    ) { padding ->
        content(padding)
    }
}