package com.newton.communities.presentation.view.community_details.composables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.newton.core.domain.models.about_us.Community

@Composable
fun CommunityOverviewTab(community: Community) {
    var expandedDescription by remember { mutableStateOf(false) }

    val contentAlpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(1000),
        label = "ContentAlpha"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .alpha(contentAlpha),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Leadership Card
        item {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Leadership",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    LeadershipItem(
                        title = "Community Lead",
                        name = community.communityLead
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LeadershipItem(
                        title = "Co-Lead",
                        name = community.coLead
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LeadershipItem(
                        title = "Secretary",
                        name = community.secretary
                    )
                }
            }
        }

        // Description Card
        item {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "About",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = community.description,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = if (expandedDescription) Int.MAX_VALUE else 3,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = if (expandedDescription) "Read Less" else "Read More",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable { expandedDescription = !expandedDescription }
                    )
                }
            }
        }

        // Contact Information Card
        item {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Contact Information",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    ContactItem(
                        icon = Icons.Default.Email,
                        text = community.email
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ContactItem(
                        icon = Icons.Default.Call,
                        text = community.phoneNumber
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Social Media",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.secondary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(community.socialMedia) { socialMedia ->
                            SocialMediaButton(socialMedia)
                        }
                    }
                }
            }
        }

        item {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Community Metrics",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    MetricItem(
                        label = "Active Members",
                        value = (community.totalMembers * 0.7f).toInt(),
                        maxValue = community.totalMembers,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    MetricItem(
                        label = "Weekly Participation",
                        value = (community.totalMembers * 0.5f).toInt(),
                        maxValue = community.totalMembers,
                        color = MaterialTheme.colorScheme.secondary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    MetricItem(
                        label = "Project Completion",
                        value = 75,
                        maxValue = 100,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        }
    }
}
