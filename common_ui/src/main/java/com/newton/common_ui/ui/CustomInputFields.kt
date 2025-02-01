package com.newton.common_ui.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.newton.common_ui.R

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
      TextField(
          value = inputText,
          onValueChange = onInputChanged,
          label = { Text(label) },
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


        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
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
                Row {
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
