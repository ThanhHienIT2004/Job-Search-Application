package com.mobile.jobsearchapplication.data.model.user

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class FavoriteJobPosting(
    @SerializedName("jobId") val jobId: UUID,
    @SerializedName("status") val status: Boolean
)