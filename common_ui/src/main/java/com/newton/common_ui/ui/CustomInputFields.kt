package com.newton.common_ui.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.newton.common_ui.R
import com.newton.meruinnovators.ui.theme.PurpleGrey40

@Composable
fun DefaultTextInput(
    onInputChanged: (String) -> Unit,
    inputText: String,
    label: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {},
    onSubmitted: (() -> Unit)? = null
) {
      OutlinedTextField(
          value = inputText,
          shape = RoundedCornerShape(14.dp),
          onValueChange = onInputChanged,
          label = { Text(
              text = label,
              style = MaterialTheme.typography.bodyMedium.copy(
                  color = PurpleGrey40
              )
          ) },
          singleLine = true,
          modifier = modifier.fillMaxWidth(),
          isError = isError,
          keyboardOptions = KeyboardOptions(
              keyboardType = keyboardType,
              imeAction = imeAction
          ),
          keyboardActions = KeyboardActions(
              onNext = { onImeAction() },
              onDone = { onImeAction() }
          ),
      )
  }


@Composable
fun PasswordTextInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String? = null,
    imeAction: ImeAction = ImeAction.Done,
    onImeAction: () -> Unit = {}
) {
    var passwordVisible by remember { mutableStateOf(false) }


        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            shape = RoundedCornerShape(14.dp),
            label = { Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = PurpleGrey40
                )
            ) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = isError,
            visualTransformation = if (passwordVisible)
            VisualTransformation.None
            else
            PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onNext = { onImeAction() },
                onDone = { onImeAction() }
            ),
            trailingIcon = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (isError) {
                        Icon(
                            painter = painterResource(R.drawable.error),
                            contentDescription = "error",
                            modifier = modifier.padding(end = 8.dp)
                        )
                    }

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(
                                if (passwordVisible)
                                    R.drawable.ic_visibility_off
                                else
                                R.drawable.ic_visibility,

                            ),
                            contentDescription = if (passwordVisible)
                            "Hide password"

                            else
                            "Show password"
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
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        leadingIcon = leadingIcon?.let {
            {
                Icon(
                    imageVector = it,
                    contentDescription = contentDescription
                )
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            disabledTextColor = MaterialTheme.colorScheme.onSurface,
            disabledBorderColor = MaterialTheme.colorScheme.outline,
            disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
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
            leadingIcon = leadingIcon?.let {
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