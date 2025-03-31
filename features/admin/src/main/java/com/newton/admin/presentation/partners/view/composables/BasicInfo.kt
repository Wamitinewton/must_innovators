package com.newton.admin.presentation.partners.view.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Category
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newton.admin.presentation.partners.events.AddPartnersEvent
import com.newton.admin.presentation.partners.states.AddPartnersState
import com.newton.common_ui.ui.CustomCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicInfo(
    partnersState: AddPartnersState,
    onEvent: (AddPartnersEvent) -> Unit
) {
    val partnerTypeOptions = listOf(
        "TECH", "ACADEMIC", "COMMUNITY", "MEDIA", "CORPORATE"
    )
    CustomCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
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
                        contentDescription = null,
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
                            contentDescription = null,
                        )
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = partnersState.partnerTypeExpanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(), supportingText = {
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
                modifier = Modifier
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