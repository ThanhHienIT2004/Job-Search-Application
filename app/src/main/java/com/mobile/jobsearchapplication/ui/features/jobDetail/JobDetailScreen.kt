package com.mobile.jobsearchapplication.ui.features.jobDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.ui.base.BaseScreen
import com.mobile.jobsearchapplication.ui.components.CustomInfoBox
import com.mobile.jobsearchapplication.ui.components.CustomSectionBox
import com.mobile.jobsearchapplication.ui.components.topBar.BackButton
import com.mobile.jobsearchapplication.ui.features.job.JobUiState
import com.mobile.jobsearchapplication.ui.features.job.JobViewModel
import com.mobile.jobsearchapplication.ui.features.job.RecommendedJobsList
import com.mobile.jobsearchapplication.ui.theme.LightPurple

@Composable
fun JobDetailScreen(jobId: String, navController: NavController) {
    val viewModel: JobDetailViewModel = viewModel()

    LaunchedEffect(jobId) {
        viewModel.fetchJobDetail(jobId)
    }

    BaseScreen(
        actionsTop = {
            BackButton(navController)
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { /* Favorite */ }) {
                Icon(Icons.Filled.Favorite, contentDescription = "Favorite", tint = Color.White)
            }
            IconButton(onClick = { /* Share */ }) {
                Icon(Icons.Filled.Share, contentDescription = "Share", tint = Color.White)
            }
            ThreeDotsMenu()
        },
        actionsBot = { BottomActionBar(navController, jobId) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            JobDetailContent(viewModel.uiState.collectAsState().value, navController)
        }
    }
}

@Composable
fun JobDetailContent(uiState: JobDetailUiState, navController: NavController) {
    when (uiState) {
        is JobDetailUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is JobDetailUiState.Success -> {
            uiState.job.let { job ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 80.dp)
                ) {
                    item {
                        AsyncImage(
                            model = job.jobImage ?: R.drawable.placeholder,
                            contentDescription = "Job Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(bottom = 8.dp),
                            contentScale = ContentScale.Crop,
                            error = painterResource(id = R.drawable.error)
                        )
                    }
                    // Nhóm thông tin công việc thành danh sách
                    items(
                        listOf(
                            Triple(Icons.Default.AttachMoney, "Salary", "${job.salaryMin ?: "N/A"} - ${job.salaryMax ?: "N/A"} ${job.currency}" to Pair(LightPurple, LightPurple)),
                            Triple(Icons.Default.Business, "Company", "Company ID: ${job.companyId}" to Pair(Color.Gray, Color.Black)),
                            Triple(Icons.Default.Person, "Posted By", "Posted By: ${job.postedBy}" to Pair(Color.Gray, Color.Black)),
                            Triple(Icons.Default.LocationOn, "Location", job.location to Pair(Color.Gray, Color.Black)),
                            Triple(Icons.Default.Work, "Job Type", job.jobType to Pair(Color.Gray, Color.Black)),
                            Triple(Icons.Default.Badge, "Experience Level", job.experienceLevel to Pair(Color.Gray, Color.Black)),
                            Triple(Icons.Default.Group, "Quantity", "Số lượng: ${job.quantity}" to Pair(Color.Gray, Color.Black)),
                            Triple(Icons.Default.People, "Gender Requirement", "Giới tính: ${job.genderRequire}" to Pair(Color.Gray, Color.Black)),
                            Triple(Icons.Default.Verified, "Status", "Trạng thái: ${job.status}" to Pair(Color.Gray, Color.Black)),
                            Triple(Icons.Default.AccessTime, "Created At", "Đăng ngày: ${job.createdAt}" to Pair(Color.Gray, Color.Gray)),
                        )
                    ) { (icon, desc, textAndColors) ->
                        val (text, colors) = textAndColors
                        CustomInfoBox(
                            icon = icon,
                            contentDescription = desc,
                            text = text,
                            iconTint = colors.first,
                            textColor = colors.second
                        )
                    }
                    // Deadline nếu có
                    if (!job.deadline.isNullOrEmpty()) {
                        item {
                            CustomInfoBox(
                                icon = Icons.Default.CalendarMonth,
                                contentDescription = "Deadline",
                                text = "Hạn nộp: ${job.deadline}",
                                textColor = Color.Gray
                            )
                        }
                    }
                    // Các phần mô tả, yêu cầu, quyền lợi
                    items(
                        listOf(
                            "Mô tả công việc" to job.description,
                            "Yêu cầu" to job.requirements.orEmpty(),
                            "Quyền lợi" to job.benefits.orEmpty()
                        )
                    ) { (title, content) ->
                        CustomSectionBox(title = title, content = content)
                    }
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Việc tương tự",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        val jobViewModel: JobViewModel = viewModel()
                        when (val uiState = jobViewModel.uiState.collectAsState().value) {
                            is JobUiState.Loading -> {
                                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                    CircularProgressIndicator()
                                }
                            }
                            is JobUiState.Success -> {
                                uiState.jobs?.let {
                                    RecommendedJobsList(
                                        jobVM = jobViewModel,
                                        jobs = it,
                                        navController = navController
                                    )
                                }
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
        }
        is JobDetailUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = uiState.message,
                    color = Color.Red,
                    fontSize = 14.sp
                )
            }
        }
        is JobDetailUiState.Idle -> {
            // Không hiển thị gì
        }
    }
}