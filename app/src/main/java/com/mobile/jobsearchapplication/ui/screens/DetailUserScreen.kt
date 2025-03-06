package com.mobile.jobsearchapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mobile.jobsearchapplication.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailUserScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hồ sơ") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            ProfileHeader()
            ProfileInfo()
            ProfileTabs()
            PostList()
        }
    }
}

@Composable
fun ProfileHeader() {
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.ic_profile),
            contentDescription = "Cover Photo",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 120.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Nguyễn Văn A", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text("Lập trình viên tại Công ty ABC", fontSize = 16.sp, color = Color.Gray)
        }
    }
}

@Composable
fun ProfileInfo() {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.Email, contentDescription = "Email", tint = Color.Gray)
            Spacer(modifier = Modifier.width(8.dp))
            Text("nguyenvana@gmail.com", fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.Phone, contentDescription = "Phone", tint = Color.Gray)
            Spacer(modifier = Modifier.width(8.dp))
            Text("+84 123 456 789", fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.LocationOn, contentDescription = "Address", tint = Color.Gray)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Hà Nội, Việt Nam", fontSize = 16.sp)
        }
    }
}

@Composable
fun ProfileTabs() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Bài viết", "Ảnh", "Bạn bè")

    TabRow(selectedTabIndex = selectedTab) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTab == index,
                onClick = { selectedTab = index },
                text = { Text(title) }
            )
        }
    }
}

@Composable
fun PostList() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Bài viết gần đây", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        repeat(3) { index ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Bài viết số ${index + 1}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Đây là nội dung mô tả bài viết của người dùng...")
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Icon(Icons.Filled.Favorite, contentDescription = "Like", tint = Color.Red)
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.Filled.Comment, contentDescription = "Comment", tint = Color.Blue)
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.Filled.Share, contentDescription = "Share", tint = Color.Green)
                    }
                }
            }
        }
    }
}
