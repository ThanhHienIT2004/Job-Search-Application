package com.mobile.jobsearchapplication.ui.features.notification

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.mobile.jobsearchapplication.ui.theme.LightBlue
import com.mobile.jobsearchapplication.ui.theme.LightPurple
import com.mobile.jobsearchapplication.utils.FireBaseUtils

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NotificationScreen(
    navController: NavHostController? = null,
    modifier: Modifier = Modifier,
    viewModel: NotificationViewModel = viewModel()
) {
    val userState = FireBaseUtils.getLoggedInUserId()
    val notiState by viewModel.notificationsState.collectAsState()
    val userId = userState

    LaunchedEffect(Unit) {
        viewModel.loadNotification(userId)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

        }

        when {
            notiState.isLoading -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(5) {
                        Xuong()
                    }
                }
            }
            notiState.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Lỗi: ${notiState.error}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.error
                        ),
                        modifier = Modifier
                            .background(
                                Color(0xFFFFE6E6).copy(alpha = 0.8f),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
            notiState.notifications.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Không có thông báo",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray
                        ),
                        modifier = Modifier
                            .background(
                                Color.White.copy(alpha = 0.8f),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(notiState.notifications) { notification ->
                        NotificationItem(notification, viewModel)
                    }
                    if (notiState.hasMore) {
                        item {
                            Button(
                                onClick = { viewModel.loadNotification(userId) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(
                                        brush = Brush.linearGradient(
                                            colors = listOf(LightBlue, LightPurple)
                                        )
                                    ),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent
                                )
                            ) {
                                Text(
                                    text = if (notiState.isLoadingMore) "Đang tải thêm..." else "Tải thêm",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.White
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationItem(notification: SingleNotification, viewModel: NotificationViewModel) {
    val scale = remember { Animatable(1f) }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    LaunchedEffect(isPressed) {
        if (isPressed) {
            scale.animateTo(0.97f, animationSpec = tween(100))
            scale.animateTo(1f, animationSpec = tween(100))
        }
    }

    AnimatedVisibility(
        visible = true,
        enter = fadeIn(animationSpec = tween(500)) + slideInVertically(initialOffsetY = { it / 2 })
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(1f)
                .scale(scale.value)
                .shadow(6.dp, RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .clickable(interactionSource = interactionSource, indication = null) {
                    viewModel.checkIsRead(notification.id as Long, true)
                }
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color.White, Color(0xFFF5F5F5))
                    )
                ),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(LightBlue, LightPurple)
                            )
                        )
                        .shadow(4.dp, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = notification.title.firstOrNull()?.toString() ?: "N",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                ) {
                    Text(
                        text = notification.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 18.sp,
                            fontWeight = if (notification.isRead) FontWeight.Normal else FontWeight.SemiBold,
                            color = Color(0xFF1A1A1A)
                        )
                    )
                    Text(
                        text = notification.description,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 14.sp,
                            color = Color(0xFF666666)
                        ),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Loại: ${notification.type}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 12.sp,
                                color = Color(0xFF888888)
                            )
                        )
                        notification.createdAt?.let {
                            Text(
                                text = "Nhận lúc: $it",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 12.sp,
                                    color = Color(0xFF888888)
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Xuong() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp)
            .shadow(4.dp, RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFFE0E0E0), Color(0xFFD0D0D0))
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(16.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(Color(0xFFE0E0E0), Color(0xFFD0D0D0))
                            )
                        )
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(12.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(Color(0xFFE0E0E0), Color(0xFFD0D0D0))
                            )
                        )
                        .padding(top = 8.dp)
                )
                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .height(10.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color(0xFFE0E0E0), Color(0xFFD0D0D0))
                                )
                            )
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .height(10.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color(0xFFE0E0E0), Color(0xFFD0D0D0))
                                )
                            )
                    )
                }
            }
        }
    }
}