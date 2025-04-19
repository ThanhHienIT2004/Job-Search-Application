package com.mobile.jobsearchapplication.ui.features.jobDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.jobsearchapplication.data.model.job.Job
import com.mobile.jobsearchapplication.utils.RetrofitClient
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
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.jobApiService.getJobDetail(jobId)
                }
                if (response.message == "Success") {
                    _uiState.value = response.data?.let { JobDetailUiState.Success(it) }!!
                } else {
                    _uiState.value =
                        JobDetailUiState.Error(response.message ?: "Lỗi không xác định")
                }
            } catch (e: Exception) {
                _uiState.value =
                    JobDetailUiState.Error("Lỗi khi lấy chi tiết công việc: ${e.message}")
            }
        }
    }
}

sealed class JobDetailUiState {
    data object Idle : JobDetailUiState() // Trạng thái ban đầu, chưa gọi API
    data object Loading : JobDetailUiState()
    data class Success(val job: Job) : JobDetailUiState()
    data class Error(val message: String) : JobDetailUiState()
}