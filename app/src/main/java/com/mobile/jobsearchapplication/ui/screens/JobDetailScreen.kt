package com.mobile.jobsearchapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Share
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun JobDetailScreen(navController: NavHostController, jobTitle: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)
        ){
            // Thanh điều hướng trên cùng
            TopBar(navController) // ✅ Truyền navController vào đây

            // Nội dung chi tiết công việc
            JobDetailContent(jobTitle)
        }

        // Thanh hành động phía dưới
        BottomActionBar(
            modifier = Modifier
                .align(Alignment.BottomCenter) // Căn thanh bar xuống dưới cùng
        )
    }
}


@Composable
fun BottomActionBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(Color(0xFFF6F0FF)) // Màu nền nhạt
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Nút gọi
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clickable { /* TODO: Xử lý gọi */ }
                .padding(horizontal = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Call,
                contentDescription = "Gọi",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "Gọi",
                fontSize = 12.sp,
                color = Color.Black
            )
        }

        // Nút chat
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clickable { /* TODO: Xử lý chat */ }
                .padding(horizontal = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Chat,
                contentDescription = "Chat",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "Chat",
                fontSize = 12.sp,
                color = Color.Black
            )
        }

        // Nút ứng tuyển
        Button(
            onClick = { /* TODO: Xử lý ứng tuyển */ },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF6F61) // Màu cam đỏ
            ),
            shape = RoundedCornerShape(50),
            modifier = Modifier.height(48.dp)
        ) {
            Text(
                text = "Ứng tuyển",
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }
}


@Composable
fun TopBar(navControler: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier
                .size(24.dp)
                .clickable { /* TODO: Xử lý back */ }
        )
        Row {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = "Like",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { /* TODO: Xử lý yêu thích */ }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Share",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { /* TODO: Xử lý chia sẻ */ }
            )
        }
    }
}

@Composable
fun JobDetailContent(jobTitle: String?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Ảnh minh họa (tạm dùng Box làm ảnh placeholder)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color.LightGray)
        ) {
            Icon(
                imageVector = Icons.Default.Image,
                contentDescription = "Placeholder",
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.Center),
                tint = Color.Gray
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Tiêu đề công việc
        jobTitle?.let {
            Text(
                text = it,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(4.dp))

        // Mức lương
        Text(
            text = "35.000 - 40.000 đ / ngày",
            fontSize = 16.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Mô tả công việc
        Text(
            text = "Body text for your whole article or post. We’ll put in some lorem ipsum to show how a filled-out page might look:",
            fontSize = 14.sp,
            color = Color.DarkGray
        )
    }
}

@Composable
fun BottomActionBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(Color(0xFFF6F0FF)) // Màu nền nhạt
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Nút gọi
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clickable { /* TODO: Xử lý gọi */ }
                .padding(horizontal = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Call,
                contentDescription = "Gọi",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "Gọi",
                fontSize = 12.sp,
                color = Color.Black
            )
        }

        // Nút chat
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clickable { /* TODO: Xử lý chat */ }
                .padding(horizontal = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Chat,
                contentDescription = "Chat",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "Chat",
                fontSize = 12.sp,
                color = Color.Black
            )
        }

        // Nút ứng tuyển
        Button(
            onClick = { /* TODO: Xử lý ứng tuyển */ },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF6F61) // Màu cam đỏ
            ),
            shape = RoundedCornerShape(50),
            modifier = Modifier.height(48.dp)
        ) {
            Text(
                text = "Ứng tuyển",
                color = Color.White,
                fontSize = 14.sp
            )
        }

    }
}
//TopBar()	Thanh điều hướng trên cùng với icon Back, Like và Share.
//JobDetailContent()	Nội dung chi tiết công việc: ảnh, tiêu đề, lương, mô tả.
//BottomActionBar()	Thanh hành động phía dưới gồm các nút: Gọi, Chat và Ứng tuyển.
//Modifier.clickable	Cho phép click vào các phần tử.
//background()	Đặt màu nền cho phần tử.
//Button	Tạo nút bấm với màu sắc tuỳ chỉnh.
//Spacer	Tạo khoảng cách giữa các phần tử.