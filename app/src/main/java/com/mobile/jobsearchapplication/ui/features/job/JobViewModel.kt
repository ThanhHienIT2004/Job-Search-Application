package com.mobile.jobsearchapplication.ui.features.job

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.jobsearchapplication.data.model.job.Job
import com.mobile.jobsearchapplication.utils.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class JobViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<JobUiState>(JobUiState.Loading)
    val uiState: StateFlow<JobUiState> = _uiState

    init {
        fetchJobs()
    }

    fun fetchJobs() {
        viewModelScope.launch {
            _uiState.value = JobUiState.Loading
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.jobApiService.getJobs()
                }
                if (response.message == "Success" && response.data != null) {
                    _uiState.value = JobUiState.Success(response.data.data, response.data.pageCount)
                } else {
                    _uiState.value = JobUiState.Error(response.message)
                }
            } catch (e: Exception) {
                _uiState.value = JobUiState.Error("Error fetching jobs: ${e.message}")
            }
        }
    }
}

sealed class JobUiState {
    object Loading : JobUiState()
    data class Success(val jobs: List<Job>, val pageCount: Int) : JobUiState()
    data class Error(val message: String) : JobUiState()
}