package com.mobile.jobsearchapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.mobile.jobsearchapplication.ui.base.BaseScreen
import com.mobile.jobsearchapplication.ui.theme.LightBlue
import com.mobile.jobsearchapplication.ui.theme.LightPurple
import com.mobile.jobsearchapplication.viewmodel.JobDetailViewModel
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.viewmodel.JobDetailUiState

@Composable
fun JobDetailScreen(jobId: String, navController: NavHostController) {
    val viewModel: JobDetailViewModel = viewModel()
    
    LaunchedEffect(jobId) {
        viewModel.fetchJobDetail(jobId)
    }
    
    BaseScreen(
        showBackButton = true,
        onBackClick = { navController.popBackStack() },
        actionsBot = { BottomActionBar() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ){
            JobDetailContent(viewModel.uiState.collectAsState().value)
        }
    }
}

@Composable
fun BottomActionBar() {
    Surface(
        color = LightBlue,
        shape = RoundedCornerShape(32.dp),
        shadowElevation = 50.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(LightPurple),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Nút gọi
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable { /* TODO: Xử lý gọi */ }
                    .padding(horizontal = 10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "Gọi",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Gọi",
                    fontSize = 12.sp,
                    color = Color.White
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
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Chat",
                    fontSize = 12.sp,
                    color = Color.White
                )
            }

            // Nút ứng tuyển
            Button(
                onClick = { /* TODO: Xử lý ứng tuyển */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF6F61)
                ),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .height(48.dp)
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = "Ứng tuyển",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun JobDetailContent(uiState: JobDetailUiState) {
    when (uiState) {
        is JobDetailUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is JobDetailUiState.Success -> {
            uiState.job.let { job ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        AsyncImage(
                            model = job.jobImage ?: R.drawable.placeholder,
                            contentDescription = "Job Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentScale = ContentScale.Crop,
                            error = painterResource(id = R.drawable.error)
                        )
                    }
                    item {
                        Text(
                            text = job.title,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AttachMoney,
                                contentDescription = "Salary",
                                tint = Color.Red
                            )
                            Text(
                                text = "${job.salaryMin ?: "N/A"} - ${job.salaryMax ?: "N/A"} ${job.currency}",
                                fontSize = 18.sp,
                                color = Color.Red,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Job ID",
                                tint = Color.Gray
                            )
                            Text(
                                text = "ID: ${job.id}",
                                fontSize = 16.sp
                            )
                        }
                    }
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Business,
                                contentDescription = "Company",
                                tint = Color.Gray
                            )
                            Text(
                                text = "Company ID: ${job.companyId}",
                                fontSize = 16.sp
                            )
                        }
                    }
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Posted By",
                                tint = Color.Gray
                            )
                            Text(
                                text = "Posted By: ${job.postedBy}",
                                fontSize = 16.sp
                            )
                        }
                    }
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Location",
                                tint = Color.Gray
                            )
                            Text(
                                text = job.location,
                                fontSize = 16.sp
                            )
                        }
                    }
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Work,
                                contentDescription = "Job Type",
                                tint = Color.Gray
                            )
                            Text(
                                text = job.jobType,
                                fontSize = 16.sp
                            )
                        }
                    }
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Badge,
                                contentDescription = "Experience Level",
                                tint = Color.Gray
                            )
                            Text(
                                text = job.experienceLevel,
                                fontSize = 16.sp
                            )
                        }
                    }
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Group,
                                contentDescription = "Quantity",
                                tint = Color.Gray
                            )
                            Text(
                                text = "Số lượng: ${job.quantity}",
                                fontSize = 16.sp
                            )
                        }
                    }
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.People,
                                contentDescription = "Gender Requirement",
                                tint = Color.Gray
                            )
                            Text(
                                text = "Giới tính: ${job.genderRequire}",
                                fontSize = 16.sp
                            )
                        }
                    }
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = "Status",
                                tint = Color.Gray
                            )
                            Text(
                                text = "Trạng thái: ${job.status}",
                                fontSize = 16.sp
                            )
                        }
                    }
                    item {
                        Text(
                            text = "Mô tả công việc",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = job.description,
                            fontSize = 16.sp
                        )
                    }
                    item {
                        if (!job.requirements.isNullOrEmpty()) {
                            Text(
                                text = "Yêu cầu",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = job.requirements,
                                fontSize = 16.sp
                            )
                        }
                    }
                    item {
                        if (!job.benefits.isNullOrEmpty()) {
                            Text(
                                text = "Quyền lợi",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = job.benefits,
                                fontSize = 16.sp
                            )
                        }
                    }
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccessTime,
                                contentDescription = "Created At",
                                tint = Color.Gray
                            )
                            Text(
                                text = "Đăng ngày: ${job.createdAt}",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                    item {
                        if (!job.deadline.isNullOrEmpty()) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CalendarMonth,
                                    contentDescription = "Deadline",
                                    tint = Color.Gray
                                )
                                Text(
                                    text = "Hạn nộp: ${job.deadline}",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
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
                    fontSize = 16.sp
                )
            }
        }
        is JobDetailUiState.Idle -> {
            // Không hiển thị gì
        }
    }
}