package com.mobile.jobsearchapplication.ui.features.application

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobile.jobsearchapplication.utils.FireBaseUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationScreen(
    jobId: String,
    onClose: () -> Unit,
    onExpand: (Boolean) -> Unit = {}
) {
    val viewModel: ApplicationViewModel = viewModel()
    val infoProfileState by viewModel.infoProfileState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val formState by viewModel.formState.collectAsState()
    val currentUserMail = FireBaseUtils.getCurrentUserEmail() ?: "N/A"

    // FocusRequester để chuyển focus giữa TextField
    val coverLetterFocusRequester = remember { FocusRequester() }
    val additionalInfoFocusRequester = remember { FocusRequester() }

    // Theo dõi trạng thái focus để mở rộng
    var isTextFieldFocused by remember { mutableStateOf(false) }

    // Chọn file PDF
    var selectedCvFileName by remember { mutableStateOf("No file selected") }
    val context = LocalContext.current
    val pickFileLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                viewModel.handleCvFileSelection(context, uri) { fileName ->
                    selectedCvFileName = fileName ?: "No file selected"
                }
            }
        }
    }

    // Hiển thị dialog kết quả
    when (uiState) {
        is ApplicationState.Loading -> {
            AlertDialog(
                onDismissRequest = {},
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surface),
                content = {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Đang gửi đơn ứng tuyển...",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            )
        }
        is ApplicationState.Success -> {
            AlertDialog(
                onDismissRequest = {
                    viewModel.resetApplicationState()
                    onClose()
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surface),
                content = {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Thành công",
                            tint = Color(0xFF4CAF50),
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Thành công!",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Đơn ứng tuyển của bạn đã được gửi thành công.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        TextButton(
                            onClick = {
                                viewModel.resetApplicationState()
                                onClose()
                            },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(
                                text = "Đóng",
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            )
        }
        is ApplicationState.Error -> {
            AlertDialog(
                onDismissRequest = { viewModel.resetApplicationState() },
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surface),
                content = {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = "Lỗi",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Đã có lỗi xảy ra",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = (uiState as ApplicationState.Error).message,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        TextButton(
                            onClick = { viewModel.resetApplicationState() },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(
                                text = "Thử lại",
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            )
        }
        is ApplicationState.Idle -> {
            // Không hiển thị dialog
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.surface)
            .navigationBarsPadding()
    ) {
        // Nội dung cuộn
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 100.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Nút đóng
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onClose() }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Đóng",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Text(
                text = "Hồ sơ ứng tuyển",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Thông tin ứng viên",
                style = MaterialTheme.typography.titleSmall,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Họ và tên:", color = Color.Gray)
                    Text(text = "Số điện thoại:", color = Color.Gray)
                    Text(text = "Email:", color = Color.Gray)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = infoProfileState.fullName.ifEmpty { "Chưa cập nhật" },
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = infoProfileState.phoneNumber.ifEmpty { "Chưa cập nhật" },
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = currentUserMail,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Lời nhắn gửi",
                style = MaterialTheme.typography.titleSmall,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = formState.coverLetter,
                onValueChange = { viewModel.updateCoverLetter(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .focusRequester(coverLetterFocusRequester)
                    .onFocusChanged { focusState ->
                        isTextFieldFocused = focusState.isFocused
                        onExpand(isTextFieldFocused)
                    },
                label = { Text("Viết lời nhắn gửi đến nhà tuyển dụng") },
                placeholder = { Text("Ví dụ: Tôi rất quan tâm đến vị trí này...") },
                maxLines = 8,
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = Color.LightGray,
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                enabled = uiState !is ApplicationState.Loading,
                isError = formState.coverLetter.isBlank(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { additionalInfoFocusRequester.requestFocus() }
                )
            )

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Thông tin thêm (nếu có)",
                style = MaterialTheme.typography.titleSmall,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = formState.additionalInfo,
                onValueChange = { viewModel.updateAdditionalInfo(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .focusRequester(additionalInfoFocusRequester)
                    .onFocusChanged { focusState ->
                        isTextFieldFocused = focusState.isFocused
                        onExpand(isTextFieldFocused)
                    },
                label = { Text("Thông tin bổ sung (không bắt buộc)") },
                placeholder = { Text("Ví dụ: Kỹ năng hoặc kinh nghiệm liên quan...") },
                maxLines = 8,
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = Color.LightGray,
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                enabled = uiState !is ApplicationState.Loading,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_GET_CONTENT)
                    intent.type = "application/pdf"
                    pickFileLauncher.launch(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                enabled = uiState !is ApplicationState.Loading
            ) {
                Text(
                    text = "Chọn file CV",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = selectedCvFileName,
                style = MaterialTheme.typography.bodyMedium,
                color = if (selectedCvFileName == "No file selected") Color.Gray else Color.Black
            )

            if (selectedCvFileName == "No file selected") {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Vui lòng chọn file CV",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Nút cố định ở dưới cùng
        val buttonAlpha by animateFloatAsState(
            targetValue = if (formState.coverLetter.isNotBlank() &&
                formState.cvFile != null &&
                uiState !is ApplicationState.Loading) 1f else 0.5f
        )
        Button(
            onClick = { viewModel.submitApplication(jobId) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .align(Alignment.BottomCenter)
                .alpha(buttonAlpha),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
            shape = RoundedCornerShape(12.dp),
            enabled = uiState !is ApplicationState.Loading &&
                    formState.coverLetter.isNotBlank() &&
                    formState.cvFile != null
        ) {
            if (uiState is ApplicationState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = "Nộp hồ sơ",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}