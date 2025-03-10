//package com.mobile.jobsearchapplication.ui.screens
//
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.navigation.NavHostController
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import com.mobile.jobsearchapplication.model.Job
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.mobile.jobsearchapplication.ui.components.JobList
//import com.mobile.jobsearchapplication.viewmodel.JobViewModel
//
//@Composable
//fun SearchScreen(navController: NavHostController) {
//    val jobViewModel: JobViewModel = viewModel()
//    val jobs by jobViewModel.jobs.collectAsState()
//
//    var searchText by remember { mutableStateOf("") }
//
//    Column(modifier = Modifier.fillMaxSize()) {
//        // Dùng lại SearchBar từ HomeScreen
//        SearchBar(
//            searchText = searchText,
//            onSearchTextChanged = {
//                searchText = it
//                jobViewModel.searchJobs(it)
//            }
//        )
//
//        // Lọc công việc và hiển thị danh sách
//        JobList(jobs = jobs)
//    }
//
//    // Tải danh sách công việc khi vào màn hình
//    LaunchedEffect(Unit) {
//        jobViewModel.loadJobs()
//    }
//}
//
//
//
//@Composable
//fun LocationFilter() {
//    val locations = listOf("HCM", "Cần Thơ", "Hà Nội")
//    var selectedLocation by remember { mutableStateOf(locations[0]) }
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp)
//            .background(Color(0xFFEFEFEF), shape = RoundedCornerShape(16.dp)),
//        horizontalArrangement = Arrangement.SpaceEvenly
//    ) {
//        locations.forEach { location ->
//            Text(
//                text = location,
//                modifier = Modifier
//                    .padding(8.dp)
//                    .clickable { selectedLocation = location }
//                    .background(
//                        if (selectedLocation == location) Color.Blue else Color.Transparent,
//                        shape = RoundedCornerShape(16.dp)
//                    )
//                    .padding(horizontal = 16.dp, vertical = 8.dp),
//                color = if (selectedLocation == location) Color.White else Color.Black,
//                fontWeight = FontWeight.Bold
//            )
//        }
//    }
//}
//
//@Composable
//fun JobTypeFilter() {
//    val types = listOf("Tất cả", "Phù hợp nhất", "Gần nhất")
//    var selectedType by remember { mutableStateOf(types[0]) }
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp, vertical = 8.dp),
//        horizontalArrangement = Arrangement.SpaceEvenly
//    ) {
//        types.forEach { type ->
//            Text(
//                text = type,
//                modifier = Modifier
//                    .padding(4.dp)
//                    .clickable { selectedType = type }
//                    .border(
//                        width = 1.dp,
//                        color = if (selectedType == type) Color.Blue else Color.Gray,
//                        shape = RoundedCornerShape(16.dp)
//                    )
//                    .padding(horizontal = 16.dp, vertical = 8.dp),
//                color = if (selectedType == type) Color.Blue else Color.Black
//            )
//        }
//    }
//}
//
