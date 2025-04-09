package com.mobile.jobsearchapplication.ui.features.home

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.data.model.job.Job
import com.mobile.jobsearchapplication.ui.components.bottomBar.BottomNavBarCustom
import com.mobile.jobsearchapplication.ui.components.PostItemList
import com.mobile.jobsearchapplication.ui.base.BaseScreen
import com.mobile.jobsearchapplication.ui.components.topBar.SearchButton
import com.mobile.jobsearchapplication.ui.theme.LightBlue
import com.mobile.jobsearchapplication.ui.features.job.JobUiState
import com.mobile.jobsearchapplication.ui.features.job.JobViewModel

@Composable
fun HomeScreen(navController: NavController, jobViewModel: JobViewModel = viewModel()) {
    BaseScreen(
        actionsTop = {
            Spacer(Modifier.weight(1f))
            SearchButton()
        },
        actionsBot = {
            BottomNavBarCustom(
                navController = navController
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            // Danh mục công việc theo nghề
            JobCategorySection(navController)

            when (val uiState = jobViewModel.uiState.collectAsState().value) {
                is JobUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is JobUiState.Success -> {
                    RecommendedJobsList(jobs = uiState.jobs, pageCount = uiState.pageCount, navController = navController)
                }
                is JobUiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = uiState.message, color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}

@Composable
fun RecommendedJobsList(jobs: List<Job>, pageCount: Int, navController: NavController) {
    Column {
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(jobs) { job ->
                JobItem(
                    job = job,
                    onClick = {
                        navController.navigate("job_detail_screen/${job.id}")
                    }
                )
            }
        }
    }
}

@Composable
fun JobItem(job: Job, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(165.dp)
            .padding(end = 8.dp)
            .clickable(onClick = onClick),  // Chỉ cần sử dụng onClick thay vì navController
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = LightBlue)
    ) {
        Column {
            AsyncImage(
                model = job.jobImage,
                contentDescription = "Job Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                contentScale = ContentScale.FillWidth,
                placeholder = painterResource(id = R.drawable.placeholder),  // Thêm placeholder nếu cần
                error = painterResource(id = R.drawable.error)  // Thêm image khi lỗi tải
            )

            Text(
                text = job.title,
                maxLines = 2,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp, top = 8.dp)  // Thêm padding trên
            )

            Text(
                text = job.location,
                fontSize = 11.sp,
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp)
            )

            Text(
                text = if (job.salaryMin != null && job.salaryMax != null) {
                    "${job.salaryMin} - ${job.salaryMax} ${job.currency}"
                } else {
                    "Salary not specified"
                },
                color = Color.Yellow,
                fontSize = 11.sp,
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp, bottom = 5.dp)
            )
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
            LazyRow{
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
            modifier = Modifier
                .size(60.dp)
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

