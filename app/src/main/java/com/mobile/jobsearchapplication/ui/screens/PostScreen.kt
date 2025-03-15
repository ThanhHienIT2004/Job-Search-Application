package com.mobile.jobsearchapplication.ui.screens

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.mobile.jobsearchapplication.R
import com.mobile.jobsearchapplication.ui.screens.components.*
import com.mobile.jobsearchapplication.viewmodel.PostViewModel


@Composable
fun PostScreen(navController: NavHostController, viewModel: PostViewModel = viewModel()) {
    BaseScreen(
        "Đăng tin",
        true,
        onBackClick = { navController.navigate("home_screen")}
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

            // 🟢 Thông tin nhà tuyển dụng
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



            CustomTextField(label = "Tên hộ kinh doanh", value = "") {}

            CustomTextField(label = "Địa chỉ", value = "") {}

            Box(modifier = Modifier.fillMaxWidth().height(100.dp).clickable { /* Chọn ảnh */ }) {
                Image(painter = painterResource(id = R.drawable.ic_image), contentDescription = "Hình ảnh nơi làm việc")
                Text(text = "Hình nơi làm việc", modifier = Modifier.align(Alignment.Center))
            }

            // 🟢 Nội dung đăng tuyển
            Text(text = "NỘI DUNG ĐĂNG TUYỂN", fontSize = 16.sp, color = Color.Gray, modifier = Modifier.padding(top = 16.dp))

            CustomTextField(label = "Tiêu đề tin đăng", value = viewModel.title) { viewModel.title = it }


            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CustomTextField(
                    label = "Số lượng tuyển dụng",
                    value = viewModel.jobQuantity,
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number) // Bật bàn phím số
                ) {
                    if (it.all { char -> char.isDigit() }) { // Chỉ cho nhập số
                        viewModel.jobQuantity = it
                    }
                }
            }

            CustomTextField(label = "Mô tả công việc", value = viewModel.description, maxLines = 4) { viewModel.description = it }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CustomTextField(
                    label = "Lương tối thiểu",
                    value = viewModel.salaryMin,
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number) // Bật bàn phím số
                ) {
                    if (it.all { char -> char.isDigit() }) { // Chỉ cho nhập số
                        viewModel.salaryMin = it
                    }
                }

                CustomTextField(
                    label = "Lương tối đa",
                    value = viewModel.salaryMax,
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number) // Bật bàn phím số
                ) {
                    if (it.all { char -> char.isDigit() }) { // Chỉ cho nhập số
                        viewModel.salaryMax = it
                    }
                }
            }

            DropdownMenuField(label = "Giới tính", options = listOf("Không yêu cầu", "Nam", "Nữ"), selectedOption = viewModel.gender) { viewModel.gender = it }

            DropdownMenuField(label = "Trình độ học vấn", options = listOf("Không yêu cầu", "Trung cấp", "Cao đẳng", "Đại học"), selectedOption = viewModel.educationLevel) { viewModel.educationLevel = it }
            DropdownMenuField(label = "Kinh nghiệm làm việc", options = listOf("Không yêu cầu", "Dưới 1 năm", "1-2 năm", "Trên 2 năm"), selectedOption = viewModel.experience) { viewModel.experience = it }

            CustomTextField(label = "Thông tin khác", value = viewModel.additionalInfo) { viewModel.additionalInfo = it }

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { viewModel.submitPost() }, modifier = Modifier.fillMaxWidth()) {
                Text("Đăng tin")
            }
        }
    }
}