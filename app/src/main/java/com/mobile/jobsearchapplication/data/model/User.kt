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
    val id: String,
    val fullName: String,
    val userName: String,
    val gender: Gender,
    val phone: String,
    val email: String,
    val address: Address,
    val birthDay: LocalDate
)
