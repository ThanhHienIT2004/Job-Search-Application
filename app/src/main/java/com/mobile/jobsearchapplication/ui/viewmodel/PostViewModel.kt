package com.mobile.jobsearchapplication.ui.viewmodel

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// ✅ ViewModel quản lý trạng thái của PostScreen
class PostViewModel : ViewModel() {
    var title by mutableStateOf("")
    var jobQuantity by mutableStateOf("")
    var salaryMin by mutableStateOf("")
    var salaryMax by mutableStateOf("")
    var description by mutableStateOf("")
    var gender by mutableStateOf("Không yêu cầu")
    var educationLevel by mutableStateOf("Không yêu cầu")
    var experience by mutableStateOf("Không yêu cầu")
    var additionalInfo by mutableStateOf("")

    fun submitPost() {
        // Xử lý logic đăng tin
    }
}
