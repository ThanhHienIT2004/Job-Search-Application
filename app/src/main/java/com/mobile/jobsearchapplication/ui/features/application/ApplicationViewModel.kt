package com.mobile.jobsearchapplication.ui.features.application

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.jobsearchapplication.data.model.jobapplication.JobApplication
import com.mobile.jobsearchapplication.data.repository.jobApplication.JobApplicationRepository
import com.mobile.jobsearchapplication.data.repository.user.UserRepository
import com.mobile.jobsearchapplication.ui.features.profile.InfoProfileState
import com.mobile.jobsearchapplication.utils.FireBaseUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

data class FormState(
    val coverLetter: String = "",
    val additionalInfo: String = "",
    val cvFile: File? = null
)

class ApplicationViewModel(
    private val userRepository: UserRepository = UserRepository(),
    private val jobApplicationRepository: JobApplicationRepository = JobApplicationRepository()
) : ViewModel() {
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
            val userId = FireBaseUtils.getLoggedInUserId() ?: return@launch
            try {
                val response = userRepository.getInfo(userId)
                if (response.isSuccess) {
                    val user = response.data
                    _infoProfileState.value = _infoProfileState.value.copy(
                        fullName = user?.fullName.orEmpty(),
                        phoneNumber = user?.phoneNumber.orEmpty(),
                        avatar = user?.avatar.orEmpty(),
                        bio = user?.bio.orEmpty(),
                        birthDay = user?.birthDay.orEmpty(),
                        gender = user?.gender.orEmpty()
                    )
                } else {
                    _uiState.value = ApplicationState.Error("Không thể tải thông tin người dùng")
                }
            } catch (e: Exception) {
                _uiState.value = ApplicationState.Error("Lỗi khi tải thông tin: ${e.message}")
            }
        }
    }

    fun updateCoverLetter(coverLetter: String) {
        _formState.value = _formState.value.copy(coverLetter = coverLetter)
    }

    fun updateAdditionalInfo(additionalInfo: String) {
        _formState.value = _formState.value.copy(additionalInfo = additionalInfo)
    }

    fun handleCvFileSelection(context: Context, uri: Uri, onFileNameUpdated: (String?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val tempFile = context.contentResolver.openInputStream(uri)?.use { input ->
                    File.createTempFile(
                        "cv_${System.currentTimeMillis()}",
                        ".pdf",
                        context.cacheDir
                    ).apply {
                        outputStream().use { output -> input.copyTo(output) }
                        deleteOnExit()
                    }
                }
                _formState.value = _formState.value.copy(cvFile = tempFile)
                withContext(Dispatchers.Main) {
                    onFileNameUpdated(tempFile?.name)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _uiState.value = ApplicationState.Error("Lỗi khi chọn file CV: ${e.message}")
                    onFileNameUpdated(null)
                }
            }
        }
    }

    fun submitApplication(jobId: String) {
        viewModelScope.launch {
            _uiState.value = ApplicationState.Loading
            try {
                val userId = FireBaseUtils.getLoggedInUserId()
                    ?: throw IllegalStateException("Người dùng chưa đăng nhập")
                val cvFile = _formState.value.cvFile
                    ?: throw IllegalStateException("Chưa chọn file CV")

                // Tạo RequestBody cho các trường text
                val jobIdBody = jobId.toRequestBody("text/plain".toMediaTypeOrNull())
                val userIdBody = userId.toRequestBody("text/plain".toMediaTypeOrNull())
                val statusBody = "PENDING".toRequestBody("text/plain".toMediaTypeOrNull())
                val coverLetterBody = _formState.value.coverLetter.toRequestBody("text/plain".toMediaTypeOrNull())
                val additionalInfoBody = _formState.value.additionalInfo.toRequestBody("text/plain".toMediaTypeOrNull())

                // Tạo MultipartBody.Part cho file CV
                val cvRequestBody = cvFile.asRequestBody("application/pdf".toMediaTypeOrNull())
                val cvPart = MultipartBody.Part.createFormData("cv", cvFile.name, cvRequestBody)

                // Gửi yêu cầu
                val response = withContext(Dispatchers.IO) {
                    jobApplicationRepository.submitApplication(
                        jobId = jobIdBody,
                        userId = userIdBody,
                        status = statusBody,
                        coverLetter = coverLetterBody,
                        additionalInfo = additionalInfoBody,
                        cv = cvPart
                    )
                }

                if (response.isSuccessful && response.body()?.message == "Success") {
                    _uiState.value = ApplicationState.Success
                } else {
                    _uiState.value = ApplicationState.Error("Lỗi khi gửi ứng tuyển: ${response.message()}")
                }
            } catch (e: Exception) {
                _uiState.value = ApplicationState.Error("Lỗi khi gửi ứng tuyển: ${e.message}")
            }
        }
    }

    fun resetApplicationState() {
        _uiState.value = ApplicationState.Idle
        _formState.value = FormState()
    }
}

sealed class ApplicationState {
    data object Idle : ApplicationState()
    data object Loading : ApplicationState()
    data object Success : ApplicationState()
    data class Error(val message: String) : ApplicationState()
}