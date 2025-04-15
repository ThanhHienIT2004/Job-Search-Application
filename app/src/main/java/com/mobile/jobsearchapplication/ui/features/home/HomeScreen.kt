package com.mobile.jobsearchapplication.ui.features.home

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.data.model.job.Job
import com.mobile.jobsearchapplication.ui.components.bottomBar.BottomNavBarCustom
import com.mobile.jobsearchapplication.ui.base.BaseScreen
import com.mobile.jobsearchapplication.ui.components.topBar.SearchButton
import com.mobile.jobsearchapplication.ui.features.job.JobUiState
import com.mobile.jobsearchapplication.ui.features.job.JobViewModel
import com.mobile.jobsearchapplication.ui.features.jobcategory.JobCategoryViewModel
import com.mobile.jobsearchapplication.ui.features.jobcategory.JobCategoryUiState
import com.mobile.jobsearchapplication.data.model.jobcategory.JobCategory
import com.mobile.jobsearchapplication.ui.theme.LightBlue

@Composable
fun HomeScreen(
    navController: NavController,
    jobViewModel: JobViewModel = viewModel(),
    jobCategoryViewModel: JobCategoryViewModel = viewModel() // Thêm JobCategoryViewModel
) {
    BaseScreen(
        actionsTop = {
            Spacer(Modifier.weight(1f))
            SearchButton()
        },
        actionsBot = {
            BottomNavBarCustom(navController = navController)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            // Tiêu đề "Việc làm theo ngành nghề"
            Text(
                text = "Việc làm theo ngành nghề",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )

            // Hiển thị danh sách ngành nghề
            when (val uiState = jobCategoryViewModel.uiState.collectAsState().value) {
                is JobCategoryUiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is JobCategoryUiState.Success -> {
                    JobCategoryList(
                        jobCategories = uiState.jobCategories,
                        navController = navController,
                    )

                    // Thêm nút "Xem tất cả  ngành nghề"
                    Text(
                        text = "Xem tất cả ${uiState.jobCategories.size} ngành nghề",
                        color = LightBlue,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
//                                navController.navigate("all_job_categories")
                            },
                        textAlign = TextAlign.Center
                    )
                }
                is JobCategoryUiState.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = uiState.message,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
                is JobCategoryUiState.Idle -> {
                    // Không làm gì hoặc hiển thị placeholder
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Danh sách công việc đề xuất
            when (val uiState = jobViewModel.uiState.collectAsState().value) {
                is JobUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is JobUiState.Success -> {
                    RecommendedJobsList(
                        jobs = uiState.jobs,
                        pageCount = uiState.pageCount,
                        navController = navController
                    )
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
         Text("Việc làm dành cho bạn", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 6.dp))
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

@Composable
fun JobCategoryList(jobCategories: List<JobCategory>, navController: NavController) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(jobCategories) { category ->
            JobCategoryItem(
                category = category,
                onClick = {
                    // Chuyển hướng đến màn hình hiển thị công việc theo ngành nghề
                    navController.navigate("jobs_by_category/${category.id}")
                }
            )
        }
    }
}

@Composable
fun JobCategoryItem(category: JobCategory, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .width(60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Hiển thị biểu tượng ngành nghề
        AsyncImage(
            model = category.imageUrl, // Sử dụng imageUrl từ JobCategory
            contentDescription = category.name,
            modifier = Modifier
                .size(50.dp)
                .background(Color.LightGray, shape = CircleShape),
            placeholder = painterResource(id = R.drawable.placeholder),
            error = painterResource(id = R.drawable.error)
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Hiển thị tên ngành nghề
        Text(
            text = category.name,
            fontSize = 12.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}