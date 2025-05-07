package com.mobile.jobsearchapplication.ui.features.jobDetail

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.data.repository.company.CompanyRepository
import com.mobile.jobsearchapplication.data.repository.user.UserRepository
import com.mobile.jobsearchapplication.ui.base.BaseScreen
import com.mobile.jobsearchapplication.ui.components.topBar.BackButton
import com.mobile.jobsearchapplication.ui.features.job.JobUiState
import com.mobile.jobsearchapplication.ui.features.job.JobViewModel
import com.mobile.jobsearchapplication.utils.FireBaseUtils.Companion.isUserLoggedIn
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun JobDetailScreen(jobId: String, navController: NavController) {
    val context = LocalContext.current
    val viewModel: JobDetailViewModel = viewModel()
    val jobVM: JobViewModel = viewModel()
    val jobUiState by jobVM.uiState.collectAsState()

    // Gọi API lấy chi tiết công việc
    LaunchedEffect(jobId) {
        viewModel.fetchJobDetail(jobId)
        jobVM.loadFavoriteJobs()
    }
    // Đặt isFavorite là mutableStateOf để có thể cập nhật được
    var isFavorite by rememberSaveable { mutableStateOf(false) }

    if (jobUiState is JobUiState.Success) {
        isFavorite = (jobUiState as JobUiState.Success).favoriteJobs?.contains(UUID.fromString(jobId)) == true
    }

    BaseScreen(
        actionsTop = {
            BackButton(navController, "home_screen")
            Spacer(modifier = Modifier.weight(1f)) // Đẩy icon sang phải

            Icon(
                imageVector = if (isFavorite) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "Icon Favorite",
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        if (!isUserLoggedIn()) {
                            Toast.makeText(context, "Vui lòng đăng nhập để lưu công việc", Toast.LENGTH_SHORT).show()
                            return@clickable
                        }
                        isFavorite = !isFavorite
                        jobVM.updateFavoriteApi(jobId = jobId, state = isFavorite)
                    },
                tint = if (isFavorite) Color.Red else Color.Gray,
            )

            IconButton(onClick = { /* Share */ }) {
                Icon(Icons.Filled.Share, contentDescription = "Share", tint = Color.White)
            }
            ThreeDotsMenu(navController, jobId)
        },
        actionsBot = { BottomActionBar(navController, viewModel ) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            JobDetailContentModern(viewModel.uiState.collectAsState().value, navController)
        }
    }
}

@Composable
fun JobDetailContentModern(uiState: JobDetailUiState, navController: NavController) {
    val userRepository = remember { UserRepository() }
    val companyRepository = remember { CompanyRepository() }
    var companyName by remember { mutableStateOf<String?>(null) }
    var postedByName by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(uiState) {
        if (uiState is JobDetailUiState.Success) {
            companyName = companyRepository.getCompanyDetail(uiState.job.companyId).data?.name
            postedByName = userRepository.getInfo(uiState.job.postedBy).data?.fullName
        }
    }

    when (uiState) {
        is JobDetailUiState.Success -> {
            val job = uiState.job
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF9F9F9))
                    .padding(bottom = 80.dp)
            ) {
                item {
                    Box {
                        AsyncImage(
                            model = job.jobImage ?: R.drawable.placeholder,
                            contentDescription = "Job Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .padding(top = 80.dp)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(Color.Transparent, Color(0xAA000000)),
                                        startY = 100f
                                    )
                                ),
                            contentAlignment = Alignment.BottomStart
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = job.title,
                                    color = Color.White,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = companyName ?: "",
                                    color = Color.LightGray,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }

                item { InfoCard(icon = Icons.Default.AttachMoney, title = "Mức lương", value = "${job.salaryMin} - ${job.salaryMax} ${job.currency}/ ${job.salaryPeriod.toVietnamesePeriod()}") }
                item { InfoCard(icon = Icons.Default.Person, title = "Người đăng", value = postedByName ?: "") }
                item { InfoCard(icon = Icons.Default.Work, title = "Hình thức", value = job.jobType.toVietnameseJobType()) }
                item { InfoCard(icon = Icons.Default.Badge, title = "Kinh nghiệm", value = job.experienceLevel.toVietnameseExperience()) }
                item { InfoCard(icon = Icons.Default.People, title = "Giới tính", value = job.genderRequire.toVietnameseGender()) }
                item { InfoCard(Icons.Default.Group, "Số lượng", "${job.quantity}") }
                item { InfoCard(Icons.Default.Verified, "Trạng thái", job.status.toVietnameseStatus()) }
                item { InfoCard(Icons.Default.AccessTime, "Ngày đăng", job.createdAt) }
                if (!job.deadline.isNullOrEmpty()) {
                    item { InfoCard(Icons.Default.CalendarMonth, "Hạn nộp", job.deadline ?: "") }
                }
                item { InfoCard(Icons.Default.LocationOn, "Địa điểm", job.location) }
                item {
                    DetailSectionCard(title = "Mô tả công việc", content = job.description)
                }
                item {
                    DetailSectionCard(title = "Yêu cầu", content = job.requirements.orEmpty())
                }
                item {
                    DetailSectionCard(title = "Quyền lợi", content = job.benefits.orEmpty())
                }

            }
        }

        is JobDetailUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is JobDetailUiState.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Lỗi: ${uiState.message}", color = Color.Red)
            }
        }

        else -> {}
    }
}

@Composable
fun InfoCard(icon: ImageVector, title: String, value: String) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(icon, contentDescription = title, tint = Color(0xFF4A5BCB), modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(title, fontSize = 14.sp, color = Color.Gray)
                Text(value, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
fun DetailSectionCard(title: String, content: String) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = content, fontSize = 14.sp)
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
fun String.toVietnamesePeriod(): String = when (this) {
    "MONTHLY" -> "Tháng"
    "WEEKLY" -> "Tuần"
    "DAYLY" -> "Ngày"
    else -> ""
}
fun String.toVietnameseGender1(): String = when (this) {
    "Male" -> "Nam"
    "Female" -> "Nữ"
    "Other" -> "Không xác định"
    else -> ""
}

fun String.toVietnameseStatus(): String = when (this) {
    "ACTIVE" -> "Còn tuyển"
    "CLOSED" -> "Đã đóng"
    "DRAFT" -> "Bản nháp"
    "EXPIRED" -> "Hết hạn"
    else -> "Không xác định"
}
