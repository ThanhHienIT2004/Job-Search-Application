package com.mobile.jobsearchapplication.ui.components.text_field.auth

import java.lang.Error

data class TextFieldAuthModel(
    var value: String,
    val onValueChange: (String) -> Unit,
    val label: String,
    val leadingIcon: Int,
    val trailingIcons: List<Int>? = null,
    var isError: Boolean = false,
    val messageError: String = "",
    val isPasswordField: Boolean = false,
    val isImeActionDone: Boolean = false
)





