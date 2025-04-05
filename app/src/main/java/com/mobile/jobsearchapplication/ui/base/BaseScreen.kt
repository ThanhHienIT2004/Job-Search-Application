package com.mobile.jobsearchapplication.ui.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobile.jobsearchapplication.ui.theme.LightBlue
import com.mobile.jobsearchapplication.ui.theme.LightPurple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen(
    title: String = "",
    isLoading: Boolean? = false,
    errorMessage: String? = null,
    showBackButton: Boolean = false,
    onBackClick: (() -> Unit)? = null,
    actionsTop: @Composable (RowScope.() -> Unit)? = null,
    actionsBot: @Composable (RowScope.() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit

) {
    Scaffold(
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .background( brush = Brush.linearGradient(
                        colors = listOf(LightBlue, LightPurple)
                    ))
                    .offset(y = 12.dp)
                ,

            ) {
                // Nút back (nếu có)
                if (showBackButton) {
                    IconButton(
                        onClick = { onBackClick?.invoke() },
                        modifier = Modifier.size(48.dp) // Kích thước nút hợp lý
                    ) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowLeft,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }

                // Tiêu đề luôn ở giữa
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = White, // Tùy chỉnh màu nếu cần
                    textAlign = TextAlign.Start,

                )

                // ActionsTop (nếu có)
                actionsTop?.let {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        it()
                    }
                } ?: Spacer(modifier = Modifier.width(48.dp)) // Giữ chỗ để cân bằng
            }
        },
        bottomBar = {
            actionsBot?.let {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    it()
                }
            }
        }
    ) { padding ->
        content(padding)
    }
}