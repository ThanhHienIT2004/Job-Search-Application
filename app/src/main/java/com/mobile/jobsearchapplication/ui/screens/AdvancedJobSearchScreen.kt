package com.mobile.jobsearchapplication.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mobile.jobsearchapplication.model.Job
import com.mobile.jobsearchapplication.ui.screens.components.BackButton
import com.mobile.jobsearchapplication.ui.screens.components.BottomNavBarCustom
import com.mobile.jobsearchapplication.ui.screens.components.Screen

@Composable
fun PostFilterScreen(navController: NavController, query: String) {
    var selectedFilter by remember { mutableStateOf("Tất cả") }  // Trạng thái lưu bộ lọc

    BaseScreen (
        title = query,
        actionsTop = { BackButton(navController) },
        actionsBot = {
            BottomNavBarCustom(navController)
        }
    )
    {

    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterSection(selectedFilter: String, onFilterSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedSalary by remember { mutableStateOf("Tất cả") }

    val filters = listOf("Tất cả", "Toàn thời gian", "Bán thời gian", "Remote", "Freelance")

    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        filters.forEach { filter ->
            FilterButton(
                text = filter,
                isSelected = filter == selectedFilter,
                onClick = { onFilterSelected(filter) }
            )
        }

        // Dropdown lọc theo mức lương
        Box {
            Button(onClick = { expanded = true }) {
                Text("Lọc: $selectedSalary")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                listOf("Tất cả", "Dưới 5 triệu", "5-10 triệu", "Trên 10 triệu").forEach { salary ->
                    DropdownMenuItem(
                        text = { Text(salary) },
                        onClick = {
                            selectedSalary = salary
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

// Button cho từng filter
@Composable
fun FilterButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.run {
            buttonColors(
                containerColor = if (isSelected) Color.Blue else Color.LightGray
            )
        },
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(text, color = if (isSelected) Color.White else Color.Black)
    }
}
