package com.newton.commonUi.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.vector.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSelector(
    modifier: Modifier = Modifier,
    selectedValue: String,
    options: List<String>,
    onSelectionChanged: (String) -> Unit,
    label: String,
    leadingIcon: ImageVector? = null,
    contentDescription: String? = null
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
                leadingIcon =
                leadingIcon?.let {
                    {
                        Icon(
                            imageVector = it,
                            contentDescription = contentDescription
                        )
                    }
                },
                modifier =
                Modifier
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
