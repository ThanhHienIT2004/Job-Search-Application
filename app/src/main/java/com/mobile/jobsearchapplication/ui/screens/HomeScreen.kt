package com.mobile.jobsearchapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mobile.jobsearchapplication.viewmodel.UserViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: UserViewModel = viewModel()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            // Thanh tìm kiếm
            SearchBar()

            // Danh mục công việc theo nghề
            JobCategorySection()

            // Danh sách việc làm gợi ý
            RecommendedJobsList()
        }
    }

@Composable
fun SearchBar() {
    TextField(
        value = "",
        onValueChange = {},
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
        shape = RoundedCornerShape(16.dp)
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

        LazyRow(modifier = Modifier.padding(top = 8.dp)) {
            val categories = listOf("Bán hàng", "Tạp vụ", "Giúp việc")

            items(categories.size) { index ->
                JobCategoryItem(category = categories[index])
            }
        }
    }
}

@Composable
fun JobCategoryItem(category: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Surface(
            modifier = Modifier.size(80.dp),
            color = Color.LightGray,
            shape = RoundedCornerShape(50)
        ) {
            // Placeholder cho hình ảnh
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

        LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
            val jobs = listOf("Job 1", "Job 2", "Job 3")

            items(jobs.size) { index ->
                JobListItem(jobTitle = jobs[index])
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
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Surface(
                modifier = Modifier.size(60.dp),
                color = Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            ) {
                // Placeholder hình ảnh
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = jobTitle, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = "Mô tả công việc ngắn gọn...", fontSize = 12.sp, color = Color.Gray)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Search, contentDescription = "Clock", tint = Color.Gray)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Today • 23 min", fontSize = 12.sp, color = Color.Gray)
                }
            }
        }
    }
}

