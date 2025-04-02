package com.newton.home.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.newton.common_ui.ui.EmptyStateCard
import com.newton.common_ui.ui.ErrorScreen
import com.newton.common_ui.ui.LoadingIndicator
import com.newton.core.domain.models.home_models.PartnersData
import com.newton.home.presentation.states.PartnersUiState


@Composable
fun SectionHeader(
    title: String,
    showViewAll: Boolean = false,
    onViewAllClick: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
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
                modifier = Modifier
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
        modifier = Modifier
            .width(300.dp)
            .height(220.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = partner.logo,
                        contentDescription = "${partner.name} logo",
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
        modifier = Modifier
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
    val (backgroundColor, textColor) = when (status.uppercase()) {
        "ACTIVE" -> Pair(
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.colorScheme.primary
        )
        "PENDING" -> Pair(
            MaterialTheme.colorScheme.tertiaryContainer,
            MaterialTheme.colorScheme.tertiary
        )
        else -> Pair(
            MaterialTheme.colorScheme.errorContainer,
            MaterialTheme.colorScheme.error
        )
    }

    Box(
        modifier = Modifier
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


