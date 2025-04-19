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
import com.mobile.jobsearchapplication.data.repository.job.JobRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

class PostViewModel(
    private val jobRepository: JobRepository = JobRepository()
) : ViewModel() {
    // Trạng thái cho các trường nhập liệu
    var title by mutableStateOf("")
    var jobQuantity by mutableStateOf("")
    var salaryMin by mutableStateOf("")
    var salaryMax by mutableStateOf("")
    var description by mutableStateOf("")
    var gender by mutableStateOf("Không yêu cầu")
    var educationLevel by mutableStateOf("Không yêu cầu")
    var experience by mutableStateOf("Không yêu cầu")
    var additionalInfo by mutableStateOf("")
    var location by mutableStateOf("")
    var city by mutableStateOf("")
    var district by mutableStateOf("")
    var categoryId by mutableStateOf("")
    var employmentType by mutableStateOf("FULL_TIME")
    var workingHours by mutableStateOf("8h/ngày")
    var isRemote by mutableStateOf(false)
    var userId by mutableStateOf("default_user_id") // Thêm trạng thái userId

    var postResult by mutableStateOf<Result<String>?>(null)
        private set

    @RequiresApi(Build.VERSION_CODES.O)
    fun submitPost() {
        viewModelScope.launch {
            try {
                // Kiểm tra dữ liệu đầu vào
                if (title.isBlank()) {
                    postResult = Result.failure(Exception("Tiêu đề không được để trống"))
                    return@launch
                }
                if (jobQuantity.isBlank() || jobQuantity.toIntOrNull() == null) {
                    postResult = Result.failure(Exception("Số lượng tuyển dụng phải là số hợp lệ"))
                    return@launch
                }
                if (salaryMin.isNotBlank() && salaryMin.toDoubleOrNull() == null) {
                    postResult = Result.failure(Exception("Lương tối thiểu phải là số hợp lệ"))
                    return@launch
                }
                if (salaryMax.isNotBlank() && salaryMax.toDoubleOrNull() == null) {
                    postResult = Result.failure(Exception("Lương tối đa phải là số hợp lệ"))
                    return@launch
                }
                if (location.isBlank()) {
                    postResult = Result.failure(Exception("Địa chỉ không được để trống"))
                    return@launch
                }
                if (city.isBlank()) {
                    postResult = Result.failure(Exception("Thành phố không được để trống"))
                    return@launch
                }
                if (categoryId.isBlank() || categoryId.toIntOrNull() == null) {
                    postResult = Result.failure(Exception("Danh mục công việc không hợp lệ"))
                    return@launch
                }
                if (userId.isBlank()) {
                    postResult = Result.failure(Exception("ID người dùng không được để trống"))
                    return@launch
                }

                // Tạo JSON body khớp với JobParams và JobTable
                val gson = Gson()
                val jobJson = JsonObject().apply {
                    addProperty("companyId", "default_company_id") // Thay bằng ID thực
                    addProperty("title", title)
                    addProperty("description", description)
                    addProperty("requirements", educationLevel)
                    addProperty("benefits", additionalInfo.takeIf { it.isNotBlank() } ?: "")
                    addProperty("postedBy", userId) // Sử dụng userId cho postedBy
                    addProperty("userId", userId) // Thêm userId để khớp với server
                    addProperty("categoryId", categoryId.toInt())
                    add("salary", JsonObject().apply {
                        addProperty("min", salaryMin.toDoubleOrNull() ?: 0.0)
                        addProperty("max", salaryMax.toDoubleOrNull() ?: 0.0)
                        addProperty("currency", "VND")
                        addProperty("isNegotiable", true)
                    })
                    addProperty("employmentType", employmentType)
                    add("location", JsonObject().apply {
                        addProperty("city", city)
                        addProperty("district", district.takeIf { it.isNotBlank() } ?: "Không xác định")
                        addProperty("address", location)
                        addProperty("isRemote", isRemote)
                    })
                    add("experience", JsonObject().apply {
                        val (minYears, maxYears, level) = when (experience) {
                        "Dưới 1 năm" -> Triple(0, 1, "INTERN")
                        "1-2 năm" -> Triple(1, 2, "JUNIOR")
                        "Trên 2 năm" -> Triple(2, 5, "SENIOR")
                        else -> Triple(0, 0, "FRESH")
                    }
                        addProperty("minYears", minYears)
                        addProperty("maxYears", maxYears)
                        addProperty("level", level)
                    })
                    addProperty("deadline", LocalDateTime.now().plusDays(30).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                    addProperty("positionsAvailable", jobQuantity.toInt())
                    addProperty("genderRequirement", when (gender) {
                        "Nam" -> "MALE"
                        "Nữ" -> "FEMALE"
                        else -> "ANY"
                    })
                    addProperty("status", "ACTIVE")
                    add("additionalInfo", JsonObject().apply {
                        addProperty("workingHours", workingHours)
                        addProperty("overtimePolicy", null as String?)
                        addProperty("probationPeriod", null as String?)
                        addProperty("jobImage", null as String?)
                    })
                }

                // Tạo Job object để gửi qua API
                val job = Job(
                    id = UUID.randomUUID(),
                    title = title,
                    description = description,
                    salaryMin = salaryMin.toBigDecimalOrNull(),
                    salaryMax = salaryMax.toBigDecimalOrNull(),
                    currency = "VND",
                    location = location,
                    jobType = employmentType,
                    experienceLevel = experience,
                    companyId = "default_company_id",
                    postedBy = userId,
                    benefits = additionalInfo.takeIf { it.isNotBlank() },
                    quantity = jobQuantity.toInt(),
                    genderRequire = gender,
                    deadline = LocalDateTime.now().plusDays(30).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                    status = "ACTIVE",
                    requirements = educationLevel,
                    jobImage = null,
                    createdAt = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                )

                // Gọi API
                val response: ApiResponse<Job> = jobRepository.createJob(job)

                if (response.data != null) {
                    postResult = Result.success("Đăng tin thành công: ${response.message}")
                } else {
                    postResult = Result.failure(Exception("Đăng tin thất bại: ${response.message}"))
                }
            } catch (e: HttpException) {
                when (e.code()) {
                    400 -> postResult = Result.failure(Exception("Yêu cầu không hợp lệ: Vui lòng kiểm tra dữ liệu nhập (userId, categoryId, hoặc định dạng)."))
                    404 -> postResult = Result.failure(Exception("Không tìm thấy endpoint /jobs/add. Vui lòng kiểm tra cấu hình API."))
                    else -> postResult = Result.failure(Exception("Lỗi server: ${e.message()}"))
                }
            } catch (e: Exception) {
                postResult = Result.failure(Exception("Lỗi không xác định: ${e.message}"))
            }
        }
    }
}