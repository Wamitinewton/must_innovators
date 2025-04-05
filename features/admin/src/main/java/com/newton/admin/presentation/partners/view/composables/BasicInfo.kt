package com.newton.admin.presentation.partners.view.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.admin.presentation.partners.events.*
import com.newton.admin.presentation.partners.states.*
import com.newton.commonUi.ui.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicInfo(
    partnersState: AddPartnersState,
    onEvent: (AddPartnersEvent) -> Unit
) {
    val partnerTypeOptions =
        listOf(
            "TECH",
            "ACADEMIC",
            "COMMUNITY",
            "MEDIA",
            "CORPORATE"
        )
    CustomCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Basic Information",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = partnersState.partnerName,
                onValueChange = { onEvent.invoke(AddPartnersEvent.NameChange(it)) },
                label = { Text("Partner Name") },
                placeholder = { Text("e.g. Google Cloud") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Business,
                        contentDescription = null
                    )
                },
                singleLine = true,
                supportingText = {
                    partnersState.errors["name"]?.let {
                        Text(
                            it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))
            ExposedDropdownMenuBox(
                expanded = partnersState.partnerTypeExpanded,
                onExpandedChange = {
                    onEvent.invoke(AddPartnersEvent.IsPartnerTypeExpanded(it))
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = partnersState.partnerType,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Partner Type") },
                    placeholder = { Text("Select partner type") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Category,
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = partnersState.partnerTypeExpanded)
                    },
                    modifier =
                    Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    supportingText = {
                        partnersState.errors["partnerType"]?.let {
                            Text(
                                it,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                )

                ExposedDropdownMenu(
                    expanded = partnersState.partnerTypeExpanded,
                    onDismissRequest = {
                        onEvent.invoke(AddPartnersEvent.IsPartnerTypeExpanded(false))
                    }
                ) {
                    partnerTypeOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                onEvent.invoke(AddPartnersEvent.TypeChange(option))
                                onEvent.invoke(AddPartnersEvent.IsPartnerTypeExpanded(false))
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = partnersState.description,
                onValueChange = { onEvent.invoke(AddPartnersEvent.DescriptionChange(it)) },
                label = { Text("Description") },
                placeholder = { Text("Describe the partner and their contributions") },
                modifier =
                Modifier
                    .fillMaxWidth()
                    .heightIn(min = 120.dp),
                minLines = 3,
                supportingText = {
                    partnersState.errors["description"]?.let {
                        Text(
                            it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
        }
    }
}
