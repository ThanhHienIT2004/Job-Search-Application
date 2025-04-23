package com.mobile.jobsearchapplication.data.model.job

import java.math.BigDecimal

data class JobDetails(
    val companyId: String,
    val title: String,
    val description: String,
    val requirements: String,
    val benefits: String,
    val postedBy: String,
    val categoryId: Int,
    val salaryMin: BigDecimal,
    val salaryMax: BigDecimal,
    val currency: String,
    val isNegotiable: Boolean,
    val employmentType: String,
    val city: String,
    val district: String,
    val address: String,
    val isRemote: Boolean,
    val minYears: Int,
    val maxYears: Int,
    val experienceLevel: String,
    val deadline: String,
    val positionsAvailable: Int,
    val genderRequirement: String,
    val status: String,
    val workingHours: String,
    val overtimePolicy: String,
    val probationPeriod: String,
    val jobImage: String?
)