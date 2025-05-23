/**
 * Copyright (c) 2025 Meru Science Innovators Club
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Meru Science Innovators Club.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Meru Science Innovators Club.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 *
 * NO WARRANTY: This software is provided "as is" without warranty of any kind,
 * either express or implied, including but not limited to the implied warranties
 * of merchantability and fitness for a particular purpose.
 */
package com.newton.commonUi.ui

import android.webkit.*
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.input.key.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.commonUi.R
import com.newton.commonUi.theme.*

@Composable
fun AuthTextFields(
    onInputChanged: (String) -> Unit,
    inputText: String,
    label: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {},
    onSubmitted: (() -> Unit)? = null,
    supportingText: @Composable () -> Unit
) {
    OutlinedTextField(
        value = inputText,
        shape = RoundedCornerShape(14.dp),
        onValueChange = onInputChanged,
        label = {
            Text(
                text = label,
                style =
                MaterialTheme.typography.bodyMedium.copy(
                    color = PurpleGrey40
                )
            )
        },
        singleLine = true,
        modifier = modifier.fillMaxWidth(),
        isError = isError,
        keyboardOptions =
        KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions =
        KeyboardActions(
            onNext = { onImeAction() },
            onDone = { onImeAction() }
        ),
        supportingText = supportingText
    )
}

@Composable
fun DefaultTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    isRequired: Boolean = false,
    isError: Boolean = false,
    errorMessage: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    maxLines: Int = 1,
    minLines: Int = 1,
    focusRequester: FocusRequester? = null,
    validateUrl: Boolean = false,
    leadingIcon: ImageVector? = null,
    shape: RoundedCornerShape = RoundedCornerShape(0.dp)
) {
    val textFieldModifier =
        if (focusRequester != null) {
            modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
        } else {
            modifier.fillMaxWidth()
        }

    val isUrlError = validateUrl && value.isNotEmpty() && !URLUtil.isValidUrl(value)
    val showError = isError || isUrlError
    val displayErrorMessage =
        when {
            isUrlError -> "Please enter a valid URL"
            errorMessage != null -> errorMessage
            else -> null
        }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = if (isRequired) "$label *" else label,
                color = if (showError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
            )
        },
        modifier = textFieldModifier,
        enabled = enabled,
        readOnly = readOnly,
        singleLine = singleLine,
        shape = shape,
        isError = showError,
        maxLines = maxLines,
        minLines = minLines,
        leadingIcon = {
            if (leadingIcon != null) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = "Icon"
                )
            }
        },
        supportingText = displayErrorMessage?.let { { Text(it) } },
        keyboardOptions =
        KeyboardOptions(
            capitalization = capitalization,
            keyboardType = keyboardType,
            imeAction = imeAction
        )
    )
}

@Composable
fun PasswordTextInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    imeAction: ImeAction = ImeAction.Done,
    onImeAction: () -> Unit = {},
    supportingText: @Composable () -> Unit = {}
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(14.dp),
        label = {
            Text(
                text = label,
                style =
                MaterialTheme.typography.bodyMedium.copy(
                    color = PurpleGrey40
                )
            )
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        isError = isError,
        visualTransformation =
        if (passwordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        keyboardOptions =
        KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        keyboardActions =
        KeyboardActions(
            onNext = { onImeAction() },
            onDone = { onImeAction() }
        ),
        supportingText = supportingText,
        trailingIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter =
                        painterResource(
                            if (passwordVisible) {
                                R.drawable.ic_visibility_off
                            } else {
                                R.drawable.ic_visibility
                            }
                        ),
                        contentDescription =
                        if (passwordVisible) {
                            "Hide password"
                        } else {
                            "Show password"
                        }
                    )
                }
            }
        }

    )
}

/**
 * Reusable read-only text field component
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadOnlyTextField(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    contentDescription: String? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = { },
        label = { Text(label) },
        readOnly = true,
        enabled = false,
        modifier =
        modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        leadingIcon =
        leadingIcon?.let {
            {
                Icon(
                    imageVector = it,
                    contentDescription = contentDescription
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ValidatedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    contentDescription: String? = null,
    errorMessage: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    maxLines: Int = 1,
    enabled: Boolean = true
) {
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth(),
            isError = errorMessage != null,
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation,
            maxLines = maxLines,
            enabled = enabled,
            leadingIcon =
            leadingIcon?.let {
                {
                    Icon(
                        imageVector = it,
                        contentDescription = contentDescription
                    )
                }
            }
        )

        AnimatedVisibility(visible = errorMessage != null) {
            errorMessage?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }
        }
    }
}

@Composable
fun CustomClickableOutlinedTextField(
    modifier: Modifier = Modifier,
    placeHolder: String,
    trailingIcon: ImageVector,
    value: String,
    onClick: () -> Unit,
    isError: Boolean = false,
    supportingText:
        @Composable()
        () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    OutlinedTextField(
        readOnly = true,
        modifier = modifier,
        value = value,
        shape = MaterialTheme.shapes.small,
        isError = isError,
        supportingText = supportingText,
        onValueChange = {},
        placeholder = {
            Text(text = placeHolder)
        },
        label = {
            Text(text = placeHolder)
        },
        maxLines = 1,
        trailingIcon = {
            Icon(
                imageVector = trailingIcon,
                contentDescription = null
            )
        },
        interactionSource =
        interactionSource.also { interaction ->
            LaunchedEffect(key1 = interaction) {
                interaction.interactions.collect {
                    if (it is PressInteraction.Release) {
                        onClick.invoke()
                    }
                }
            }
        }
    )
}

@Composable
fun MultilineInputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: ImageVector? = null,
    contentDescription: String? = null,
    errorMessage: String? = null,
    minLines: Int = 4,
    maxLines: Int = 6,
    placeholder: String = "",
    maxChar: Int = 300
) {
    val charCount = value.length

    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                if (newValue.length <= maxChar) {
                    onValueChange(newValue)
                }
            },
            label = { Text(label) },
            placeholder = { Text(placeholder) },
            minLines = minLines,
            maxLines = maxLines,
            modifier =
            Modifier
                .fillMaxWidth()
                .heightIn(min = 120.dp),
            isError = errorMessage != null,
            leadingIcon =
            leadingIcon?.let {
                {
                    Icon(
                        imageVector = it,
                        contentDescription = contentDescription
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "$charCount/$maxChar",
            style = MaterialTheme.typography.bodySmall,
            color = if (charCount > maxChar * 0.9) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.align(Alignment.End)
        )

        AnimatedVisibility(visible = errorMessage != null) {
            errorMessage?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }
        }
    }
}

@Composable
fun OtpDigitBox(
    value: String,
    isFocused: Boolean,
    focusRequester: FocusRequester,
    onValueChanged: (String) -> Unit,
    onKeyEvent: (androidx.compose.ui.input.key.KeyEvent) -> Boolean = { false },
    onFocusChanged: (Boolean) -> Unit,
    isEnabled: Boolean = true
) {
    val borderColor =
        if (isFocused) {
            MaterialTheme.colorScheme.primary
        } else if (value.isNotEmpty()) {
            MaterialTheme.colorScheme.outline
        } else {
            MaterialTheme.colorScheme.surfaceVariant
        }

    TextField(
        value = value,
        onValueChange = onValueChanged,
        modifier =
        Modifier
            .size(56.dp)
            .focusRequester(focusRequester)
            .onFocusChanged { onFocusChanged(it.isFocused) }
            .onKeyEvent(onKeyEvent)
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            ),
        textStyle =
        androidx.compose.ui.text.TextStyle(
            textAlign = TextAlign.Center,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        ),
        keyboardOptions =
        KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(),
        singleLine = true,
        enabled = isEnabled,
        colors =
        TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(12.dp),
        maxLines = 1
    )
}
