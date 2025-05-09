package com.mobile.jobsearchapplication.ui.features.post

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mobile.jobsearchapplication.data.model.ApiResponse
import com.mobile.jobsearchapplication.data.model.job.Job
import com.mobile.jobsearchapplication.data.repository.job.JobRepository
import com.mobile.jobsearchapplication.utils.FireBaseUtils
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class JobPost(
    val companyId: String,
    val title: String,
    val description: String,
    val requirements: String,
    val benefits: String?, // Sẽ xử lý null trong submitPost
    val postedBy: String,
    val categoryId: Int,
    val salaryMin: Double,
    val salaryMax: Double,
    val salaryPeriod: String = "MONTHLY",
    val currency: String = "VND",
    val employmentType: String,
    val location: String,
    val experienceLevel: String,
    val deadline: String,
    val positionsAvailable: Int,
    val genderRequirement: String,
    val status: String = "ACTIVE",
    val additionalInfo: AdditionalInfo
) {
    data class AdditionalInfo(
        val workingHours: String,
        val overtimePolicy: String?,
        val probationPeriod: String?,
        val jobImage: String?
    )
}

@RequiresApi(Build.VERSION_CODES.O)
class PostViewModel(
    private val jobRepository: JobRepository = JobRepository()
) : ViewModel() {
    private val userId = FireBaseUtils.getLoggedInUserId()
    var jobPost by androidx.compose.runtime.mutableStateOf(
        JobPost(
            companyId = userId,
            title = "",
            description = "",
            requirements = "Không yêu cầu",
            benefits = "",
            postedBy = userId,
            categoryId = 0,
            salaryMin = 0.0,
            salaryMax = 0.0,
            salaryPeriod = "MONTHLY",
            currency = "VND",
            employmentType = "FULL_TIME",
            location = "",
            experienceLevel = "FRESH",
            deadline = LocalDateTime.now().plusDays(30).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
            positionsAvailable = 0,
            genderRequirement = "ANY",
            status = "ACTIVE",
            additionalInfo = JobPost.AdditionalInfo(
                workingHours = "8h/ngày",
                overtimePolicy = null,
                probationPeriod = null,
                jobImage = null
            )
        )
    )
    var postResult by mutableStateOf<Result<String>?>(null)
        private set

    private fun validateJobPost(): Result<Unit> {
        return when {
            userId.isEmpty() -> Result.failure(Exception("Vui lòng đăng nhập để đăng tin"))
            jobPost.companyId.isEmpty() -> Result.failure(Exception("ID công ty không hợp lệ"))
            jobPost.title.isBlank() -> Result.failure(Exception("Tiêu đề không được để trống"))
            jobPost.description.isBlank() -> Result.failure(Exception("Mô tả công việc không được để trống"))
            jobPost.positionsAvailable <= 0 -> Result.failure(Exception("Số lượng tuyển dụng phải lớn hơn 0"))
            jobPost.salaryMin <= 0.0 -> Result.failure(Exception("Lương tối thiểu phải lớn hơn 0"))
            jobPost.salaryMax <= 0.0 -> Result.failure(Exception("Lương tối đa phải lớn hơn 0"))
            jobPost.salaryMin > jobPost.salaryMax -> Result.failure(Exception("Lương tối thiểu không được lớn hơn lương tối đa"))
            jobPost.location.isBlank() -> Result.failure(Exception("Địa chỉ không được để trống"))
            jobPost.categoryId <= 0 -> Result.failure(Exception("Danh mục công việc không hợp lệ"))
            jobPost.additionalInfo.workingHours.isBlank() -> Result.failure(Exception("Giờ làm việc không được để trống"))
            jobPost.experienceLevel !in listOf("FRESH", "INTERN", "JUNIOR", "SENIOR", "LEAD") ->
                Result.failure(Exception("Mức kinh nghiệm không hợp lệ"))
            jobPost.employmentType !in listOf("FULL_TIME", "PART_TIME", "CONTRACT") ->
                Result.failure(Exception("Loại công việc không hợp lệ"))
            jobPost.genderRequirement !in listOf("MALE", "FEMALE", "ANY") ->
                Result.failure(Exception("Yêu cầu giới tính không hợp lệ"))
            jobPost.status !in listOf("ACTIVE", "CLOSED", "DRAFT") ->
                Result.failure(Exception("Trạng thái công việc không hợp lệ"))
            jobPost.salaryPeriod !in listOf("HOURLY", "DAILY", "WEEKLY", "MONTHLY", "YEARLY") ->
                Result.failure(Exception("Chu kỳ lương không hợp lệ"))
            jobPost.currency.isBlank() -> Result.failure(Exception("Đơn vị tiền tệ không được để trống"))
            try {
                LocalDateTime.parse(jobPost.deadline, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                false
            } catch (e: Exception) {
                true
            } -> Result.failure(Exception("Ngày hết hạn không hợp lệ"))
            LocalDateTime.parse(jobPost.deadline, DateTimeFormatter.ISO_LOCAL_DATE_TIME).isBefore(LocalDateTime.now()) ->
                Result.failure(Exception("Ngày hết hạn phải trong tương lai"))
            else -> Result.success(Unit)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun submitPost() {
        viewModelScope.launch {
            try {
                validateJobPost().onFailure { exception ->
                    postResult = Result.failure(exception)
                    return@launch
                }

                // Ánh xạ JobPost sang JobParams
                val jobParams = mapOf(
                    "companyId" to jobPost.companyId,
                    "title" to jobPost.title,
                    "description" to jobPost.description,
                    "requirements" to jobPost.requirements,
                    "benefits" to (jobPost.benefits ?: ""), // Xử lý null
                    "postedBy" to jobPost.postedBy,
                    "categoryId" to jobPost.categoryId,
                    "salaryMin" to jobPost.salaryMin,
                    "salaryMax" to jobPost.salaryMax,
                    "salaryPeriod" to jobPost.salaryPeriod,
                    "currency" to jobPost.currency,
                    "employmentType" to jobPost.employmentType,
                    "location" to jobPost.location,
                    "experienceLevel" to jobPost.experienceLevel,
                    "deadline" to LocalDateTime.now().plusDays(30).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                    "positionsAvailable" to jobPost.positionsAvailable,
                    "genderRequirement" to jobPost.genderRequirement,
                    "status" to jobPost.status,
                    "additionalInfo" to mapOf(
                        "workingHours" to jobPost.additionalInfo.workingHours,
                        "overtimePolicy" to jobPost.additionalInfo.overtimePolicy,
                        "probationPeriod" to jobPost.additionalInfo.probationPeriod,
                        "jobImage" to jobPost.additionalInfo.jobImage
                    )
                )

                val jsonBody = Gson().toJsonTree(jobParams).asJsonObject
                val response: ApiResponse<Job> = jobRepository.createJob(jsonBody)
                postResult = Result.success(response.message)
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorMessage = try {
                    Gson().fromJson(errorBody, ApiResponse::class.java)?.message
                        ?: "Lỗi server: ${e.code()}"
                } catch (jsonException: Exception) {
                    "Lỗi server: ${e.code()} - ${e.message()}"
                }
                postResult = Result.failure(Exception(errorMessage))
            } catch (e: IOException) {
                postResult = Result.failure(Exception("Lỗi kết nối mạng"))
            } catch (e: Exception) {
                postResult = Result.failure(Exception("Lỗi: ${e.message ?: "Không xác định"}"))
            }
        }
    }
}