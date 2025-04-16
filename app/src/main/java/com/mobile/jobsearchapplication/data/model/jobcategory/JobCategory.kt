package com.mobile.jobsearchapplication.data.model.jobcategory


import com.google.gson.annotations.SerializedName

data class JobCategory(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("imageUrl") val imageUrl: String?
)