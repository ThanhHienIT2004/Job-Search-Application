package com.mobile.jobsearchapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

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