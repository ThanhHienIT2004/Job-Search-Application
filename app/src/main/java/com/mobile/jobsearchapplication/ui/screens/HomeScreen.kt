package com.mobile.jobsearchapplication.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.data.remote.FirebaseRealtimeService

//import com.mobile.jobsearchapplication.data.model.User

import com.mobile.jobsearchapplication.ui.components.bottom_bar.BottomNavBarCustom

//import com.mobile.jobsearchapplication.ui.features.user.UserViewModel

import com.mobile.jobsearchapplication.ui.base.BaseScreen
import java.util.UUID

@Composable
fun HomeScreen(navController: NavController, viewModel: UserViewModel = viewModel()) {
    val image: Painter = painterResource(id = R.drawable.bg) // Thay bằng tên hình ảnh trong res/drawable
    BaseScreen(
        title = "Thông báo",
        showBackButton = true,
        onBackClick = { navController.popBackStack() },
        showSearch = true, // Bật icon tìm kiếm
        navController = navController,
        actionsBot = { BottomNavBarCustom(navController) }
    ) {
        // Gọi loadJobs để tải danh sách công việc từ Firebase
        LaunchedEffect(Unit) {
            viewModel.loadJobs()
        }

        Column(
            modifier = Modifier
                .paint(painter = image, contentScale = ContentScale.Crop)
                .fillMaxSize()
        ) {
            // Thêm thông tin người dùng
            AddUserScreen(viewModel)

            // Hiển thị danh sách công việc
            JobList(viewModel.jobs.value)
        }
    }
}

@Composable
fun AddUserScreen(viewModel: UserViewModel = viewModel()) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    val context = LocalContext.current
    val isSuccess = viewModel.isSuccess.value

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        JobList(viewModel.jobs.value)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val userId = UUID.randomUUID().toString()
                val user = User(id = userId, name = name, email = email)
                viewModel.submitUser(user)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Gửi dữ liệu")
        }

        isSuccess?.let {
            if (it) {
                Toast.makeText(context, "Gửi thành công", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Gửi thất bại", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

data class Job(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val location: String = "",
    val salaryMin: Double = 0.0,
    val salaryMax: Double = 0.0,
    val company: String = ""
)
data class User(
    val id: String = "",
    val name: String = "",
    val email: String = ""
)

@Composable
fun JobList(jobs: List<Job>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        items(jobs) { job ->
            JobItem(job)
        }
    }
}

@Composable
fun JobItem(job: Job) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { /* Handle click here */ },
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = job.title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = job.description)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Location: ${job.location}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Salary: ${job.salaryMin} - ${job.salaryMax}")
        }
    }
}
class JobViewModel(
    private val firebaseRealtimeService: FirebaseRealtimeService = FirebaseRealtimeService()
) : ViewModel() {

    private val _jobs = mutableStateOf<List<Job>>(emptyList())
    val jobs: State<List<Job>> = _jobs

    // Lấy danh sách công việc từ Firebase
    fun loadJobs() {
        firebaseRealtimeService.getJobs { jobsList ->
            _jobs.value = jobsList
        }
    }
}

class UserViewModel(
    private val firebaseRealtimeService: FirebaseRealtimeService = FirebaseRealtimeService()
) : ViewModel() {

    private val _isSuccess = mutableStateOf<Boolean?>(null)
    val isSuccess: State<Boolean?> = _isSuccess

    private val _jobs = mutableStateOf<List<Job>>(emptyList())
    val jobs: State<List<Job>> = _jobs

    fun submitUser(user: User) {
        firebaseRealtimeService.addUser(user) { success ->
            _isSuccess.value = success
        }
    }

    // Tải danh sách công việc từ Firebase
    fun loadJobs() {
        firebaseRealtimeService.getJobs { jobsList ->
            _jobs.value = jobsList
        }
    }
}


//
//// hàm tạo tiêu đề cho từng section
//@Composable
//fun TitleSection(title: String, isExpanded: Boolean, onExpandToggle: (Boolean) -> Unit) {
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Text(
//            text = title,
//            fontWeight = FontWeight.Bold,
//            fontSize = 18.sp
//        )
//
//        Spacer(Modifier.weight(1f))
//
//        Text(
//            text = if (isExpanded) "Thu gọn" else "Mở rộng",
//            fontSize = 12.sp,
//            modifier = Modifier.clickable { onExpandToggle(!isExpanded) }
//        )
//    }
//}
//
//// -------------- Việc làm theo nghề -----------------------
//@OptIn(ExperimentalLayoutApi::class)
//@Composable
//fun JobCategorySection(navController: NavController) {
//    var isExpanded by remember { mutableStateOf(false) }
//    var isCheckedIconJob by remember { mutableStateOf(false) }
//    var selectedJob by remember { mutableStateOf<String?>(null) }
//    val categories = listOf("Bán Hàng", "Tạp vụ", "Giúp việc", "t", "t", "t", "t", "t", "t")
//
//    Column(modifier = Modifier.padding(16.dp)) {
//        TitleSection("Việc làm theo nghề", isExpanded = isExpanded) {
//            isExpanded = it
//        }
//
//        // kiểm tra có nhấn mở rông hay không
//        if (isExpanded) {
//            FlowRow (
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceAround
//            ) {
//                categories.forEach { category ->
//                    JobCategoryItem(category = category, isCheckedIconJob) {
//                        isCheckedIconJob = it
//                    }
//                }
//            }
//        } else {
//            LazyRow(modifier = Modifier.padding(top = 8.dp)) {
//
//                items(categories.size) { index ->
//                    JobCategoryItem(category = categories[index], isCheckedIconJob) { isChecked ->
//                        isCheckedIconJob = isChecked
//                        selectedJob = if (isChecked) categories[index] else null
//                    }
//                }
//            }
//        }
//
//        // chuyển screen khi nhấn vào icon Job
//        if (isCheckedIconJob) {
//            navController.navigate("adv_job_search/${selectedJob ?: ""}") {
//                popUpTo(navController.graph.startDestinationId) { saveState = true }
//                launchSingleTop = true
//                restoreState = true
//            }
//        }
//    }
//}
//
//@Composable
//fun JobCategoryItem(category: String, isCheckedIconJob: Boolean, onToggleIconJob: (Boolean) -> Unit) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier.padding(8.dp)
//    ) {
//        Surface(
//            modifier = Modifier.size(60.dp)
//                .clickable { onToggleIconJob(!isCheckedIconJob) }
//            ,
//            color = Color.LightGray,
//            shape = RoundedCornerShape(50)
//        ) {
//            // Placeholder cho hình ảnh
//        }
//        Spacer(modifier = Modifier.height(4.dp))
//        Text(text = category, fontSize = 14.sp)
//    }
//}
//
//// -------------- Việc dành cho bạn -----------------------
//@Composable
//fun RecommendedJobsList(navController: NavController) {
//    var isExpanded by remember { mutableStateOf(false) }
//
//    Column(modifier = Modifier.padding(16.dp, 0.dp)) {
//        TitleSection("Việc dành cho bạn", isExpanded = isExpanded) {
//            isExpanded = it
//        }
//
//        LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
//            val jobs = listOf("Lập trình mobile frontend", "Job 2", "Job 3","job 4","job 5")
//
//            items(jobs.size) { index ->
//                PostItemList(navController, jobTitle = jobs[index])
//            }
//        }
//    }
//}
//
//
