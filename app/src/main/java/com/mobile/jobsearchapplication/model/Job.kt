package com.mobile.jobsearchapplication.model
import java.time.LocalDate

data class Job(
    val title: String,
    val salary: String,
    val location: String,
    val description: String
)











//data class Job(
//    val id: String,                  // ID công việc (UUID hoặc do server cung cấp)
//    val title: String,                // Tiêu đề công việc (VD: "Lập trình viên Android")
//    val company: String,              // Tên công ty tuyển dụng
//    val location: String,             // Địa điểm làm việc (VD: "Hà Nội, Việt Nam")
//    val salary: String?,              // Mức lương (VD: "$1000 - $2000" hoặc null nếu không công khai)
//    val jobType: JobType,             // Loại công việc (Toàn thời gian, bán thời gian, remote)
//    val description: String,          // Mô tả công việc chi tiết
//    val requirements: List<String>,   // Danh sách yêu cầu công việc
//    val postedDate: LocalDate,        // Ngày đăng tuyển
//    val deadline: LocalDate?,         // Hạn cuối nộp đơn (có thể null)
//    val contactEmail: String,         // Email liên hệ
//    val contactPhone: String?,        // Số điện thoại (có thể null)
//    val isSaved: Boolean = false      // Đánh dấu công việc đã lưu hay chưa
//)
//
//// Enum cho loại công việc
//enum class JobType {
//    FULL_TIME, PART_TIME, REMOTE, INTERNSHIP, CONTRACT
//}







