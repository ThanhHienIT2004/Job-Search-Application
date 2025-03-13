package com.mobile.jobsearchapplication.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobile.jobsearchapplication.ui.screens.components.BottomNavigationBar

@Composable
fun BaseScreen (
    title: String = "",
    showBackButton: Boolean = false,
    onBackClick: (() -> Unit)? = null,
    actionsTop: @Composable (RowScope.() -> Unit) = {},
    actionsBot: @Composable (RowScope.() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title)},
                navigationIcon = {
                    if (showBackButton) {
                        IconButton( onClick = { onBackClick?.invoke() }, modifier = Modifier.padding(10.dp) ) {
                            Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = "Back")
                        }
                    }
                },
                actions = actionsTop
            )
        },
        bottomBar = {
            actionsBot?.let {
                BottomAppBar { actionsBot() }
            }
        }
    ) { padding ->
        content(padding)
    }
}