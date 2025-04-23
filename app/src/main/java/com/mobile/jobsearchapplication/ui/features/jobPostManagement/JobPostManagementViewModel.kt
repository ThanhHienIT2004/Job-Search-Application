package com.mobile.jobsearchapplication.ui.features.jobPostManagement

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.jobsearchapplication.data.model.jobapplication.AppliedUserWithApplication
import com.mobile.jobsearchapplication.data.model.jobapplication.UpdateAppliedStatus
import com.mobile.jobsearchapplication.data.repository.jobApplication.JobApplicationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

sealed class JobPostManagementUiState {
    data object Loading : JobPostManagementUiState()
    data class Success(
        val appliedUsers: List<AppliedUserWithApplication>
    ) : JobPostManagementUiState()
    data class Error(val message: String) : JobPostManagementUiState()
}

class JobPostManagementViewModel: ViewModel() {
    private val jobApplicationRepository = JobApplicationRepository()

    private val _uiState = MutableStateFlow<JobPostManagementUiState>(JobPostManagementUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun loadAppliedUsers(jobId: String) {
        viewModelScope.launch {
            try {
                val response = jobApplicationRepository.getAppliedUsersByJobId(jobId)

                if (response.message == "Success") {
                    _uiState.value = when(val currentState = _uiState.value) {
                        is JobPostManagementUiState.Success -> currentState.copy(appliedUsers = response.data ?: emptyList())
                        else -> JobPostManagementUiState.Success(appliedUsers = response.data ?: emptyList())
                    }
                } else {
                    _uiState.value = JobPostManagementUiState.Error(response.message)
                }
            } catch (e: Exception) {
                _uiState.value = JobPostManagementUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun updateStatusAppliedJob(userId: String, jobId: UUID, status: String, context: Context) {
        viewModelScope.launch {
            try {
                val updateAppliedStatus = UpdateAppliedStatus(userId, jobId, status)
                val response = jobApplicationRepository.updateStatusAppliedJob(updateAppliedStatus)
                if (response.message == "Success") {
                    loadAppliedUsers(jobId.toString())
                    Toast.makeText(context, "Cập nhật trạng thái thành công", Toast.LENGTH_SHORT).show()
                } else {
                    _uiState.value = JobPostManagementUiState.Error(response.message)
                    Toast.makeText(context, "Cập nhật trạng thái thất bại", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                _uiState.value = JobPostManagementUiState.Error(e.message ?: "Unknown error")
                Toast.makeText(context, "Cập nhật trạng thái thất bại", Toast.LENGTH_SHORT).show()
            }
        }
    }

}