package com.mobile.jobsearchapplication.ui.components.skeleton

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobile.jobsearchapplication.ui.features.job.JobItem

@Composable
fun SectionJobSkeleton(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        repeat(10) {
            val pagerState = rememberPagerState(pageCount = { 10 })

            Surface(
                modifier = Modifier.padding(start = 10.dp ,top = 5.dp)
                    .fillMaxWidth(0.6f).height(30.dp),
                color = Color(0xFF8C8D8F)
            ) {}

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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier.padding(top = 5.dp)
                    .fillMaxWidth(0.95f)
                    .fillMaxHeight(0.6f),
                color = Color(0xFF8C8D8F)


            ) {}
            Surface(
                modifier = Modifier.padding(8.dp, 8.dp)
                    .fillMaxWidth(),
                color = Color(0xFF8C8D8F)
            ) {}

            Row {
                Column(
                    modifier = Modifier.weight(5f)
                ) {
                    Surface(
                        modifier = Modifier.padding(8.dp, 4.dp)
                            .fillMaxWidth(),
                        color = Color(0xFF8C8D8F)
                    ) {}
                    Surface(
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                            .fillMaxWidth(),
                        color = Color(0xFF8C8D8F)
                    ) {}
                }
            }
        }
    }
}