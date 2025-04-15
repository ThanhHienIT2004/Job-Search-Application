package com.mobile.jobsearchapplication.ui.components.textField.auth

data class TextFieldModel(
    var value: String = "",
    val onValueChange: (String) -> Unit,
    val label: String,
    val leadingIcon: Int? = null,
    val trailingIcons: List<Int>? = null,
    var isError: Boolean = false,
    val messageError: String = "",
    val isPasswordField: Boolean = false,
    val isImeActionDone: Boolean = false
)





