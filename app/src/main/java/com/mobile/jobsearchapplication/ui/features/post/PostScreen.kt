package com.mobile.jobsearchapplication.ui.features.post

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
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
    val categories = remember {
        listOf(
            Pair(3, "Giúp việc"),
            Pair(4, "Kinh doanh"),
            Pair(5, "Marketing"),
            Pair(6, "Y tế"),
            Pair(7, "Giáo dục"),
            Pair(8, "Xây dựng"),
            Pair(9, "Thiết kế đồ họa"),
            Pair(10, "Nhân sự"),
            Pair(11, "Vận tải"),
            Pair(12, "Du lịch"),
            Pair(13, "Nhà hàng - Khách sạn"),
            Pair(14, "Nông nghiệp"),
            Pair(15, "Bán lẻ"),
            Pair(16, "Tài chính - Ngân hàng"),
            Pair(17, "Thời trang"),
            Pair(18, "Truyền thông"),
            Pair(19, "Kỹ thuật"),
            Pair(20, "Luật"),
            Pair(1, "Công nghệ thông tin"),
            Pair(2, "Kế toán")
        )
    }

    LaunchedEffect(viewModel.postResult) {
        viewModel.postResult?.let { result ->
            isLoading = false
            result.onSuccess { message ->
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }.onFailure { exception ->
                Toast.makeText(context, exception.message ?: "Lỗi không xác định", Toast.LENGTH_SHORT).show()
            }
        }
    }

    BaseScreen(
        actionsTop = {
            BackButton(navController, "home_screen")
            TitleTopBar(text = "Đăng tin tuyển dụng")
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Phần thông tin nhà tuyển dụng
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "THÔNG TIN NHÀ TUYỂN DỤNG",
                        fontSize = 18.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    CustomTextField(
                        label = buildAnnotatedString {
                            append("Thành phố")
                            withStyle(style = SpanStyle(color = Color.Red)) { append(" *") }
                        },
                        value = jobPost.location.city,
                        onValueChange = { viewModel.jobPost = jobPost.copy(location = jobPost.location.copy(city = it)) }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    CustomTextField(
                        label = buildAnnotatedString {
                            append("Quận/Huyện")
                        },
                        value = jobPost.location.district ?: "",
                        onValueChange = { viewModel.jobPost = jobPost.copy(location = jobPost.location.copy(district = it)) }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    CustomTextField(
                        label = buildAnnotatedString {
                            append("Địa chỉ")
                            withStyle(style = SpanStyle(color = Color.Red)) { append(" *") }
                        },
                        value = jobPost.location.address,
                        onValueChange = { viewModel.jobPost = jobPost.copy(location = jobPost.location.copy(address = it)) }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = jobPost.location.isRemote,
                            onCheckedChange = { viewModel.jobPost = jobPost.copy(location = jobPost.location.copy(isRemote = it)) }
                        )
                        Text("Làm việc từ xa", modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Phần nội dung đăng tuyển
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "NỘI DUNG ĐĂNG TUYỂN",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    CustomTextField(
                        label = buildAnnotatedString {
                            append("URL ảnh công việc")
                        },
                        value = jobPost.additionalInfo.jobImage ?: "",
                        onValueChange = {
                            viewModel.jobPost = jobPost.copy(
                                additionalInfo = jobPost.additionalInfo.copy(jobImage = it.ifBlank { null })
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    CustomTextField(
                        label = buildAnnotatedString {
                            append("Tiêu đề tin đăng")
                            withStyle(style = SpanStyle(color = Color.Red)) { append(" *") }
                        },
                        value = jobPost.title,
                        onValueChange = { viewModel.jobPost = jobPost.copy(title = it) }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DropdownMenuField(
                        label = buildAnnotatedString {
                            append("Danh mục công việc")
                            withStyle(style = SpanStyle(color = Color.Red)) { append(" *") }
                        }.toString(),
                        options = categories.map { it.second },
                        selectedOption = categories.find { it.first == jobPost.categoryId }?.second ?: "",
                        onOptionSelected = { selected ->
                            val categoryId = categories.find { it.second == selected }?.first ?: 0
                            viewModel.jobPost = jobPost.copy(categoryId = categoryId)
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    CustomTextField(
                        label = buildAnnotatedString {
                            append("Số lượng tuyển dụng")
                            withStyle(style = SpanStyle(color = Color.Red)) { append(" *") }
                        },
                        value = jobPost.positionsAvailable.toString(),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        onValueChange = {
                            if (it.all { char -> char.isDigit() }) {
                                viewModel.jobPost = jobPost.copy(positionsAvailable = it.toIntOrNull() ?: 0)
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    CustomTextField(
                        label = buildAnnotatedString {
                            append("Mô tả công việc")
                            withStyle(style = SpanStyle(color = Color.Red)) { append(" *") }
                        },
                        value = jobPost.description,
                        maxLines = 4,
                        onValueChange = { viewModel.jobPost = jobPost.copy(description = it) }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CustomTextField(
                            label = buildAnnotatedString {
                                append("Lương tối thiểu")
                                withStyle(style = SpanStyle(color = Color.Red)) { append(" *") }
                            },
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
                            label = buildAnnotatedString {
                                append("Lương tối đa")
                                withStyle(style = SpanStyle(color = Color.Red)) { append(" *") }
                            },
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

                    Spacer(modifier = Modifier.height(12.dp))

                    DropdownMenuField(
                        label = buildAnnotatedString {
                            append("Giới tính")
                        }.toString(),
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

                    Spacer(modifier = Modifier.height(12.dp))

                    DropdownMenuField(
                        label = buildAnnotatedString {
                            append("Trình độ học vấn")
                        }.toString(),
                        options = listOf("Không yêu cầu", "Trung cấp", "Cao đẳng", "Đại học"),
                        selectedOption = jobPost.requirements,
                        onOptionSelected = { viewModel.jobPost = jobPost.copy(requirements = it) }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DropdownMenuField(
                        label = buildAnnotatedString {
                            append("Kinh nghiệm làm việc")
                        }.toString(),
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

                    Spacer(modifier = Modifier.height(12.dp))

                    DropdownMenuField(
                        label = buildAnnotatedString {
                            append("Loại hình công việc")
                        }.toString(),
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

                    Spacer(modifier = Modifier.height(12.dp))

                    CustomTextField(
                        label = buildAnnotatedString {
                            append("Giờ làm việc")
                            withStyle(style = SpanStyle(color = Color.Red)) { append(" *") }
                        },
                        value = jobPost.additionalInfo.workingHours,
                        onValueChange = {
                            viewModel.jobPost = jobPost.copy(
                                additionalInfo = jobPost.additionalInfo.copy(workingHours = it)
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    CustomTextField(
                        label = buildAnnotatedString {
                            append("Chính sách tăng ca")
                        },
                        value = jobPost.additionalInfo.overtimePolicy ?: "",
                        onValueChange = {
                            viewModel.jobPost = jobPost.copy(
                                additionalInfo = jobPost.additionalInfo.copy(overtimePolicy = it)
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    CustomTextField(
                        label = buildAnnotatedString {
                            append("Thời gian thử việc")
                        },
                        value = jobPost.additionalInfo.probationPeriod ?: "",
                        onValueChange = {
                            viewModel.jobPost = jobPost.copy(
                                additionalInfo = jobPost.additionalInfo.copy(probationPeriod = it)
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    CustomTextField(
                        label = buildAnnotatedString {
                            append("Thông tin khác")
                        },
                        value = jobPost.benefits ?: "",
                        onValueChange = { viewModel.jobPost = jobPost.copy(benefits = it) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    isLoading = true
                    viewModel.submitPost()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500)),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text("Đăng tin", fontSize = 16.sp, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
@Composable
fun CustomTextField(
    label: AnnotatedString, // Đổi từ String sang AnnotatedString
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    maxLines: Int = 1
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) }, // Sử dụng AnnotatedString trực tiếp
        modifier = modifier,
        keyboardOptions = keyboardOptions,
        maxLines = maxLines,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color(0xFFFFA500),
            unfocusedIndicatorColor = Color.Gray
        )
    )
}