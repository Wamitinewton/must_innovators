package com.newton.account.presentation.composables.update_profile

import android.webkit.URLUtil.isValidUrl
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.newton.common_ui.ui.FlowRow
import com.newton.core.domain.models.auth_models.Project

@Composable
fun ProjectsList(
    projects: List<Project>,
    onProjectUpdated: (Int, Project) -> Unit,
    onProjectRemoved: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        projects.forEachIndexed { index, project ->
            ProjectItem(
                project = project,
                onProjectUpdated = { updatedProject ->
                    onProjectUpdated(index, updatedProject)
                },
                onProjectRemoved = { onProjectRemoved(index) }
            )
        }
    }
}


@Composable
fun ProjectItem(
    project: Project,
    onProjectUpdated: (Project) -> Unit,
    onProjectRemoved: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = spring(stiffness = Spring.StiffnessLow)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = project.name,
                    onValueChange = {
                        onProjectUpdated(project.copy(name = it))
                    },
                    label = { Text("Project Name") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )

                IconButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = if (expanded) "Collapse" else "Expand",
                        modifier = Modifier.rotate(rotationState)
                    )
                }
            }

            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    // Description field
                    OutlinedTextField(
                        value = project.description,
                        onValueChange = {
                            onProjectUpdated(project.copy(description = it))
                        },
                        label = { Text("Description") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences
                        ),
                        maxLines = 4
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ProjectTechnologies(
                        technologies = project.technologies,
                        onTechnologiesUpdated = {
                            onProjectUpdated(project.copy(technologies = it))
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = project.link,
                        onValueChange = {
                            onProjectUpdated(project.copy(link = it))
                        },
                        label = { Text("Project Link") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Uri
                        ),
                        isError = project.link.isNotEmpty() && !isValidUrl(project.link),
                        supportingText = {
                            if (project.link.isNotEmpty() && !isValidUrl(project.link)) {
                                Text("Please enter a valid URL")
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedButton(
                        onClick = onProjectRemoved,
                        modifier = Modifier.align(Alignment.End),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.error)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Project",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Text("Remove Project")
                    }
                }
            }
        }
    }
}

@Composable
fun ProjectTechnologies(
    technologies: List<String>,
    onTechnologiesUpdated: (List<String>) -> Unit
) {
    var newTech by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Technologies",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = newTech,
            onValueChange = { newTech = it },
            label = { Text("Add Technology") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (newTech.isNotBlank() && !technologies.contains(newTech.trim())) {
                            val updatedList = technologies.toMutableList().apply {
                                add(newTech.trim())
                            }
                            onTechnologiesUpdated(updatedList)
                            newTech = ""
                        }
                    },
                    enabled = newTech.isNotBlank()
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Technology"
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (technologies.isNotEmpty()) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),

            ) {
                technologies.forEach { tech ->
                    Chip(
                        text = tech,
                        onRemove = {
                            val updatedList = technologies.toMutableList().apply {
                                remove(tech)
                            }
                            onTechnologiesUpdated(updatedList)
                        }
                    )
                }
            }
        } else {
            Text(
                text = "No technologies added yet",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

