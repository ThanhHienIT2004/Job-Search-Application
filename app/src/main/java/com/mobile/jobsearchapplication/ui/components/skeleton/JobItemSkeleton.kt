package com.mobile.jobsearchapplication.ui.components.skeleton

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.placeholderShimmer
import androidx.wear.compose.material.rememberPlaceholderState

@Composable
fun SectionJobSkeleton(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        repeat(10) {
            val pagerState = rememberPagerState(pageCount = { 10 })

            // Placeholder cho tiêu đề
            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(25.dp)
                    .padding(start = 10.dp, top = 5.dp)
                    .clip(RoundedCornerShape(8.dp))

            )

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentPadding = PaddingValues(horizontal = 40.dp),
                pageSpacing = 16.dp
            ) {
                JobItemSkeleton()
            }
        }
    }
}

@Composable
fun JobItemSkeleton(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFBFCFF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            // Placeholder cho hình ảnh hoặc nội dung chính
            ShimmerBox(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.7f)
                    .padding(12.dp, top =  12.dp, end = 12.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            ShimmerBox(
                modifier = Modifier.fillMaxWidth(0.7f)
                    .padding(12.dp, top =  4.dp)
                    .height(24.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            ShimmerBox(
                modifier = Modifier.fillMaxWidth(0.4f)
                    .padding(12.dp, top =  4.dp)
                    .height(16.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            ShimmerBox(
                modifier = Modifier.fillMaxWidth(0.6f)
                    .padding(12.dp, top =  4.dp)
                    .height(16.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

        }
    }
}

@Composable
fun ShimmerBox(
    modifier: Modifier = Modifier
) {
    // Màu shimmer đẹp hơn và rõ ràng hơn
    val shimmerColors = listOf(
        Color(0xFFBDBDBD),
        Color(0xFFE0E0E0),
        Color(0xFFBDBDBD),
    )

    // Tạo animation
    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        initialValue = -200f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    // Brush shimmer chạy ngang (chiều từ trái qua phải)
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(x = translateAnim, y = 0f),
        end = Offset(x = translateAnim + 100f, y = 0f)
    )

    // Dùng Box hoặc Text làm nền shimmer
    Box(
        modifier = modifier
            .background(brush = brush)
    )
}
