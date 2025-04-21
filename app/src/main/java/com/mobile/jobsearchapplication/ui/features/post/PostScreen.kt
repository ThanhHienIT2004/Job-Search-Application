package com.mobile.jobsearchapplication.ui.features.post

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.ui.base.BaseScreen
import com.mobile.jobsearchapplication.ui.components.textField.post.CustomTextField
import com.mobile.jobsearchapplication.ui.components.textField.post.DropdownMenuField
import com.mobile.jobsearchapplication.ui.components.topBar.BackButton
import com.mobile.jobsearchapplication.ui.components.topBar.TitleTopBar
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PostScreen(navController: NavHostController, viewModel: PostViewModel = viewModel()) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }
    val jobPost = viewModel.jobPost
    val categories = remember { listOf(Pair(1, "Công nghệ thông tin"), Pair(2, "Kinh doanh"), Pair(3, "Kế toán")) } // Giả lập, thay bằng API

    LaunchedEffect(viewModel.postResult) {
        viewModel.postResult?.let { result ->
            isLoading = false
            result.onSuccess { message ->
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                navController.popBackStack() // Quay lại màn hình trước
            }.onFailure { exception ->
                Toast.makeText(context, exception.message ?: "Lỗi không xác định", Toast.LENGTH_SHORT).show()
            }
        }
    }

    BaseScreen(
        actionsTop = {
            BackButton(navController, "home_screen")

            TitleTopBar(
                text = "Đăng tin tuyển dụng",
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = "THÔNG TIN NHÀ TUYỂN DỤNG", fontSize = 16.sp, color = Color.Gray)

            Row(modifier = Modifier.padding(vertical = 8.dp)) {
                var selectedType by remember { mutableStateOf("Cá nhân") }
                Button(
                    onClick = { selectedType = "Cá nhân" },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedType == "Cá nhân") Color(0xFFFFA500) else Color.LightGray
                    )
                ) {
                    Text("Cá nhân", color = if (selectedType == "Cá nhân") Color.White else Color.Black)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { selectedType = "Công ty" },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedType == "Công ty") Color(0xFFFFA500) else Color.LightGray
                    )
                ) {
                    Text("Công ty", color = if (selectedType == "Công ty") Color.White else Color.Black)
                }
            }

            CustomTextField(
                label = "Thành phố",
                value = jobPost.location.city,
                onValueChange = { viewModel.jobPost = jobPost.copy(location = jobPost.location.copy(city = it)) }
            )
            CustomTextField(
                label = "Quận/Huyện",
                value = jobPost.location.district ?: "",
                onValueChange = { viewModel.jobPost = jobPost.copy(location = jobPost.location.copy(district = it)) }
            )
            CustomTextField(
                label = "Địa chỉ",
                value = jobPost.location.address,
                onValueChange = { viewModel.jobPost = jobPost.copy(location = jobPost.location.copy(address = it)) }
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = jobPost.location.isRemote,
                    onCheckedChange = { viewModel.jobPost = jobPost.copy(location = jobPost.location.copy(isRemote = it)) }
                )
                Text("Làm việc từ xa", modifier = Modifier.padding(start = 8.dp))
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clickable { /* Tích hợp chọn ảnh từ thư viện */ }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_image),
                    contentDescription = "Hình ảnh nơi làm việc"
                )
                Text(text = "Hình nơi làm việc", modifier = Modifier.align(Alignment.Center))
            }

            Text(
                text = "NỘI DUNG ĐĂNG TUYỂN",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 16.dp)
            )

            CustomTextField(
                label = "Tiêu đề tin đăng",
                value = jobPost.title,
                onValueChange = { viewModel.jobPost = jobPost.copy(title = it) }
            )

            DropdownMenuField(
                label = "Danh mục công việc",
                options = categories.map { it.second },
                selectedOption = categories.find { it.first == jobPost.categoryId }?.second ?: "",
                onOptionSelected = { selected ->
                    val categoryId = categories.find { it.second == selected }?.first ?: 0
                    viewModel.jobPost = jobPost.copy(categoryId = categoryId)
                }
            )

            CustomTextField(
                label = "Số lượng tuyển dụng",
                value = jobPost.positionsAvailable.toString(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                onValueChange = {
                    if (it.all { char -> char.isDigit() }) {
                        viewModel.jobPost = jobPost.copy(positionsAvailable = it.toIntOrNull() ?: 0)
                    }
                }
            )

            CustomTextField(
                label = "Mô tả công việc",
                value = jobPost.description,
                maxLines = 4,
                onValueChange = { viewModel.jobPost = jobPost.copy(description = it) }
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CustomTextField(
                    label = "Lương tối thiểu",
                    value = jobPost.salary.min.toString(),
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        if (it.all { char -> char.isDigit() || char == '.' }) {
                            viewModel.jobPost = jobPost.copy(salary = jobPost.salary.copy(min = it.toDoubleOrNull() ?: 0.0))
                        }
                    }
                )
                CustomTextField(
                    label = "Lương tối đa",
                    value = jobPost.salary.max.toString(),
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        if (it.all { char -> char.isDigit() || char == '.' }) {
                            viewModel.jobPost = jobPost.copy(salary = jobPost.salary.copy(max = it.toDoubleOrNull() ?: 0.0))
                        }
                    }
                )
            }

            DropdownMenuField(
                label = "Giới tính",
                options = listOf("Không yêu cầu", "Nam", "Nữ"),
                selectedOption = when (jobPost.genderRequirement) {
                    "MALE" -> "Nam"
                    "FEMALE" -> "Nữ"
                    else -> "Không yêu cầu"
                },
                onOptionSelected = {
                    viewModel.jobPost = jobPost.copy(
                        genderRequirement = when (it) {
                            "Nam" -> "MALE"
                            "Nữ" -> "FEMALE"
                            else -> "ANY"
                        }
                    )
                }
            )

            DropdownMenuField(
                label = "Trình độ học vấn",
                options = listOf("Không yêu cầu", "Trung cấp", "Cao đẳng", "Đại học"),
                selectedOption = jobPost.requirements,
                onOptionSelected = { viewModel.jobPost = jobPost.copy(requirements = it) }
            )

            DropdownMenuField(
                label = "Kinh nghiệm làm việc",
                options = listOf("Không yêu cầu", "Dưới 1 năm", "1-2 năm", "Trên 2 năm", "Trưởng nhóm"),
                selectedOption = when (jobPost.experience.level) {
                    "FRESH" -> "Không yêu cầu"
                    "INTERN" -> "Dưới 1 năm"
                    "JUNIOR" -> "1-2 năm"
                    "SENIOR" -> "Trên 2 năm"
                    "LEAD" -> "Trưởng nhóm"
                    else -> "Không yêu cầu"
                },
                onOptionSelected = {
                    val experience = when (it) {
                        "Không yêu cầu" -> JobPost.Experience(0, 0, "FRESH")
                        "Dưới 1 năm" -> JobPost.Experience(0, 1, "INTERN")
                        "1-2 năm" -> JobPost.Experience(1, 2, "JUNIOR")
                        "Trên 2 năm" -> JobPost.Experience(2, 5, "SENIOR")
                        "Trưởng nhóm" -> JobPost.Experience(5, 10, "LEAD")
                        else -> JobPost.Experience(0, 0, "FRESH")
                    }
                    viewModel.jobPost = jobPost.copy(experience = experience)
                }
            )
            DropdownMenuField(
                label = "Loại hình công việc",
                options = listOf("Toàn thời gian", "Bán thời gian", "Hợp đồng"),
                selectedOption = when (jobPost.employmentType) {
                    "FULL_TIME" -> "Toàn thời gian"
                    "PART_TIME" -> "Bán thời gian"
                    "CONTRACT" -> "Hợp đồng"
                    else -> "Toàn thời gian"
                },
                onOptionSelected = {
                    viewModel.jobPost = jobPost.copy(
                        employmentType = when (it) {
                            "Toàn thời gian" -> "FULL_TIME"
                            "Bán thời gian" -> "PART_TIME"
                            "Hợp đồng" -> "CONTRACT"
                            else -> "FULL_TIME"
                        }
                    )
                }
            )

            CustomTextField(
                label = "Chính sách tăng ca",
                value = jobPost.additionalInfo.overtimePolicy ?: "",
                onValueChange = {
                    viewModel.jobPost = jobPost.copy(
                        additionalInfo = jobPost.additionalInfo.copy(overtimePolicy = it)
                    )
                }
            )

            CustomTextField(
                label = "Thời gian thử việc",
                value = jobPost.additionalInfo.probationPeriod ?: "",
                onValueChange = {
                    viewModel.jobPost = jobPost.copy(
                        additionalInfo = jobPost.additionalInfo.copy(probationPeriod = it)
                    )
                }
            )

            CustomTextField(
                label = "Thông tin khác",
                value = jobPost.benefits ?: "",
                onValueChange = { viewModel.jobPost = jobPost.copy(benefits = it) }
            )

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { viewModel.submitPost() }, modifier = Modifier.fillMaxWidth()) {
                Text("Đăng tin")
            }
        }
    }
}