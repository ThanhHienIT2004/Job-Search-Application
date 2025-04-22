package com.mobile.jobsearchapplication.ui.features.post

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.mobile.jobsearchapplication.data.model.ApiResponse
import com.mobile.jobsearchapplication.data.model.job.Job
import com.mobile.jobsearchapplication.data.repository.auth.AuthRepository
import com.mobile.jobsearchapplication.data.repository.job.JobRepository
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
    val benefits: String?,
    val postedBy: String,
    val categoryId: Int,
    val salary: Salary,
    val employmentType: String,
    val location: Location,
    val experience: Experience,
    val deadline: String,
    val positionsAvailable: Int,
    val genderRequirement: String,
    val status: String = "ACTIVE",
    val additionalInfo: AdditionalInfo
) {
    data class Salary(
        val min: Double,
        val max: Double,
        val currency: String = "VND",
        val isNegotiable: Boolean = true
    )

    data class Location(
        val city: String,
        val district: String?,
        val address: String,
        val isRemote: Boolean
    )

    data class Experience(
        val minYears: Int,
        val maxYears: Int,
        val level: String
    )

    data class AdditionalInfo(
        val workingHours: String,
        val overtimePolicy: String?,
        val probationPeriod: String?,
        val jobImage: String?
    )
}

@RequiresApi(Build.VERSION_CODES.O)
class PostViewModel(
    private val jobRepository: JobRepository = JobRepository(),

) : ViewModel() {
    var jobPost by mutableStateOf(
        JobPost(
            companyId = "a7KxP9mZqW2rT5vY8nL3jF6hB4dC",
            title = "",
            description = "",
            requirements = "Không yêu cầu",
            benefits = null,
            postedBy = "g3DCV3byPnau4HXRhQYso4iTBoE2",
            categoryId = 0,
            salary = JobPost.Salary(min = 0.0, max = 0.0),
            employmentType = "FULL_TIME",
            location = JobPost.Location(city = "", district = null, address = "", isRemote = false),
            experience = JobPost.Experience(minYears = 0, maxYears = 0, level = "FRESH"), // Thay ENTRY bằng FRESH
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
            jobPost.title.isBlank() -> Result.failure(Exception("Tiêu đề không được để trống"))
            jobPost.description.isBlank() -> Result.failure(Exception("Mô tả công việc không được để trống"))
            jobPost.positionsAvailable <= 0 -> Result.failure(Exception("Số lượng tuyển dụng phải lớn hơn 0"))
            jobPost.salary.min <= 0.0 -> Result.failure(Exception("Lương tối thiểu phải lớn hơn 0"))
            jobPost.salary.max <= 0.0 -> Result.failure(Exception("Lương tối đa phải lớn hơn 0"))
            jobPost.salary.min > jobPost.salary.max -> Result.failure(Exception("Lương tối thiểu không được lớn hơn lương tối đa"))
            jobPost.location.city.isBlank() -> Result.failure(Exception("Thành phố không được để trống"))
            jobPost.location.address.isBlank() -> Result.failure(Exception("Địa chỉ không được để trống"))
            jobPost.categoryId <= 0 -> Result.failure(Exception("Danh mục công việc không hợp lệ"))
            jobPost.companyId == "default_company_id" -> Result.failure(Exception("ID công ty không hợp lệ"))
            jobPost.postedBy == "default_user_id" -> Result.failure(Exception("Vui lòng đăng nhập để đăng tin"))
            jobPost.additionalInfo.workingHours.isBlank() -> Result.failure(Exception("Giờ làm việc không được để trống"))
            jobPost.experience.level !in listOf("INTERN", "FRESH", "JUNIOR", "SENIOR", "LEAD") -> Result.failure(Exception("Mức kinh nghiệm không hợp lệ"))
            jobPost.employmentType !in listOf("FULL_TIME", "PART_TIME", "CONTRACT") -> Result.failure(Exception("Loại công việc không hợp lệ"))
            jobPost.genderRequirement !in listOf("MALE", "FEMALE", "ANY") -> Result.failure(Exception("Yêu cầu giới tính không hợp lệ"))
            jobPost.status !in listOf("ACTIVE", "CLOSED", "DRAFT") -> Result.failure(Exception("Trạng thái công việc không hợp lệ"))
            else -> Result.success(Unit)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun submitPost() {
        viewModelScope.launch {
            try {
                // Kiểm tra dữ liệu
                validateJobPost().onFailure { exception ->
                    postResult = Result.failure(exception)
                    return@launch
                }

                // Tạo JSON theo cấu trúc JobParams
                val jsonBody = Gson().toJsonTree(jobPost).asJsonObject

                // Log JSON để debug
                println("JSON Body: $jsonBody")

                // Gọi API
                val response: ApiResponse<Job> = jobRepository.createJob(jsonBody)
                if (response.data != null) {
                    postResult = Result.success("Đăng tin thành công: ${response.message}")
                } else {
                    postResult = Result.failure(Exception("Đăng tin thất bại: ${response.message}"))
                }
            } catch (e: HttpException) {
                postResult = when (e.code()) {
                    400 -> Result.failure(Exception("Yêu cầu không hợp lệ: Vui lòng kiểm tra dữ liệu nhập"))
                    401 -> Result.failure(Exception("Chưa đăng nhập hoặc token hết hạn"))
                    403 -> Result.failure(Exception("Không có quyền đăng tin"))
                    404 -> Result.failure(Exception("Không tìm thấy endpoint /jobs/add"))
                    500 -> Result.failure(Exception("Lỗi server, vui lòng thử lại sau"))
                    else -> Result.failure(Exception("Lỗi HTTP: ${e.code()} - ${e.message()}"))
                }
            } catch (e: IOException) {
                postResult = Result.failure(Exception("Lỗi kết nối mạng, vui lòng kiểm tra kết nối"))
            } catch (e: Exception) {
                postResult = Result.failure(Exception("Lỗi không xác định: ${e.message}"))
            }
        }
    }
}