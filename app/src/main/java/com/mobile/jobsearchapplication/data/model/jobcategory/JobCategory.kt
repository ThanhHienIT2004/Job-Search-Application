package com.mobile.jobsearchapplication.data.model.jobcategory


import com.google.gson.annotations.SerializedName

data class JobCategory(
    @SerializedName("categoryId") val id: String,
    @SerializedName("name") val name: String, // Sửa từ "categoryName" thành "name"
    val imageUrl: String?
)