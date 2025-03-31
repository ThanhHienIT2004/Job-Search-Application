package com.mobile.jobsearchapplication.ui.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat.MessagingStyle.Message

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen (
    isLoading: Boolean? = false,
    errorMessage: String? = null,
    actionsTop: @Composable (RowScope.() -> Unit)? = null,
    actionsBot: @Composable (RowScope.() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            actionsTop?.let {
                Row (
                    modifier = Modifier.fillMaxWidth().height(64.dp),

                ) {
                    actionsTop()
                }
            }
        },
        bottomBar = {
            actionsBot?.let {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    actionsBot()
                }
            }
        }

    ) { padding ->
        content(padding)
    }
}