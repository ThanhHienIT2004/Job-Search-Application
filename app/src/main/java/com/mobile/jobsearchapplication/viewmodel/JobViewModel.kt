//package com.mobile.jobsearchapplication.ui.components
//
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.text.font.FontWeight
//import com.mobile.jobsearchapplication.model.Job
//
//@Composable
//fun JobList(jobs: List<Job>) {
//    Column(modifier = Modifier.fillMaxSize()) {
//        if (jobs.isEmpty()) {
//            Text(
//                text = "Không tìm thấy công việc nào",
//                modifier = Modifier
//                    .padding(16.dp)
//                    .fillMaxWidth()
//            )
//        } else {
//            jobs.forEach { job ->
//                Text(
//                    text = "${job.title} - ${job.location} (${job.type})",
//                    modifier = Modifier
//                        .padding(8.dp)
//                        .clickable { /* Xử lý khi nhấn vào công việc */ },
//                    fontWeight = FontWeight.Bold
//                )
//            }
//        }
//    }
//}
