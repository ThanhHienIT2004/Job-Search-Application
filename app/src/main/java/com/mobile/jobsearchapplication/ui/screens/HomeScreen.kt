package com.mobile.jobsearchapplication.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mobile.jobsearchapplication.ui.screens.components.BottomNavigationBar
import com.mobile.jobsearchapplication.ui.screens.components.Header
import com.mobile.jobsearchapplication.ui.screens.components.MenuList
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
        shape = RoundedCornerShape(40.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent, // Xóa underline khi focus
            unfocusedIndicatorColor = Color.Transparent, // Xóa underline khi không focus
            disabledIndicatorColor = Color.Transparent
        )
    )
}

// ---------------

// hàm tạo tiêu đề cho từng section
@Composable
fun TitleSection(title: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Spacer(Modifier.weight(1f))

        Text(
            text = "Mở rộng",
            fontSize = 12.sp,
            modifier = Modifier.clickable {  }

        )
    }
}

// -------------- Việc làm theo nghề -----------------------
@Composable
fun JobCategorySection() {
    Column(modifier = Modifier.padding(16.dp)) {
        TitleSection("Việc làm theo nghề")

        LazyRow(modifier = Modifier.padding(top = 8.dp)) {
            val categories = listOf("Bán hàng", "Tạp vụ", "Giúp việc", "t", "t", "t", "t")

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
            modifier = Modifier.size(64.dp),
            color = Color.LightGray,
            shape = RoundedCornerShape(50)
        ) {
            // Placeholder cho hình ảnh
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = category, fontSize = 14.sp)
    }
}

// -------------- Việc dành cho bạn -----------------------
@Composable
fun RecommendedJobsList() {
    Column(modifier = Modifier.padding(16.dp)) {
        TitleSection("Việc dành cho bạn")

        LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
            val jobs = listOf("Lập trình mobile frontend", "Job 2", "Job 3")

            items(jobs.size) { index ->
                JobListItem(jobTitle = jobs[index])
            }
        }
    }
}

@Composable
fun JobListItem(jobTitle: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Surface(
                modifier = Modifier.size(64.dp),
                color = Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            ) {
                // Placeholder hình ảnh
            }

            Spacer(modifier = Modifier.width(16.dp))
            // content
            Column {
                Text(text = jobTitle, fontWeight = FontWeight.Bold, fontSize = 18.sp)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.AccessTime, contentDescription = "Clock", tint = Color.Gray, modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Today • 23 min", fontSize = 12.sp, color = Color.Gray)
                }

                Spacer(Modifier.height(4.dp))
                Text(text = "Mô tả công việc ngắnádasdasdasdasdjasgdjaghdgahjsdgjassgdjgsdjasgsjdgjjhagsdjagsjdgajsdgasgdjhagsjdhgajgdjasgdjasghdasjdgajsghdjagsdhagsjdgáhgdjas gọn...", fontSize = 12.sp, color = Color.Gray)

            }
        }
    }
}

