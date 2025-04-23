package com.mobile.jobsearchapplication.data.model.jobcategory

import com.google.gson.annotations.SerializedName

data class JobCategoryResponse(
    @SerializedName("pageCount") val pageCount: Int,
    @SerializedName("data") val data: List<JobCategory>
)

data class JobCategory(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("imageUrl") val imageUrl: String?
)