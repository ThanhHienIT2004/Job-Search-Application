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

@OptIn(ExperimentalMaterial3Api::class)
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
                        .height(80.dp)
                        .background(Color(0xFF4A5BCB))
                        .offset(y = 12.dp),
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
    ) { paddingValues ->
        content(paddingValues)
    }
}