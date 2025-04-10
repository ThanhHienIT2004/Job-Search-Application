package com.mobile.jobsearchapplication.ui.components.textField.post

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CustomTextField(
    label: String,
    value: String,
    modifier: Modifier = Modifier.fillMaxWidth(),
    maxLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default, // Thêm tham số keyboardOptions
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier,
        maxLines = maxLines,
        keyboardOptions = keyboardOptions // Áp dụng keyboardOptions
    )
}
