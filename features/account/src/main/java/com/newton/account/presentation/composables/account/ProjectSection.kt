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
package com.newton.account.presentation.composables.account

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.*
import com.newton.network.domain.models.authModels.*

@Composable
fun ProjectsSection(projects: List<Project>?) {
    if (projects.isNullOrEmpty()) return

    var expanded by remember { mutableStateOf(false) }
    val uriHandler = LocalUriHandler.current

    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .animateContentSize(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.Code,
                        contentDescription = "Projects",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Projects",
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = if (expanded) "Show less" else "Show more"
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (expanded) {
                projects.forEach { project ->
                    ProjectCard(
                        project = project,
                        onLinkClick = { uriHandler.openUri(project.link) }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            } else {
                // Show only first project when collapsed
                projects.firstOrNull()?.let { project ->
                    ProjectCard(
                        project = project,
                        onLinkClick = { uriHandler.openUri(project.link) }
                    )
                }

                if (projects.size > 1) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "+ ${projects.size - 1} more projects",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable { expanded = true }
                    )
                }
            }
        }
    }
}

@Composable
fun ProjectCard(
    project: Project,
    onLinkClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(
                        brush =
                            Brush.linearGradient(
                                colors =
                                    listOf(
                                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
                                        MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.2f)
                                    )
                            )
                    )
                    .padding(16.dp)
        ) {
            Column {
                Text(
                    text = project.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = project.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Technologies:",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(4.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(project.technologies) { tech ->
                        SuggestionChip(
                            onClick = { },
                            colors =
                                SuggestionChipDefaults.suggestionChipColors(
                                    containerColor =
                                        MaterialTheme.colorScheme.tertiaryContainer.copy(
                                            alpha = 0.7f
                                        )
                                ),
                            label = {
                                Text(
                                    text = tech,
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onLinkClick,
                    modifier = Modifier.align(Alignment.End),
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Link,
                        contentDescription = "Visit project link"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("View Project")
                }
            }
        }
    }
}
