package com.mobile.jobsearchapplication.ui.screens

import android.hardware.lights.Light
import android.text.Layout
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
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Share
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.viewmodel.JobDetailUiState
import com.mobile.jobsearchapplication.viewmodel.JobUiState
import com.mobile.jobsearchapplication.viewmodel.JobViewModel

@Composable
fun JobDetailScreen(jobId: String, navController: NavController) {
    val viewModel: JobDetailViewModel = viewModel()

    LaunchedEffect(jobId) {
        viewModel.fetchJobDetail(jobId)
    }

    BaseScreen(
        showBackButton = true,
        onBackClick = { navController.popBackStack() },
        actionsTop = {
            Spacer(modifier = Modifier.weight(1f)) // Đẩy icon sang phải

            IconButton(onClick = { /* tim */ }) {
                Icon(Icons.Filled.Favorite, contentDescription = "Favorite", tint = Color.White)
            }
            IconButton(onClick = { /* chuông */ }) {
                Icon(Icons.Filled.Share, contentDescription = "Share", tint = Color.White)
            }
            ThreeDotsMenu()
        },
        actionsBot = { BottomActionBar() },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ){
            JobDetailContent(viewModel.uiState.collectAsState().value, navController)
        }
    }
}

@Composable
fun ThreeDotsMenu() {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Menu",
                tint = Color.White
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Chỉnh sửa") },
                onClick = {
                    expanded = false
                    // Xử lý chỉnh sửa
                }
            )
            DropdownMenuItem(
                text = { Text("Xoá") },
                onClick = {
                    expanded = false
                    // Xử lý xoá
                }
            )
            DropdownMenuItem(
                text = { Text("Báo cáo") },
                onClick = {
                    expanded = false
                    // Xử lý xoá
                }
            )
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
fun JobDetailContent(uiState: JobDetailUiState, navController: NavController) {
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
                        .padding(bottom = 80 .dp),
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
                            error = painterResource(id = R.drawable.error)//Ảnh nếu lội
                        )
                    }
                    item {
                        Text(
                            text = job.title,
                            fontSize = 16.sp,
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
                                tint = LightPurple,
                                modifier = Modifier
                                    .size(16.dp)
                            )
                            Text(
                                text = "${job.salaryMin ?: "N/A"} - ${job.salaryMax ?: "N/A"} ${job.currency}",
                                fontSize = 14.sp,
                                color = LightPurple
                            )
                        }
                    }
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
//                            AsyncImage(
//                                model = job.jobImage ?: R.drawable.placeholder,
//                                contentDescription = "User image",
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .height(200.dp)
//                                    .padding(bottom = 8.dp),
//                                contentScale = ContentScale.Crop,
//                                error = painterResource(id = R.drawable.error)//Ảnh nếu lội
//                            )
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
                                fontSize = 14.sp
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
                                fontSize = 14.sp
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
                                fontSize = 14.sp
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
                                fontSize = 14.sp
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
                                fontSize = 14.sp
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
                                fontSize = 14.sp
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
                                fontSize = 14.sp
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
                                fontSize = 14.sp
                            )
                        }
                    }
                    item {
                        Text(
                            text = "Mô tả công việc",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = job.description,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                    }
                    item {
                        if (!job.requirements.isNullOrEmpty()) {
                            Text(
                                text = "Yêu cầu",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = job.requirements,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                        }
                    }
                    item {
                        if (!job.benefits.isNullOrEmpty()) {
                            Text(
                                text = "Quyền lợi",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = job.benefits,
                                fontSize = 14.sp
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