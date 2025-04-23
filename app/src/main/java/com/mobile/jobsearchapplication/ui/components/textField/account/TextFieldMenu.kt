package com.mobile.jobsearchapplication.ui.components.textField.account

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Female
import androidx.compose.material.icons.outlined.GridOff
import androidx.compose.material.icons.outlined.GridOn
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Male
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldMenu(
    model: TextFieldModel,
    modifier: Modifier = Modifier,
) {
    var isClickIcon by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = model.value,
            onValueChange = model.onValueChange,
            readOnly = true,
            label = { Text(model.label) },
            modifier = Modifier.fillMaxWidth(0.9f).height(70.dp),
            leadingIcon = {},
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Icon date picker",
                    modifier = Modifier.padding(end = 15.dp).size(50.dp)
                        .clickable { isClickIcon = !isClickIcon }
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Gray,
                cursorColor = Color.Gray
            )
        )

            DropdownMenu(
                expanded = isClickIcon,
                onDismissRequest = { isClickIcon = false },
                modifier = Modifier
                    .background(Color(0xFFF4F8FC))
            ) {
                DropdownMenuItem(
                    text = { Text("Nam") },
                    leadingIcon = { Icon(Icons.Outlined.Male, contentDescription = null) },
                    onClick = { model.onValueChange("Male") }
                )
                DropdownMenuItem(
                    text = { Text("Nữ") },
                    leadingIcon = { Icon(Icons.Outlined.Female, contentDescription = null) },
                    onClick = { model.onValueChange("Female") }
                )
                DropdownMenuItem(
                    text = { Text("Không muốn tiết lộ") },
                    leadingIcon = { Icon(Icons.Outlined.ErrorOutline, contentDescription = null) },
                    onClick = { model.onValueChange("Other") }
                )
            }
    }
}