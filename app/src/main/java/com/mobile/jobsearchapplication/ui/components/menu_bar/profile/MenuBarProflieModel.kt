package com.mobile.jobsearchapplication.ui.components.menu_bar.profile

import androidx.annotation.DrawableRes
import com.mobile.jobsearchapplication.R

sealed class MenuBarProfile(
    @DrawableRes val icon: Int,
    val title: String
) {
    data object Information: MenuBarProfile(R.drawable.ic_information, "Icon Information")
    data object Education: MenuBarProfile(R.drawable.ic_education, "Icon Skills And Education")
    data object Experience: MenuBarProfile(R.drawable.ic_experience, "Icon Experience And Projects")
    data object Certifications: MenuBarProfile(R.drawable.ic_certifications, "Icon Certifications")
}

open class ContentMenuItemProfile(
    val title: String,
    var content: String
)

sealed class MenuItemInformation(title: String, content: String): ContentMenuItemProfile(title, content) {
    data object FullName : MenuItemInformation("Họ và tên:", "Super Quang")
    data object Age : MenuItemInformation("Tuổi:", "20")
    data object Gender : MenuItemInformation("Giới tính:", "Nam")
    data object Address : MenuItemInformation("Địa chỉ:", "Long An")
    data object PhoneNumber : MenuItemInformation("Số điện thoại:", "0123312123")
    data object Email : MenuItemInformation("Email:", "william.johnson@example-pet-store.com")
}

sealed class MenuItemEducation(title: String, content: String): ContentMenuItemProfile(title, content) {
    data object School : MenuItemEducation("Học vấn:", "GTVT")
    data object Major : MenuItemEducation("Chuyên ngành:", "Công nghệ thông tin")
    data object GPA : MenuItemEducation("Điểm GPA:", "4.0")
    data object Skills : MenuItemEducation("Kỹ năng:", "Android, Java, Kotlin")
    data object Languages : MenuItemExperience("Ngôn ngữ:", "English, Vietnamese")
}

sealed class MenuItemExperience(title: String, content: String): ContentMenuItemProfile(title, content) {
    data object Company : MenuItemExperience("Công ty:", "FPT")
    data object Position : MenuItemExperience("Chức vụ:", "Android Developer")
    data object Time : MenuItemExperience("Thời gian làm việc:", "2022 - 2023")
    data object Projects : MenuItemExperience("Dự án:", "Project 1, Project 2")
}

sealed class MenuItemCertifications(title: String, content: String): ContentMenuItemProfile(title, content) {
    data object Certification : MenuItemCertifications("Chứng chỉ:", "Android Developer")
    data object Time : MenuItemCertifications("Thời gian:", "2022 - 2023")
}