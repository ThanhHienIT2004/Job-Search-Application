package com.mobile.jobsearchapplication.ui.features.jobcategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.jobsearchapplication.data.model.jocategory.JobCategory
import com.mobile.jobsearchapplication.utils.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class JobCategoryViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<JobCategoryUiState>(JobCategoryUiState.Idle)
    val uiState: StateFlow<JobCategoryUiState> = _uiState

    init {
        fetchJobCategories()
    }

    fun fetchJobCategories() {
        viewModelScope.launch {
            _uiState.value = JobCategoryUiState.Loading
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.jobCategoryApiService.getAllJobCategories()
                }
                if (response.message == "Success" && response.data != null) {
                        _uiState.value = JobCategoryUiState.Success(response.data.data, response.data.pageCount)

                } else {
                    _uiState.value = JobCategoryUiState.Error(response.message ?: "Lỗi không xác định")
                }
            } catch (e: Exception) {
                _uiState.value = JobCategoryUiState.Error("Lỗi khi lấy danh mục công việc: ${e.message}")
            }
        }
    }
}

sealed class JobCategoryUiState {
    data object Idle : JobCategoryUiState() // Trạng thái ban đầu, chưa gọi API
    data object Loading : JobCategoryUiState()
    data class Success(val jobCategories: List<JobCategory>, val pageCount: Int) : JobCategoryUiState()
    data class Error(val message: String) : JobCategoryUiState()
}