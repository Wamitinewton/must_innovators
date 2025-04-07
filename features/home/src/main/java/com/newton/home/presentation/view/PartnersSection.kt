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

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import coil3.compose.*
import coil3.request.*
import com.newton.commonUi.ui.*
import com.newton.home.presentation.states.*
import com.newton.network.domain.models.homeModels.*

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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        when (partnersState) {
            is PartnersUiState.Loading -> {
                LoadingIndicator(
                    text = "Loading partners...",
                )
            }

            is PartnersUiState.Error -> {
                ErrorScreen(
                    titleText = "Failed to load PARTNERS",
                    message = partnersState.message,
                    onRetry = onRetry
                )
            }

            is PartnersUiState.Empty -> {
                EmptyStateCard(
                    icon = Icons.Rounded.Groups,
                    title = "OOOPS",
                    message = "No Partners Found. Check back later",
                    buttonText = "Retry",
                    onActionClick = onRetry
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
}

@Composable
fun PartnerCard(
    partner: PartnersData,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cardWidth = 320.dp

    ElevatedCard(
        onClick = onClick,
        modifier = modifier
            .width(cardWidth)
            .animateContentSize()
            .padding(4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 8.dp,
            focusedElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            PartnerCardHeader(partner)

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = partner.description,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            AchievementsSection(partner.achievements)

            Spacer(modifier = Modifier.height(16.dp))

            PartnerCardFooter(partner)
        }
    }
}

@Composable
private fun PartnerCardHeader(partner: PartnersData) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Surface(
            shape = CircleShape,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
            color = MaterialTheme.colorScheme.surfaceContainerLow,
            modifier = Modifier.size(48.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(partner.logo)
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
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

            CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurfaceVariant) {
                Text(
                    text = buildAnnotatedString {
                        append(partner.type)
                        append(" • ")
                        append(partner.scope)
                    },
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))
        PartnerStatusBadge(status = partner.status)
    }
}

@Composable
private fun AchievementsSection(achievements: String) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = "Achievements",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = achievements,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun PartnerCardFooter(partner: PartnersData) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ContactInfoChip(
            icon = Icons.Filled.Person,
            label = partner.contact_person,
            contentDescription = "person",
            modifier = Modifier.weight(1f)
        )

        ContactInfoChip(
            icon = Icons.Filled.Email,
            label = partner.contact_email,
            contentDescription = "email",
            modifier = Modifier.weight(1f)
        )

        ContactInfoChip(
            icon = Icons.Filled.CalendarMonth,
            label = partner.start_date,
            contentDescription = "start date",
            modifier = Modifier.weight(0.8f)
        )
    }
}

@Composable
private fun ContactInfoChip(
    icon: ImageVector,
    label: String,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.surfaceContainerLowest,
        modifier = modifier.height(28.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun PartnerStatusBadge(status: String) {
    val (backgroundColor, textColor) = when (status.uppercase()) {
        "ACTIVE" -> Pair(
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.colorScheme.onPrimaryContainer
        )
        "PENDING" -> Pair(
            MaterialTheme.colorScheme.tertiaryContainer,
            MaterialTheme.colorScheme.onTertiaryContainer
        )
        else -> Pair(
            MaterialTheme.colorScheme.errorContainer,
            MaterialTheme.colorScheme.onErrorContainer
        )
    }

    Surface(
        shape = MaterialTheme.shapes.extraSmall,
        color = backgroundColor,
        contentColor = textColor,
        modifier = Modifier.height(24.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = status,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun PartnersSection(
    partners: List<PartnersData>,
    onPartnerClick: (PartnersData) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(
                items = partners,
                key = { partner -> partner.id }
            ) { partner ->
                PartnerCard(
                    partner = partner,
                    onClick = { onPartnerClick(partner) },
                )
            }
        }
    }
}
