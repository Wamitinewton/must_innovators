package com.newton.common_ui.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSelector(
    modifier: Modifier = Modifier,
    selectedValue: String,
    options: List<String>,
    onSelectionChanged: (String) -> Unit,
    label: String,
    leadingIcon: ImageVector? = null,
    contentDescription: String? = null,
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedValue,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                leadingIcon = leadingIcon?.let {
                    {
                        Icon(
                            imageVector = it,
                            contentDescription = contentDescription
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                label = { Text(label) }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { options ->
                    DropdownMenuItem(
                        text = { Text(options) },
                        onClick = {
                            onSelectionChanged(options)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}