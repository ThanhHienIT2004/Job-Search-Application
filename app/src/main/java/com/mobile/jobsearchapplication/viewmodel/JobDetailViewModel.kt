package com.mobile.jobsearchapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.jobsearchapplication.data.model.Job
import com.mobile.jobsearchapplication.network.ApiClient
import com.mobile.jobsearchapplication.network.responses.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class JobDetailViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<JobDetailUiState>(JobDetailUiState.Idle)
    val uiState: StateFlow<JobDetailUiState> = _uiState

    fun fetchJobDetail(jobId: String) {
        viewModelScope.launch {
            _uiState.value = JobDetailUiState.Loading
            try {
                val response = withContext(Dispatchers.IO) {
                    ApiClient.jobApiService.getJobDetail(jobId)
                }
                if (response.message == "Success") {
                    // Giả sử response.data là một Job đơn lẻ, không phải PaginatedData
                    _uiState.value = JobDetailUiState.Success(response.data)
                } else {
                    _uiState.value = JobDetailUiState.Error(response.message ?: "Lỗi không xác định")
                }
            } catch (e: Exception) {
                _uiState.value = JobDetailUiState.Error("Lỗi khi lấy chi tiết công việc: ${e.message}")
            }
        }
    }
}

sealed class JobDetailUiState {
    object Idle : JobDetailUiState() // Trạng thái ban đầu, chưa gọi API
    object Loading : JobDetailUiState()
    data class Success(val job: Job) : JobDetailUiState()
    data class Error(val message: String) : JobDetailUiState()
}