package com.mobile.jobsearchapplication.ui.features.jobCategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.jobsearchapplication.data.model.jobcategory.JobCategory
import com.mobile.jobsearchapplication.data.repository.jobCategory.JobCategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed class JobCategoryUiState {
    data object Loading : JobCategoryUiState()
    data class Success(val jobCategories: List<JobCategory>) : JobCategoryUiState()
    data class Error(val message: String) : JobCategoryUiState()
}

class JobCategoryViewModel : ViewModel() {
    private val jobCategoryRepository = JobCategoryRepository()

    private val _uiState = MutableStateFlow<JobCategoryUiState>(JobCategoryUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun loadJobCategories() {
        viewModelScope.launch {
            try {
                val response = jobCategoryRepository.getAllJobCategories("1", "20")
                if (response.message == "Success" && response.data?.data != null) {
                    _uiState.value = JobCategoryUiState.Success(response.data.data)
                } else {
                    _uiState.value = JobCategoryUiState.Error(response.message ?: "Lỗi không xác định")
                }
            } catch (e: Exception) {
                _uiState.value = JobCategoryUiState.Error("Lỗi khi lấy danh mục: ${e.message}")
            }
        }
    }
}