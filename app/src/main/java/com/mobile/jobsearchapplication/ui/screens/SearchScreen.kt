package com.mobile.jobsearchapplication.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

import
@Composable
fun SearchScreen(navController  : NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Thanh tìm kiếm
        SearchBar()

        // Bộ lọc vị trí
        LocationFilter()

        // Bộ lọc loại công việc
        JobTypeFilter()

        // Danh sách công việc
        JobList()
    }
}

@Composable
fun SearchBar() {
    var searchText by remember { mutableStateOf("") }

    TextField(
        value = searchText,
        onValueChange = { searchText = it },
        placeholder = { Text("Tìm kiếm việc làm...") },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Search")
        },
        trailingIcon = {
            if (searchText.isNotEmpty()) {
                IconButton(onClick = { searchText = "" }) {
                    Icon(Icons.Default.Close, contentDescription = "Clear")
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(16.dp)),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun LocationFilter() {
    val locations = listOf("HCM", "Cần Thơ", "Hà Nội")
    var selectedLocation by remember { mutableStateOf(locations[0]) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color(0xFFEFEFEF), shape = RoundedCornerShape(16.dp)),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        locations.forEach { location ->
            Text(
                text = location,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { selectedLocation = location }
                    .background(
                        if (selectedLocation == location) Color.Blue else Color.Transparent,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                color = if (selectedLocation == location) Color.White else Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun JobTypeFilter() {
    val types = listOf("Tất cả", "Phù hợp nhất", "Gần nhất")
    var selectedType by remember { mutableStateOf(types[0]) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        types.forEach { type ->
            Text(
                text = type,
                modifier = Modifier
                    .padding(4.dp)
                    .clickable { selectedType = type }
                    .border(
                        width = 1.dp,
                        color = if (selectedType == type) Color.Blue else Color.Gray,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                color = if (selectedType == type) Color.Blue else Color.Black
            )
        }
    }
}

@Composable
fun JobList() {
    val jobs = listOf(
        "Kỹ sư phần mềm",
        "Nhân viên bán hàng",
        "Chuyên viên tài chính",
        "Lập trình viên Java",
        "Kế toán",
        "Chuyên viên Marketing"
    )

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(jobs) { job ->
            JobListItem(job)
        }
    }
}

@Composable
fun JobListItem(jobTitle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color(0xFFF6F6F6), shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_business_24),
            contentDescription = "Job Icon",
            tint = Color.Gray,
            modifier = Modifier
                .size(40.dp)
                .padding(end = 8.dp)
        )
        Column {
            Text(
                text = jobTitle,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = "Description duis aute irure dolor in reprehenderit in voluptate velit.",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}
