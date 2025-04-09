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
package com.newton.events.presentation.view.eventList

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.newton.core.enums.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsTopBar(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 3.dp,
        shadowElevation = 3.dp
    ) {
        Column {
            TopAppBar(
                title = {
                    Text(
                        text = "Events",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Events"
                        )
                    }

                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = "Filter Events"
                        )
                    }
                }
            )

            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary,
                indicator = { tabPosition ->
                    if (selectedTabIndex < tabPosition.size) {
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPosition[selectedTabIndex]),
                            height = 3.dp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            ) {
                EventCategory.entries.forEachIndexed { index, category ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { onTabSelected(index) },
                        text = {
                            Text(
                                text = category.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = when (category) {
                                    EventCategory.UPCOMING -> Icons.Default.Event
                                    EventCategory.PAST -> Icons.Default.History
                                },
                                contentDescription = category.title
                            )
                        }
                    )
                }
            }
        }
    }
}
