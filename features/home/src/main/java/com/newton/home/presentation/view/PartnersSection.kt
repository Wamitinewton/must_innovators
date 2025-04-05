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
package com.newton.home.presentation.view

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
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import coil3.compose.*
import com.newton.commonUi.ui.*
import com.newton.core.domain.models.homeModels.*
import com.newton.home.presentation.states.*

@Composable
fun SectionHeader(
    title: String,
    showViewAll: Boolean = false,
    onViewAllClick: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        if (showViewAll) {
            Box(
                modifier =
                Modifier
                    .clip(MaterialTheme.shapes.large)
                    .clickable(onClick = onViewAllClick)
                    .background(color = MaterialTheme.colorScheme.secondaryContainer)
                    .padding(horizontal = 12.dp, vertical = 5.dp)
            ) {
                Text(
                    text = "ALL",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}

@Composable
fun PartnersContent(
    partnersState: PartnersUiState,
    onRetry: () -> Unit,
    onPartnerClick: (PartnersData) -> Unit
) {
    when (partnersState) {
        is PartnersUiState.Loading -> {
            LoadingIndicator(text = "Loading Partners....")
        }

        is PartnersUiState.Error -> {
            ErrorScreen(
                titleText = "Failed to load PARTNERS",
                message = partnersState.message,
                onRetry = {
                    onRetry()
                }
            )
        }

        is PartnersUiState.Empty -> {
            EmptyStateCard(
                icon = Icons.Outlined.Groups,
                title = "No Partners Found",
                message = "There are no partners available at the moment. Please check back later.",
                buttonText = "Refresh",
                onActionClick = {
                    onRetry()
                }
            )
        }

        is PartnersUiState.Success -> {
            PartnersSection(
                partners = partnersState.partners,
                onPartnerClick = onPartnerClick
            )
        }
    }
}

@Composable
fun PartnerCard(
    partner: PartnersData,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier =
        Modifier
            .width(300.dp)
            .height(220.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation =
        CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        ),
        colors =
        CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier =
                    Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = partner.logo,
                        contentDescription = "${partner.name} logo",
                        contentScale = ContentScale.Crop,
                        modifier =
                        Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = partner.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = "${partner.type} • ${partner.scope}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                StatusBadge(status = partner.status)
            }

            Text(
                text = partner.description,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "Key Achievements",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = partner.achievements,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant,
                thickness = 0.5.dp
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CompactInfoItem(
                    icon = Icons.Filled.Person,
                    text = partner.contact_person,
                    modifier = Modifier.weight(1f)
                )

                CompactInfoItem(
                    icon = Icons.Filled.Email,
                    text = partner.contact_email,
                    modifier = Modifier.weight(1f)
                )

                CompactInfoItem(
                    icon = Icons.Filled.CalendarMonth,
                    text = partner.start_date,
                    modifier = Modifier.weight(0.8f)
                )
            }
        }
    }
}

@Composable
fun CompactInfoItem(
    icon: ImageVector,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(vertical = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun PartnersSection(
    partners: List<PartnersData>,
    onPartnerClick: (PartnersData) -> Unit = {}
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        items(partners.size) { index ->
            val partner = partners[index]
            PartnerCard(
                partner = partner,
                onClick = { onPartnerClick(partner) }
            )
        }
    }
}

@Composable
fun StatusBadge(status: String) {
    val (backgroundColor, textColor) =
        when (status.uppercase()) {
            "ACTIVE" ->
                Pair(
                    MaterialTheme.colorScheme.primaryContainer,
                    MaterialTheme.colorScheme.primary
                )

            "PENDING" ->
                Pair(
                    MaterialTheme.colorScheme.tertiaryContainer,
                    MaterialTheme.colorScheme.tertiary
                )

            else ->
                Pair(
                    MaterialTheme.colorScheme.errorContainer,
                    MaterialTheme.colorScheme.error
                )
        }

    Box(
        modifier =
        Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = status,
            style = MaterialTheme.typography.labelMedium,
            color = textColor
        )
    }
}
