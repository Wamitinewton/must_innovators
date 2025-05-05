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
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.rounded.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import com.newton.home.domain.*

@Composable
fun FeaturesGrid(
    modifier: Modifier = Modifier,
    onFeatureClick: (String) -> Unit
) {
    val featureItems = listOf(
        FeatureItem(
            title = "Blogs",
            icon = Icons.AutoMirrored.Rounded.Article,
            color = MaterialTheme.colorScheme.primary,
            route = "blogs"
        ),
        FeatureItem(
            title = "Partners",
            icon = Icons.Rounded.Handshake,
            color = MaterialTheme.colorScheme.primary,
            route = "partners"
        ),
        FeatureItem(
            title = "Testimonials",
            icon = Icons.Rounded.FormatQuote,
            color = MaterialTheme.colorScheme.primary,
            route = "testimonials"
        ),
        FeatureItem(
            title = "Innovations",
            icon = Icons.Rounded.Lightbulb,
            color = MaterialTheme.colorScheme.primary,
            route = "innovations"
        ),
        FeatureItem(
            title = "Members",
            icon = Icons.Rounded.People,
            color = MaterialTheme.colorScheme.primary,
            route = "members"
        ),
        FeatureItem(
            title = "Club Payments",
            icon = Icons.Rounded.Payment,
            color = MaterialTheme.colorScheme.primary,
            route = "payments"
        ),
        FeatureItem(
            title = "Resources",
            icon = Icons.AutoMirrored.Rounded.MenuBook,
            color = MaterialTheme.colorScheme.primary,
            route = "resources"
        )
    )

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Explore",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        featureItems.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowItems.forEach { feature ->
                    CompactFeatureCard(
                        featureItem = feature,
                        onClick = { onFeatureClick(feature.route) },
                        modifier = Modifier.weight(1f)
                    )
                }

                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun CompactFeatureCard(
    featureItem: FeatureItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(70.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = featureItem.icon,
                    contentDescription = featureItem.title,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(18.dp)
                )
            }

            Text(
                text = featureItem.title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .weight(1f)
            )

            Icon(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                contentDescription = "Navigate to ${featureItem.title}",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
