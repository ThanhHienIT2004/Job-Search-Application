package com.mobile.jobsearchapplication.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DropdownMenuField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) { // 🔥 Đảm bảo full width
        Text(text = label, modifier = Modifier.padding(bottom = 4.dp))

        Box(modifier = Modifier.fillMaxWidth()) { // 🔥 Đảm bảo box cũng full width
            OutlinedTextField(
                value = selectedOption,
                onValueChange = {},
                readOnly = true, // Không cho phép nhập tay
                modifier = Modifier
                    .fillMaxWidth() // 🔥 Đảm bảo input field full width
                    .clickable { expanded = true },
                trailingIcon = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                    }
                }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth().padding(4.dp)
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false // Đóng menu sau khi chọn
                        }
                    )
                }
            }
        }
    }
}
