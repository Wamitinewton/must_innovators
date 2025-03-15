package com.newton.account.presentation.composables.update_profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ProfileHeader(
    firstName: String?,
    lastName: String?,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile",
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.Center),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .padding(4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit Profile Picture",
                modifier = Modifier.align(Alignment.Center),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = "$firstName $lastName",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
    )
}


@Composable
fun YearOfStudyDropdown(
    selectedYear: Int?,
    onYearSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val years = listOf(1, 2, 3, 4, 5)

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedYear?.toString() ?: "",
            onValueChange = { },
            label = { Text("Year of Study") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Year of Study",
                    modifier = Modifier.clickable { expanded = true }
                )
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(0.5f)
        ) {
            years.forEach { year ->
                DropdownMenuItem(
                    text = { Text("Year $year") },
                    onClick = {
                        onYearSelected(year)
                        expanded = false
                    }
                )
            }
        }
    }
}
