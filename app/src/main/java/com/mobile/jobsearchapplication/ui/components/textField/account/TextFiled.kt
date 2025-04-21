package com.mobile.jobsearchapplication.ui.components.textField.account

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldCustom(
    modifier: Modifier = Modifier,
    isNumeric: Boolean = false,
    model: TextFieldModel
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val isHidePassword = remember { mutableStateOf(true) }

    OutlinedTextField(
        value = model.value,
        onValueChange = model.onValueChange,
        label = { Text(model.label) },
        modifier = modifier
            .fillMaxWidth(0.9f)
            .height(90.dp)
            .focusRequester(focusRequester),
        leadingIcon = {
            model.leadingIcon?.let { painterResource(id = it) }?.let {
                Icon(
                    painter = it,
                    contentDescription = model.label,
                    modifier = Modifier.size(28.dp)
                )
            }
        },
        trailingIcon = {
            if (model.isPasswordField && model.trailingIcons != null) {
                val iconRes = if (isHidePassword.value) model.trailingIcons[0] else model.trailingIcons[1]
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = model.label,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            isHidePassword.value = !isHidePassword.value
                        }
                )
            }
        },
        isError = model.isError,
        supportingText = {
            if (model.isError) {
                Text(
                    text = model.messageError,
                    modifier = Modifier.fillMaxWidth().wrapContentWidth()
                )
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = if(!model.isImeActionDone) ImeAction.Next else  ImeAction.Done,
            keyboardType = if (isNumeric) KeyboardType.Number else KeyboardType.Text
        ),
        keyboardActions = if(!model.isImeActionDone) {
            KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
        } else {
            KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
        },
        visualTransformation =
            if (model.isPasswordField && isHidePassword.value)
                PasswordVisualTransformation()
            else
                VisualTransformation.None,
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
}
