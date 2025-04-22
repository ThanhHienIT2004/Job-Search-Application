package com.mobile.jobsearchapplication.ui.features.jobDetail

import android.icu.text.CaseMap.Title
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.data.repository.company.CompanyRepository
import com.mobile.jobsearchapplication.data.repository.user.UserRepository
import com.mobile.jobsearchapplication.ui.base.BaseScreen
import com.mobile.jobsearchapplication.ui.components.CustomInfoBox
import com.mobile.jobsearchapplication.ui.components.CustomSectionBox
import com.mobile.jobsearchapplication.ui.components.topBar.BackButton
import com.mobile.jobsearchapplication.ui.theme.LightPurple
import com.mobile.jobsearchapplication.data.model.job.Job

@Composable
fun JobDetailScreen(jobId: String, navController: NavController) {
    val viewModel: JobDetailViewModel = viewModel()

    LaunchedEffect(jobId) {
        viewModel.fetchJobDetail(jobId)
    }

    BaseScreen(
        actionsTop = {
            BackButton(navController, "home_screen")

            Spacer(modifier = Modifier.weight(1f)) // Đẩy icon sang phải

            IconButton(onClick = { /* tim */ }) {
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
    val userRepository = remember { UserRepository() }
    val companyRepository = remember { CompanyRepository() }
    var companyName by remember { mutableStateOf<String?>(null) }
    var postedByName by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(uiState) {
        if (uiState is JobDetailUiState.Success) {
            val postedByResponse = userRepository.getInfo(uiState.job.postedBy)
            postedByName = postedByResponse.data?.fullName ?: "Không rõ"

            val companyResponse = companyRepository.getCompanyDetail(uiState.job.companyId)
            companyName = companyResponse.data?.name ?: "Không rõ"
        }
    }
 

    when (uiState) {
        is JobDetailUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is JobDetailUiState.Success -> {
            val job = uiState.job

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
                            .height(280.dp)
                            .padding(bottom = 8.dp, top = 80.dp),
                        contentScale = ContentScale.Crop,
                        error = painterResource(id = R.drawable.error)
                    )
                    Text(
                        job.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth().padding(start = 8.dp)
                    )
                }

                items(
                    listOf(
                        Triple(Icons.Default.AttachMoney, "Salary", "${job.salaryMin ?: "N/A"} - ${job.salaryMax ?: "N/A"} ${job.currency}" to Pair(LightPurple, LightPurple)),
                        Triple(Icons.Default.Business, "Company", "Công ty: ${companyName ?: "Đang tải..."}" to Pair(Color.Gray, Color.Black)),
                        Triple(Icons.Default.Person, "Posted By", "Người đăng: ${postedByName ?: "Đang tải..."}" to Pair(Color.Gray, Color.Black)),
                        Triple(Icons.Default.LocationOn, "Location", job.location to Pair(Color.Gray, Color.Black)),
                        Triple(Icons.Default.Group, "Quantity", "Số lượng: ${job.quantity}" to Pair(Color.Gray, Color.Black)),
                        Triple(Icons.Default.Work, "Job Type", "Hình thức: ${job.jobType.toVietnameseJobType()}" to Pair(Color.Gray, Color.Black)),
                        Triple(Icons.Default.Badge, "Experience Level", "Kinh nghiệm: ${job.experienceLevel.toVietnameseExperience()}" to Pair(Color.Gray, Color.Black)),
                        Triple(Icons.Default.People, "Gender Requirement", "Giới tính: ${job.genderRequire.toVietnameseGender()}" to Pair(Color.Gray, Color.Black)),
                        Triple(Icons.Default.Verified, "Status", "Trạng thái: ${job.status.toVietnameseStatus()}" to Pair(Color.Gray, Color.Black)),
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

                items(
                    listOf(
                        "Mô tả công việc" to job.description,
                        "Yêu cầu" to job.requirements.orEmpty(),
                        "Quyền lợi" to job.benefits.orEmpty()
                    )
                ) { (title, content) ->
                    CustomSectionBox(title = title, content = content)
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

fun String.toVietnameseJobType(): String = when (this) {
    "FULL_TIME" -> "Toàn thời gian"
    "PART_TIME" -> "Bán thời gian"
    "CONTRACT" -> "Hợp đồng"
    "INTERNSHIP" -> "Thực tập"
    "FREELANCE" -> "Tự do"
    else -> "Không xác định"
}

fun String.toVietnameseExperience(): String = when (this) {
    "ENTRY" -> "Mới vào nghề"
    "MID_LEVEL" -> "Trung cấp"
    "SENIOR" -> "Cao cấp"
    "LEADER" -> "Trưởng nhóm"
    "MANAGER" -> "Quản lý"
    else -> "Không xác định"
}

fun String.toVietnameseGender(): String = when (this) {
    "MALE" -> "Nam"
    "FEMALE" -> "Nữ"
    "ANY" -> "Không yêu cầu"
    else -> "Không xác định"
}

fun String.toVietnameseGender1(): String = when (this) {
    "Male" -> "Nam"
    "Female" -> "Nữ"
    "Other" -> "Không xác định"
    else -> ""
}

fun String.toVietnameseStatus(): String = when (this) {
    "ACTIVE" -> "Đang hoạt động"
    "CLOSED" -> "Đã đóng"
    "DRAFT" -> "Bản nháp"
    "EXPIRED" -> "Hết hạn"
    else -> "Không xác định"
}
