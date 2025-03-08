package com.mobile.jobsearchapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mobile.jobsearchapplication.viewmodel.UserViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: UserViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Đặt màu nền trắng cho toàn bộ màn hình
    ) {
        // Thanh tìm kiếm
        SearchBar()

        // Danh mục công việc theo nghề
        JobCategorySection()

        // Danh sách công việc gợi ý
        RecommendedJobsList()
    }
}

@Composable
fun SearchBar() {
    var searchText by remember { mutableStateOf("") }

    TextField(
        value = searchText,
        onValueChange = { searchText = it },
        placeholder = { Text("Tìm kiếm việc làm...") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu"
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun JobCategorySection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Việc làm theo nghề ➝",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        LazyRow(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
        ) {
            val categories = listOf("Bán hàng", "Tạp vụ", "Giúp việc", "Kế toán", "IT")

            items(categories) { category ->
                JobCategoryItem(category)
            }
        }
    }
}

@Composable
fun JobCategoryItem(category: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(end = 8.dp)
    ) {
        Surface(
            modifier = Modifier
                .size(80.dp)
                .background(Color.LightGray),
            shape = RoundedCornerShape(40.dp)
        ) {
            // Placeholder cho hình ảnh
            Box(
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = category, fontSize = 14.sp)
    }
}

@Composable
fun RecommendedJobsList() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Việc dành cho bạn ➝",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
        ) {
            val jobs = listOf(
                "Nhân viên bán hàng",
                "Giúp việc gia đình",
                "Kế toán",
                "Lập trình viên",
                "Bảo vệ"
            )

            items(jobs) { job ->
                JobListItem(jobTitle = job)
            }
        }
    }
}

@Composable
fun JobListItem(jobTitle: String) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color.LightGray),
                shape = RoundedCornerShape(8.dp)
            ) {
                // Placeholder cho hình ảnh
                Box(
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = jobTitle,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "Mô tả công việc ngắn gọn...",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Clock",
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Today • 23 min",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}
