package com.mobile.jobsearchapplication.ui.components.textField.account

import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mobile.jobsearchapplication.ui.components.DatePickerModal

@Composable
fun TextFieldDatePicker(
    value: String,
    onDateSelected: (String) -> Unit
) {
    var isClickIcon by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = {},
        readOnly = true,
        label = { Text("Ngày sinh") },
        modifier = Modifier.fillMaxWidth(0.9f).height(70.dp),
        leadingIcon = {},
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = "Icon date picker",
                modifier = Modifier.padding(end = 30.dp)
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

    if (isClickIcon) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DatePickerModal(
                onDateSelected
            ) { isClickIcon = false }
        }
    }

}