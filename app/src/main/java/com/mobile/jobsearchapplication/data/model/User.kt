package com.mobile.jobsearchapplication.data.model

import java.time.LocalDate

// Enum cho giới tính
enum class Gender {
    MALE, FEMALE, OTHER
}

// Định nghĩa địa chỉ thành một data class
data class Address(
    val street: String,
    val city: String,
    val state: String,
    val country: String
)

data class User(
    val id: String,                 // ID người dùng
    val fullName: String,           // Họ và tên
    val userName: String,           // Tên đăng nhập (duy nhất)
    val password: CharArray,        // Mật khẩu (cần được xóa sau khi dùng)
    val gender: Gender,             // Giới tính (dùng Enum thay vì String)
    val phone: String,              // Số điện thoại (String để hỗ trợ mã quốc gia)
    val email: String,              // Email
    val address: Address,           // Địa chỉ (tách thành class riêng)
    val birthDay: LocalDate         // Ngày sinh (Dùng LocalDate để dễ tính toán)
)
