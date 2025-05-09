package com.mobile.jobsearchapplication.ui.features.post

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.mobile.jobsearchapplication.ui.base.BaseScreen
import com.mobile.jobsearchapplication.ui.components.textField.post.DropdownMenuField
import com.mobile.jobsearchapplication.ui.components.topBar.BackButton
import com.mobile.jobsearchapplication.ui.components.topBar.TitleTopBar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PostScreen(
    navController: NavHostController,
    viewModel: PostViewModel = viewModel()
) {
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
                Toast.makeText(
                    context,
                    exception.message ?: "Lỗi không xác định",
                    Toast.LENGTH_SHORT
                ).show()
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

            SectionInfoRecruiter(jobPost, viewModel)

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
                        selectedOption = categories.find { it.first == jobPost.categoryId }?.second
                            ?: "",
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
                            value = jobPost.salaryMin.toString(),
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                            onValueChange = {
                                if (it.all { char -> char.isDigit() || char == '.' }) {
                                    viewModel.jobPost = jobPost.copy(salaryMin = it.toDoubleOrNull() ?: 0.0)
                                }
                            }
                        )
                        CustomTextField(
                            label = buildAnnotatedString {
                                append("Lương tối đa")
                                withStyle(style = SpanStyle(color = Color.Red)) { append(" *") }
                            },
                            value = jobPost.salaryMax.toString(),
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                            onValueChange = {
                                if (it.all { char -> char.isDigit() || char == '.' }) {
                                    viewModel.jobPost = jobPost.copy(salaryMax = it.toDoubleOrNull() ?: 0.0)
                                }
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    DropdownMenuField(
                        label = buildAnnotatedString {
                            append("Đơn vị tiền tệ")
                            withStyle(style = SpanStyle(color = Color.Red)) { append(" *") }
                        }.toString(),
                        options = listOf("VND", "USD", "EUR"),
                        selectedOption = jobPost.currency,
                        onOptionSelected = { viewModel.jobPost = jobPost.copy(currency = it) }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DropdownMenuField(
                        label = buildAnnotatedString {
                            append("Chu kỳ lương")
                            withStyle(style = SpanStyle(color = Color.Red)) { append(" *") }
                        }.toString(),
                        options = listOf("Giờ", "Ngày", "Tuần", "Tháng", "Năm"),
                        selectedOption = when (jobPost.salaryPeriod) {
                            "HOURLY" -> "Giờ"
                            "DAILY" -> "Ngày"
                            "WEEKLY" -> "Tuần"
                            "MONTHLY" -> "Tháng"
                            "YEARLY" -> "Năm"
                            else -> "Tháng"
                        },
                        onOptionSelected = {
                            viewModel.jobPost = jobPost.copy(
                                salaryPeriod = when (it) {
                                    "Giờ" -> "HOURLY"
                                    "Ngày" -> "DAILY"
                                    "Tuần" -> "WEEKLY"
                                    "Tháng" -> "MONTHLY"
                                    "Năm" -> "YEARLY"
                                    else -> "MONTHLY"
                                }
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DropdownMenuField(
                        label = buildAnnotatedString {
                            append("Giới tính")
                            withStyle(style = SpanStyle(color = Color.Red)) { append(" *") }
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
                            withStyle(style = SpanStyle(color = Color.Red)) { append(" *") }
                        }.toString(),
                        options = listOf("Không yêu cầu", "1-2 năm", "3-5 năm", "Trên 5 năm", "Quản lý"),
                        selectedOption = when (jobPost.experienceLevel) {
                            "ENTRY" -> "Không yêu cầu"
                            "MID_LEVEL" -> "1-2 năm"
                            "SENIOR" -> "3-5 năm"
                            "LEADER" -> "Trên 5 năm"
                            "MANAGER" -> "Quản lý"
                            else -> "Không yêu cầu"
                        },
                        onOptionSelected = {
                            viewModel.jobPost = jobPost.copy(
                                experienceLevel = when (it) {
                                    "Không yêu cầu" -> "ENTRY"
                                    "1-2 năm" -> "MID_LEVEL"
                                    "3-5 năm" -> "SENIOR"
                                    "Trên 5 năm" -> "LEADER"
                                    "Quản lý" -> "MANAGER"
                                    else -> "ENTRY"
                                }
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DropdownMenuField(
                        label = buildAnnotatedString {
                            append("Loại hình công việc")
                            withStyle(style = SpanStyle(color = Color.Red)) { append(" *") }
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
                                additionalInfo = jobPost.additionalInfo.copy(
                                    overtimePolicy = it.ifBlank { null }
                                )
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
                                additionalInfo = jobPost.additionalInfo.copy(
                                    probationPeriod = it.ifBlank { null }
                                )
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    CustomTextField(
                        label = buildAnnotatedString {
                            append("Thông tin khác")
                        },
                        value = jobPost.benefits ?: "",
                        onValueChange = { viewModel.jobPost = jobPost.copy(benefits = it.ifBlank { null }) }
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SectionInfoRecruiter(
    jobPost: JobPost,
    viewModel: PostViewModel
) {
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
                value = jobPost.location.split(", ").getOrNull(0) ?: "",
                onValueChange = { city ->
                    val parts = jobPost.location.split(", ")
                    val newLocation = listOf(
                        city,
                        parts.getOrNull(1) ?: "",
                        parts.getOrNull(2) ?: ""
                    ).filter { it.isNotBlank() }.joinToString(", ")
                    viewModel.jobPost = jobPost.copy(location = newLocation)
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(
                label = buildAnnotatedString {
                    append("Quận/Huyện")
                },
                value = jobPost.location.split(", ").getOrNull(1) ?: "",
                onValueChange = { district ->
                    val parts = jobPost.location.split(", ")
                    val newLocation = listOf(
                        parts.getOrNull(0) ?: "",
                        district,
                        parts.getOrNull(2) ?: ""
                    ).filter { it.isNotBlank() }.joinToString(", ")
                    viewModel.jobPost = jobPost.copy(location = newLocation)
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(
                label = buildAnnotatedString {
                    append("Địa chỉ")
                    withStyle(style = SpanStyle(color = Color.Red)) { append(" *") }
                },
                value = jobPost.location.split(", ").getOrNull(2) ?: "",
                onValueChange = { address ->
                    val parts = jobPost.location.split(", ")
                    val newLocation = listOf(
                        parts.getOrNull(0) ?: "",
                        parts.getOrNull(1) ?: "",
                        address
                    ).filter { it.isNotBlank() }.joinToString(", ")
                    viewModel.jobPost = jobPost.copy(location = newLocation)
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = jobPost.location.contains("(Từ xa)"),
                    onCheckedChange = { isRemote ->
                        val newLocation = if (isRemote) {
                            "${jobPost.location} (Từ xa)".trim()
                        } else {
                            jobPost.location.replace("(Từ xa)", "").trim()
                        }
                        viewModel.jobPost = jobPost.copy(location = newLocation)
                    }
                )
                Text("Làm việc từ xa", modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}

@Composable
fun CustomTextField(
    label: AnnotatedString,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    maxLines: Int = 1
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = keyboardOptions,
        maxLines = maxLines,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedIndicatorColor = Color(0xFFEFAF39),
            unfocusedIndicatorColor = Color.Gray
        )
    )
}