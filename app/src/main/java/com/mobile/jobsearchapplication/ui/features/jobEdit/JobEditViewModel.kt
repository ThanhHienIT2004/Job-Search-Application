//package com.mobile.jobsearchapplication.ui.features.job
//
//import android.content.Context
//import androidx.lifecycle.viewModelScope
//import com.mobile.jobsearchapplication.data.model.job.Job
//import com.mobile.jobsearchapplication.data.repository.job.JobRepository
//import com.mobile.jobsearchapplication.ui.base.BaseViewModel
//import com.mobile.jobsearchapplication.ui.components.textField.account.TextFieldModel
//import com.mobile.jobsearchapplication.utils.FireBaseUtils.Companion.getLoggedInUserId
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//import java.math.BigDecimal
//import java.util.UUID
//
//data class JobPostState(
//    val job: Job? = null,
//    val isLoading: Boolean = false,
//    val isUpdateSuccess: Boolean = false,
//    val errorMessage: String? = null
//)
//
//class EditJobPostViewModel : BaseViewModel() {
//    private val jobRepository = JobRepository()
//
//    private val _jobPostState = MutableStateFlow(JobPostState())
//    val jobPostState = _jobPostState.asStateFlow()
//
//    // TextFieldModels for editing job fields
//    val titleItem = TextFieldModel(
//        onValueChange = { onChangeValue(it, "title") },
//        label = "Tiêu đề công việc",
//        messageError = "Tối đa 100 ký tự"
//    )
//    val descriptionItem = TextFieldModel(
//        onValueChange = { onChangeValue(it, "description") },
//        label = "Mô tả công việc",
//        messageError = "Tối đa 1000 ký tự"
//    )
//    val requirementsItem = TextFieldModel(
//        onValueChange = { onChangeValue(it, "requirements") },
//        label = "Yêu cầu công việc",
//        messageError = "Tối đa 1000 ký tự"
//    )
//    val benefitsItem = TextFieldModel(
//        onValueChange = { onChangeValue(it, "benefits") },
//        label = "Quyền lợi",
//        messageError = "Tối đa 1000 ký tự"
//    )
//    val salaryMinItem = TextFieldModel(
//        onValueChange = { onChangeValue(it, "salaryMin") },
//        label = "Mức lương tối thiểu",
//        messageError = "Vui lòng nhập số hợp lệ"
//    )
//    val salaryMaxItem = TextFieldModel(
//        onValueChange = { onChangeValue(it, "salaryMax") },
//        label = "Mức lương tối đa",
//        messageError = "Vui lòng nhập số hợp lệ"
//    )
//    val cityItem = TextFieldModel(
//        onValueChange = { onChangeValue(it, "city") },
//        label = "Thành phố",
//        messageError = "Vui lòng nhập tên thành phố"
//    )
//    val employmentTypeItem = TextFieldModel(
//        onValueChange = { onChangeValue(it, "employmentType") },
//        label = "Loại hình công việc",
//        messageError = "Vui lòng chọn loại hình"
//    )
//    val deadlineItem = TextFieldModel(
//        onValueChange = { onChangeValue(it, "deadline") },
//        label = "Hạn nộp hồ sơ",
//        messageError = "Vui lòng nhập ngày hợp lệ"
//    )
//
//    val listItemsUpdate = listOf(
//        titleItem,
//        descriptionItem,
//        requirementsItem,
//        benefitsItem,
//        salaryMinItem,
//        salaryMaxItem,
//        cityItem,
//        employmentTypeItem,
//        deadlineItem
//    )
//
//    // Load job data by ID
//    fun loadJobData(jobId: UUID) {
//        viewModelScope.launch {
//            _jobPostState.value = _jobPostState.value.copy(isLoading = true)
//            val response = withContext(Dispatchers.IO) { jobRepository.getJobById(jobId) }
//            if (response.isSuccess) {
//                _jobPostState.value = _jobPostState.value.copy(
//                    job = response.data,
//                    isLoading = false
//                )
//            } else {
//                _jobPostState.value = _jobPostState.value.copy(
//                    isLoading = false,
//                    errorMessage = "Không thể tải thông tin công việc"
//                )
//            }
//        }
//    }
//
//    // Handle text field value changes
//    fun onChangeValue(value: String, field: String) {
//        val currentJob = _jobPostState.value.job ?: return
//        _jobPostState.value = _jobPostState.value.copy(
//            job = when (field) {
//                "title" -> currentJob.copy(title = value)
//                "description" -> currentJob.copy(description = value)
//                "requirements" -> currentJob.copy(requirements = value)
//                "benefits" -> currentJob.copy(benefits = value)
//                "salaryMin" -> currentJob.copy(salaryMin = value.toBigDecimalOrNull() ?: BigDecimal.ZERO)
//                "salaryMax" -> currentJob.copy(salaryMax = value.toBigDecimalOrNull() ?: BigDecimal.ZERO)
//                "city" -> currentJob.copy(city = value)
//                "employmentType" -> currentJob.copy(employmentType = value)
//                "deadline" -> currentJob.copy(deadline = value)
//                else -> currentJob
//            }
//        )
//    }
//
//    // Update job information
//    fun updateJob() {
//        val job = _jobPostState.value.job ?: return
//        viewModelScope.launch {
//            _jobPostState.value = _jobPostState.value.copy(isLoading = true)
//            val uuid = getLoggedInUserId()
//            val response = withContext(Dispatchers.IO) {
//                jobRepository.updateJob(uuid, job.id, job)
//            }
//            if (response.isSuccess) {
//                _jobPostState.value = _jobPostState.value.copy(
//                    isLoading = false,
//                    isUpdateSuccess = true,
//                    errorMessage = null
//                )
//            } else {
//                _jobPostState.value = _jobPostState.value.copy(
//                    isLoading = false,
//                    isUpdateSuccess = false,
//                    errorMessage = "Cập nhật bài đăng thất bại"
//                )
//            }
//        }
//    }
//
//    // Reset state after update
//    fun resetUpdateState() {
//        _jobPostState.value = _jobPostState.value.copy(
//            isUpdateSuccess = false,
//            errorMessage = null
//        )
//    }
//}