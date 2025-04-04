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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mobile.jobsearchapplication.ui.components.bottom_bar.BottomNavBarCustom
import com.mobile.jobsearchapplication.ui.components.PostItemList
import com.mobile.jobsearchapplication.ui.features.user.UserViewModel
import com.mobile.jobsearchapplication.ui.base.BaseScreen

@Composable
fun HomeScreen(navController: NavController, viewModel: UserViewModel = viewModel()) {
    BaseScreen(
        actionsTop = {
            SearchBar(
                navController = navController,
                onMenuClicked = {
                    println("Menu clicked")
                }
            )
        },
        actionsBot = { BottomNavBarCustom(navController) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Danh mục công việc theo nghề
            JobCategorySection(navController)

            // Danh sách việc làm gợi ý
            RecommendedJobsList(navController)
        }
    }
}

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
    val categories = listOf("Bán Hàng", "Tạp vụ", "Giúp việc", "t", "t", "t", "t", "t", "t")

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
            navController.navigate("adv_job_search/${selectedJob ?: ""}") {
                popUpTo(navController.graph.startDestinationId) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
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
fun RecommendedJobsList(navController: NavController) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp, 0.dp)) {
        TitleSection("Việc dành cho bạn", isExpanded = isExpanded) {
            isExpanded = it
        }

        LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
            val jobs = listOf("Lập trình mobile frontend", "Job 2", "Job 3","job 4","job 5")

            items(jobs.size) { index ->
                PostItemList(navController, jobTitle = jobs[index])
            }
        }
    }
}


