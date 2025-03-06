package com.mobile.jobsearchapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Business
import com.mobile.jobsearchapplication.model.JobCategory

class HomeViewModel : ViewModel() {

    val jobCategories = listOf(
        JobCategory(Icons.Filled.Computer, "Công nghệ thông tin"),
        JobCategory(Icons.Filled.Build, "Kỹ thuật"),
        JobCategory(Icons.Filled.MedicalServices, "Y tế"),
        JobCategory(Icons.Filled.Business, "Kinh doanh")
    )
}
