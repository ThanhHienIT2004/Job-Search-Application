package com.mobile.jobsearchapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobile.jobsearchapplication.R

@Composable
fun JobDetailScreen(jobId: String) {
    Scaffold(
        topBar = {
            JobDetailTopBar()
        },
        bottomBar = {
            JobDetailBottomBar()
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Placeholder Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color.LightGray)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_image),
                    contentDescription = "Job Image",
                    modifier = Modifier.align(Alignment.Center),
                    tint = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Job Title & Salary
            Text(
                text = "TUYỂN SV LÀM PART TIME",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "35.000 - 40.000 đ / ngày",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Job Description
            Text(
                text = "Body text for your whole article or post. We’ll put in some lorem ipsum to show how a filled-out page might look:",
                fontSize = 14.sp,
                color = Color.Black
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobDetailTopBar() {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = { /* TODO: Xử lý quay lại */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            IconButton(onClick = { /* TODO: Xử lý yêu thích */ }) {
                Icon(
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = "Favorite"
                )
            }
            IconButton(onClick = { /* TODO: Xử lý chia sẻ */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_share),
                    contentDescription = "Share"
                )
            }
        }
    )
}

@Composable
fun JobDetailBottomBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFEDE7F6)) // Màu nền nhẹ
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(id = R.drawable.ic_call),
                contentDescription = "Gọi",
                tint = Color.Black
            )
            Text(text = "Gọi", fontSize = 12.sp, color = Color.Black)
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(id = R.drawable.ic_chat),
                contentDescription = "Chat",
                tint = Color.Black
            )
            Text(text = "Chat", fontSize = 12.sp, color = Color.Black)
        }

        Button(
            onClick = { /* TODO: Xử lý ứng tuyển */ },
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD1C4E9))
        ) {
            Text(text = "Ứng tuyển", fontSize = 16.sp, color = Color.Black)
        }
    }
}
