//package com.mobile.jobsearchapplication.ui.features.jobEdit
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavController
//import com.mobile.jobsearchapplication.data.model.BaseResponse
//import com.mobile.jobsearchapplication.data.model.job.Job
//import com.mobile.jobsearchapplication.data.model.job.JobDetails
//import java.math.BigDecimal
//
//@Composable
//fun JobEditScreen(jobId: String, navController: NavController) {
//    val viewModel: JobEditViewModel = viewModel()
//    val jobDetails by viewModel.jobDetails.observeAsState()
//    val isLoading by viewModel.isLoading.observeAsState(false)
//    val errorMessage by viewModel.errorMessage.observeAsState()
//    val updateSuccess by viewModel.updateSuccess.observeAsState()
//
//    LaunchedEffect(jobId) {
//        viewModel.fetchJobDetails(jobId)
//    }
//
//    when {
//        isLoading -> {
//            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                CircularProgressIndicator()
//            }
//        }
//        jobDetails != null -> {
//            // Convert JobDetails to JobDetailResponse<Job> for JobEditContent
//            val jobResponse = jobDetails?.let { details ->
//                BaseResponse(
//                    data = details.toJob(), // Map JobDetails to Job
//                    message = "Success"
//                )
//            }
//            if (jobResponse?.data != null) {
//                JobEditContent(
//                    jobDetails = jobResponse,
//                    onSave = { updatedDetails ->
//                        viewModel.updateJobPost(updatedDetails)
//                    },
//                    navController = navController
//                )
//            } else {
//                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                    Text("Không tìm thấy dữ liệu công việc")
//                }
//            }
//        }
//        errorMessage != null -> {
//            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                Text(errorMessage ?: "Đã xảy ra lỗi")
//            }
//        }
//    }
//
//    LaunchedEffect(updateSuccess) {
//        if (updateSuccess == true) {
//            navController.popBackStack()
//        }
//    }
//}
//
//@Composable
//fun JobEditContent(
//    jobDetails: BaseResponse<Job>,
//    onSave: (JobDetails) -> Unit,
//    navController: NavController
//) {
//    val job = jobDetails.data ?: return // Return early if job is null
//
//    var title by rememberSaveable { mutableStateOf(job.title) }
//    var description by rememberSaveable { mutableStateOf(job.description) }
//    var requirements by rememberSaveable { mutableStateOf(job.requirements ?: "") }
//    var benefits by rememberSaveable { mutableStateOf(job.benefits ?: "") }
//    var salaryMin by rememberSaveable { mutableStateOf(job.salaryMin?.toString() ?: "") }
//    var salaryMax by rememberSaveable { mutableStateOf(job.salaryMax?.toString() ?: "") }
//    var currency by rememberSaveable { mutableStateOf(job.currency) }
//    var employmentType by rememberSaveable { mutableStateOf(job.jobType) }
//    var experienceLevel by rememberSaveable { mutableStateOf(job.experienceLevel) }
//    var deadline by rememberSaveable { mutableStateOf(job.deadline ?: "") }
//    var positionsAvailable by rememberSaveable { mutableStateOf(job.quantity.toString()) }
//    var genderRequirement by rememberSaveable { mutableStateOf(job.genderRequire) }
//    var address by rememberSaveable { mutableStateOf(job.location) }
//
//    // Fields not in Job, using defaults or empty values
//    var isNegotiable by rememberSaveable { mutableStateOf(false) }
//    var city by rememberSaveable { mutableStateOf("") }
//    var district by rememberSaveable { mutableStateOf("") }
//    var isRemote by rememberSaveable { mutableStateOf(false) }
//    var minYears by rememberSaveable { mutableStateOf("0") }
//    var maxYears by rememberSaveable { mutableStateOf("0") }
//    var workingHours by rememberSaveable { mutableStateOf("") }
//    var overtimePolicy by rememberSaveable { mutableStateOf("") }
//    var probationPeriod by rememberSaveable { mutableStateOf("") }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//            .verticalScroll(rememberScrollState())
//    ) {
//        Text(
//            "Chỉnh sửa công việc",
//            fontWeight = FontWeight.Bold,
//            fontSize = 20.sp
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        OutlinedTextField(
//            value = title,
//            onValueChange = { title = it },
//            label = { Text("Tiêu đề") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        OutlinedTextField(
//            value = description,
//            onValueChange = { description = it },
//            label = { Text("Mô tả") },
//            modifier = Modifier.fillMaxWidth(),
//            minLines = 3
//        )
//
//        OutlinedTextField(
//            value = requirements,
//            onValueChange = { requirements = it },
//            label = { Text("Yêu cầu") },
//            modifier = Modifier.fillMaxWidth(),
//            minLines = 3
//        )
//
//        OutlinedTextField(
//            value = benefits,
//            onValueChange = { benefits = it },
//            label = { Text("Phúc lợi") },
//            modifier = Modifier.fillMaxWidth(),
//            minLines = 3
//        )
//
//        OutlinedTextField(
//            value = salaryMin,
//            onValueChange = { salaryMin = it },
//            label = { Text("Lương tối thiểu") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        OutlinedTextField(
//            value = salaryMax,
//            onValueChange = { salaryMax = it },
//            label = { Text("Lương tối đa") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        OutlinedTextField(
//            value = currency,
//            onValueChange = { currency = it },
//            label = { Text("Đơn vị tiền tệ") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Checkbox(
//                checked = isNegotiable,
//                onCheckedChange = { isNegotiable = it }
//            )
//            Text("Có thể thương lượng")
//        }
//
//        OutlinedTextField(
//            value = address,
//            onValueChange = { address = it },
//            label = { Text("Địa chỉ") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        OutlinedTextField(
//            value = city,
//            onValueChange = { city = it },
//            label = { Text("Thành phố") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        OutlinedTextField(
//            value = district,
//            onValueChange = { district = it },
//            label = { Text("Quận/Huyện") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Checkbox(
//                checked = isRemote,
//                onCheckedChange = { isRemote = it }
//            )
//            Text("Làm việc từ xa")
//        }
//
//        OutlinedTextField(
//            value = minYears,
//            onValueChange = { minYears = it },
//            label = { Text("Số năm kinh nghiệm tối thiểu") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        OutlinedTextField(
//            value = maxYears,
//            onValueChange = { maxYears = it },
//            label = { Text("Số năm kinh nghiệm tối đa") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        OutlinedTextField(
//            value = experienceLevel,
//            onValueChange = { experienceLevel = it },
//            label = { Text("Cấp độ kinh nghiệm") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        OutlinedTextField(
//            value = deadline,
//            onValueChange = { deadline = it },
//            label = { Text("Hạn chót") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        OutlinedTextField(
//            value = positionsAvailable,
//            onValueChange = { positionsAvailable = it },
//            label = { Text("Số vị trí") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        OutlinedTextField(
//            value = genderRequirement,
//            onValueChange = { genderRequirement = it },
//            label = { Text("Yêu cầu giới tính") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        OutlinedTextField(
//            value = workingHours,
//            onValueChange = { workingHours = it },
//            label = { Text("Giờ làm việc") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        OutlinedTextField(
//            value = overtimePolicy,
//            onValueChange = { overtimePolicy = it },
//            label = { Text("Chính sách tăng ca") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        OutlinedTextField(
//            value = probationPeriod,
//            onValueChange = { probationPeriod = it },
//            label = { Text("Thời gian thử việc") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(
//            onClick = {
//                onSave(
//                    JobDetails(
//                        companyId = job.companyId,
//                        title = title,
//                        description = description,
//                        requirements = requirements,
//                        benefits = benefits,
//                        postedBy = job.postedBy,
//                        categoryId = 1, // Placeholder; fetch from API or UI
//                        salaryMin = salaryMin.toBigDecimalOrNull() ?: job.salaryMin ?: BigDecimal.ZERO,
//                        salaryMax = salaryMax.toBigDecimalOrNull() ?: job.salaryMax ?: BigDecimal.ZERO,
//                        currency = currency,
//                        isNegotiable = isNegotiable,
//                        employmentType = employmentType,
//                        city = city,
//                        district = district,
//                        address = address,
//                        isRemote = isRemote,
//                        minYears = minYears.toIntOrNull() ?: 0,
//                        maxYears = maxYears.toIntOrNull() ?: 0,
//                        experienceLevel = experienceLevel,
//                        deadline = deadline,
//                        positionsAvailable = positionsAvailable.toIntOrNull() ?: job.quantity,
//                        genderRequirement = genderRequirement,
//                        status = job.status,
//                        workingHours = workingHours,
//                        overtimePolicy = overtimePolicy,
//                        probationPeriod = probationPeriod,
//                        jobImage = job.jobImage
//                    )
//                )
//            },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Lưu thay đổi")
//        }
//    }
//}
