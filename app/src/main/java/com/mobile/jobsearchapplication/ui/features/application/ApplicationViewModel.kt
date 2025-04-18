package com.mobile.jobsearchapplication.ui.features.application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.jobsearchapplication.data.model.jobapplication.JobApplication
import com.mobile.jobsearchapplication.data.repository.user.UserRepository
import com.mobile.jobsearchapplication.ui.features.profile.InfoProfileState
import com.mobile.jobsearchapplication.utils.FireBaseUtils
import com.mobile.jobsearchapplication.utils.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

data class FormState(
    val coverLetter: String = "",
    val additionalInfo: String = ""
)

class ApplicationViewModel : ViewModel() {
    private val userRepository = UserRepository()
    private val _infoProfileState = MutableStateFlow(InfoProfileState())
    val infoProfileState = _infoProfileState.asStateFlow()

    private val _uiState = MutableStateFlow<ApplicationState>(ApplicationState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _formState = MutableStateFlow(FormState())
    val formState = _formState.asStateFlow()

    init {
        getUserForApply()
    }

    private fun getUserForApply() {
        viewModelScope.launch(Dispatchers.Main) {
            val userId = FireBaseUtils.getLoggedInUserId()
            val response = userRepository.getInfo(userId)
            if (response.isSuccess) {
                val user = response.data
                _infoProfileState.value = _infoProfileState.value.copy(
                    fullName = user?.fullName.orEmpty(),
                    phoneNumber = user?.phoneNumber.orEmpty(),
                    avatar = user?.avatar.orEmpty(),
                    bio = user?.bio.orEmpty(),
                    birthDay = user?.birthDay.orEmpty(),
                    gender = user?.gender.orEmpty(),
                )
            } else {
                _uiState.value = ApplicationState.Error("Không thể tải thông tin người dùng")
            }
        }
    }

    fun updateCoverLetter(coverLetter: String) {
        _formState.value = _formState.value.copy(coverLetter = coverLetter)
    }

    fun updateAdditionalInfo(additionalInfo: String) {
        _formState.value = _formState.value.copy(additionalInfo = additionalInfo)
    }

    fun submitApplication(jobId: String) {
        viewModelScope.launch {
            _uiState.value = ApplicationState.Loading
            try {
                val userId = FireBaseUtils.getLoggedInUserId() ?: throw IllegalStateException("Người dùng chưa đăng nhập")

                val application = JobApplication(
                    userId = userId, // Điều chỉnh nếu API yêu cầu userId khác
                    jobId = jobId,
                    status = "PENDING",
                    coverLetter = _formState.value.coverLetter,
                    cvUrl = "https://example.com/my-cv.pdf",
                    additionalInfo = _formState.value.additionalInfo
                )

                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.jobApplicationApiService.submitApplication(application)
                }
                if (response.message == "Success" && response.data != null) {
                    _uiState.value = ApplicationState.Success
                } else {
                    _uiState.value = ApplicationState.Error("Lỗi khi gửi ứng tuyển: ${response.message}")
                }
            } catch (e: Exception) {
                _uiState.value = ApplicationState.Error("Lỗi khi gửi ứng tuyển: ${e.message}")
            }
        }
    }

    fun resetApplicationState() {
        _uiState.value = ApplicationState.Idle
    }
}

sealed class ApplicationState {
    data object Idle : ApplicationState()
    data object Loading : ApplicationState()
    data object Success : ApplicationState()
    data class Error(val message: String) : ApplicationState()
}