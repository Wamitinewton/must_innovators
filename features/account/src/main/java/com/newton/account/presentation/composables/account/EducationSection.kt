package com.newton.account.presentation.composables.account

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.newton.core.domain.models.authModels.*

@Composable
fun EducationSection(user: UserData) {
    if (user.course == null && user.registration_no == null && user.year_of_study == null && user.graduation_year == null) return

    Card(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors =
        CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Box(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.School,
                        contentDescription = "Education",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Education",
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                user.course?.let {
                    EducationInfoRow(label = "Course", value = it)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                user.registration_no?.let {
                    EducationInfoRow(label = "Registration No", value = it)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                user.year_of_study?.let {
                    EducationInfoRow(label = "Year of Study", value = it.toString())
                    Spacer(modifier = Modifier.height(8.dp))
                }

                user.graduation_year?.let {
                    EducationInfoRow(label = "Graduation Year", value = it.toString())
                }
            }
        }
    }
}

@Composable
private fun EducationInfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
        )
    }
}
