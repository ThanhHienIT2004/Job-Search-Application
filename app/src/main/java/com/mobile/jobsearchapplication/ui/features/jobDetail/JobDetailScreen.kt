package com.mobile.jobsearchapplication.ui.features.jobDetail

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.mobile.jobsearchapplication.ui.features.notification.NotificationViewModel
import com.mobile.jobsearchapplication.ui.theme.LightPurple
import com.mobile.jobsearchapplication.utils.FireBaseUtils
import com.mobile.jobsearchapplication.utils.FireBaseUtils.Companion.getCurrentUserName

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun JobDetailScreen(jobId: String, navController: NavController) {
    val viewModel: JobDetailViewModel = viewModel()
    val notificationViewModel: NotificationViewModel = viewModel()
    val context = LocalContext.current


    LaunchedEffect(jobId) {
        viewModel.fetchJobDetail(jobId)
    }

    BaseScreen(
        actionsTop = {
            BackButton(navController, "home_screen")

            Spacer(modifier = Modifier.weight(1f)) // Đẩy icon sang phải

            IconButton(onClick = {
                // Xử lý nhấn "tym"
                val uiState = viewModel.uiState.value
                when (uiState) {
                    is JobDetailUiState.Success -> {
                        val job = uiState.job
                        val applicantId = FireBaseUtils.getLoggedInUserId()
                        if (false) {
                            Toast.makeText(context, "Vui lòng đăng nhập để thích công việc", Toast.LENGTH_SHORT).show()
                            return@IconButton
                        }
                        if (applicantId == job.postedBy) {
                            Toast.makeText(context, "Bạn không thể thích bài của chính mình", Toast.LENGTH_SHORT).show()
                            return@IconButton
                        }
                        notificationViewModel.sendLikeNotification(
                            context = context,
                            applicantId = applicantId,
                            jobId = job.id.toString(), // job.id là String
                            jobTitle = job.title,
                            receiverId = job.postedBy,
                            notificationId = System.currentTimeMillis().toInt()
                        )
                        Toast.makeText(context, "Đã gửi thông báo đến người đăng", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(context, "Lỗi: Không thể tải thông tin công việc", Toast.LENGTH_SHORT).show()
                    }
                }
            }) {
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
    val currentUser = getCurrentUserName()
    val userRepository = remember { UserRepository() }
    val companyRepository = remember { CompanyRepository() }
    var companyName by remember { mutableStateOf<String?>(null) }

    var postedByName by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(uiState) {
        if (uiState is JobDetailUiState.Success) {
            val postedByResponse = userRepository.getInfo(uiState.job.postedBy)
            postedByName = postedByResponse.data?.fullName ?: "Khan rõ"

            val companyResponse = companyRepository.getCompanyDetail(uiState.job.companyId)
            companyName = companyResponse.data?.name ?: "Khan rõ"
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
