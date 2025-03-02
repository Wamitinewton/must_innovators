package com.newton.communities.presentation.view.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.BrightnessMedium
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Computer
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material.icons.outlined.Smartphone
import androidx.compose.material.icons.outlined.ViewDay
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.newton.core.domain.models.about_us.Community
import com.newton.core.utils.generateColorFromName


@Composable
fun CommunityCard(
    community: Community,
    isExpanded: Boolean,
    onExpandToggle: () -> Unit,
    onSeeDetailsClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = if (isExpanded) 6.dp else 2.dp
        ),
        onClick = onExpandToggle,
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                generateColorFromName(community.name),
                                MaterialTheme.colorScheme.surface
                            )
                        )
                    )
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier.size(48.dp),
                    shape = CircleShape,
                    color = generateColorFromName(community.name).copy(alpha = 0.2f),
                    border = BorderStroke(2.dp, generateColorFromName(community.name))
                ) {
                    Icon(
                        imageVector = getIconForCommunity(community.name),
                        contentDescription = null,
                        tint = generateColorFromName(community.name),
                        modifier = Modifier
                            .padding(8.dp)
                            .size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = community.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "${community.totalMembers} members",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }

                val rotation by animateFloatAsState(
                    targetValue = if (isExpanded) 180f else 0f,
                    label = "Arrow Rotation"
                )
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    modifier = Modifier.rotate(rotation),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                    Text(
                        text = community.description,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    OutlinedButton(
                        onClick = onSeeDetailsClick,
                        modifier = Modifier.align(Alignment.End),
                        border = BorderStroke(1.dp, generateColorFromName(community.name))
                    ) {
                        Text(
                            text = "See details",
                            color = generateColorFromName(community.name)
                        )
                    }
                }
            }
        }
    }
}

/**
 * A composable that displays a list of community cards with an optional description.
 */
@Composable
fun CommunitiesList(
    communities: List<Community>,
    showDescription: Boolean,
    onSeeDetailsClick: (Int) -> Unit
) {
    var expandedCardId by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        AnimatedVisibility(
            visible = showDescription,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Text(
                text = "Explore our specialized communities that focus on different aspects of technology",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        communities.forEach { community ->
            val isExpanded = expandedCardId == community.id.toString()

            CommunityCard(
                community = community,
                isExpanded = isExpanded,
                onExpandToggle = {
                    expandedCardId = if (isExpanded) "" else community.id.toString()
                },
                onSeeDetailsClick = {
                    onSeeDetailsClick(community.id)
                }
            )
        }
    }
}

/**
 * Returns an appropriate icon based on the community name keywords.
 */
fun getIconForCommunity(name: String): ImageVector {
    return when {
        name.contains("Android", ignoreCase = true) ||
                name.contains("Mobile", ignoreCase = true) -> Icons.Outlined.Smartphone

        name.contains("Web", ignoreCase = true) ||
                name.contains("Frontend", ignoreCase = true) ||
                name.contains("Backend", ignoreCase = true) -> Icons.Outlined.Computer

        name.contains("Design", ignoreCase = true) ||
                name.contains("UI", ignoreCase = true) ||
                name.contains("UX", ignoreCase = true) -> Icons.Outlined.ViewDay

        name.contains("Machine Learning", ignoreCase = true) ||
                name.contains("AI", ignoreCase = true) ||
                name.contains("Data Science", ignoreCase = true) -> Icons.Outlined.Psychology

        name.contains("Graphics", ignoreCase = true) ||
                name.contains("Art", ignoreCase = true) ||
                name.contains("Animation", ignoreCase = true) -> Icons.Outlined.BrightnessMedium

        // Default icon for any other community
        else -> Icons.Outlined.Code
    }
}