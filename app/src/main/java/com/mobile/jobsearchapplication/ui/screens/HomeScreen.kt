package com.mobile.jobsearchapplication.ui.screens

import android.health.connect.datatypes.units.Length
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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.mobile.jobsearchapplication.model.Job
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
        JobCategorySection(navController)

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
fun TitleSection(title: String, isExpanded: Boolean, onExpandToggle: (Boolean) -> Unit) {
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
            text = if (isExpanded) "Thu gọn" else "Mở rộng",
            fontSize = 12.sp,
            modifier = Modifier.clickable { onExpandToggle(!isExpanded) }
        )
    }
}

// -------------- Việc làm theo nghề -----------------------
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JobCategorySection(navController: NavController) {
    var isExpanded by remember { mutableStateOf(false) }
    var isCheckedIconJob by remember { mutableStateOf(false) }
    var selectedJob by remember { mutableStateOf<String?>(null) }
    val categories = listOf("Bán hàng", "Tạp vụ", "Giúp việc", "t", "t", "t", "t", "t", "t")

    Column(modifier = Modifier.padding(16.dp)) {
        TitleSection("Việc làm theo nghề", isExpanded = isExpanded) {
            isExpanded = it
        }

        // kiểm tra có nhấn mở rông hay không
        if (isExpanded) {
            FlowRow (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                categories.forEach { category ->
                    JobCategoryItem(category = category, isCheckedIconJob) {
                        isCheckedIconJob = it
                    }
                }
            }
        } else {
            LazyRow(modifier = Modifier.padding(top = 8.dp)) {

                items(categories.size) { index ->
                    JobCategoryItem(category = categories[index], isCheckedIconJob) { isChecked ->
                        isCheckedIconJob = isChecked
                        selectedJob = if (isChecked) categories[index] else null
                    }
                }
            }
        }

        // chuyển screen khi nhấn vào icon Job
        if (isCheckedIconJob) {
            navController.navigate("adv_job_search/${selectedJob ?: ""}")
        }
    }
}

@Composable
fun JobCategoryItem(category: String, isCheckedIconJob: Boolean, onToggleIconJob: (Boolean) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Surface(
            modifier = Modifier.size(60.dp)
                .clickable { onToggleIconJob(!isCheckedIconJob) }
            ,
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
    var isExpanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        TitleSection("Việc dành cho bạn", isExpanded = isExpanded) {
            isExpanded = it
        }

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
        Row(modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                // title
                Text(text = jobTitle, fontWeight = FontWeight.Bold, fontSize = 18.sp)

                // AccessTime
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.AccessTime, contentDescription = "Clock", tint = Color.Gray, modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Today • 23 min", fontSize = 12.sp, color = Color.Gray)
                }

                // salary
                Spacer(Modifier.height(4.dp))
                Text("Lương: 1000000 - 12000000", fontSize = 14.sp)

                // describe
                Spacer(Modifier.height(4.dp))
                ExpandableText("Mô tả công việc ngắnádasdasdasdasdjasgdjaghdgahjsdgjassgdjgsdjasgsjdgjjhagsdjagsjdgajsdgasgdjhagsjdhgajgdjasgdjasghdasjdgajsghdjagsdhagsjdgáhgdjas gọn...")

                // button
                ButtonForJobItem()
            }
        }
    }
}

// Giới hạn kí tự trong mô tả
@Composable
fun ExpandableText(text: String, maxLength: Int = 50) {
    var expanded by remember { mutableStateOf(false) }

    val displayText = if (expanded || text.length <= maxLength) text else  text.take(maxLength)

    Column {
        Text (
            text = displayText, fontSize = 12.sp
        )
    }

    if (text.length > maxLength) {
        Text(
            text = if (expanded) "Thu gọn" else "Xem thêm", fontSize = 12.sp,
            modifier = Modifier.clickable { expanded = !expanded }
        )
    }
}

// Các nút trong bài đăng
@Composable
fun ButtonForJobItem() {
    var isCheckedFav by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {isCheckedFav = !isCheckedFav}) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "FavoriteButton",
                tint = if (isCheckedFav) Color.Red else Color.White
            )
        }

        IconButton(onClick = {
        }) {
            Icon(
                imageVector = Icons.Filled.Send,
                contentDescription = "Xem chi tiết công việc",
                tint = Color.Blue
            )
        }

    }
}

