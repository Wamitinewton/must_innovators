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
package com.newton.admin.presentation.home.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.*
import androidx.navigation.*
import com.newton.admin.presentation.home.events.*
import com.newton.admin.presentation.home.view.composables.*
import com.newton.admin.presentation.home.viewModel.*
import com.newton.commonUi.ui.*
import com.newton.network.domain.models.admin.*
import java.time.*
import java.time.format.*

data class CommunityGroup(val name: String, val members: Int)
data class SampleEvent(val community: String, val events: Int)
data class InteractionData(val day: String, val intensity: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminHome(
    viewModel: AdminHomeViewModel,
    onEvent: (AdminHomeEvent) -> Unit,
    navController: NavController
) {
    val adminState by viewModel.adminState.collectAsState()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var routeToNavigate by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(routeToNavigate) {
        routeToNavigate?.let { route ->
            navController.navigate(route)
            onEvent(AdminHomeEvent.Sheet(shown = false))
            routeToNavigate = null
        }
    }

    // Sample data for demonstration
    val communityGroups = listOf(
        CommunityGroup("Sports", 120),
        CommunityGroup("Music", 80),
        CommunityGroup("Art", 150),
        CommunityGroup("Tech", 200)
    )

    val eventData = listOf(
        SampleEvent("Sports", 1),
        SampleEvent("Music", 3),
        SampleEvent("Art", 7),
        SampleEvent("Tech", 4),
        SampleEvent("Art", 10),
        SampleEvent("Tech", 4),
        SampleEvent("Art", 12),
        SampleEvent("Tech", 4)
    )
    val interactionData = List(30) { index ->
        val date = LocalDate.now().minusDays(29L - index)
        val formattedDate = date.format(DateTimeFormatter.ofPattern("MMM dd"))
        val baseInteractions = 500
        val weekdayBoost = if (date.dayOfWeek.value <= 5) 200 else 0
        val random = (-50..50).random()
        val interactionCount = baseInteractions + weekdayBoost + random

        InteractionData(formattedDate, interactionCount)
    }
    val activeUsers = List(30) { index ->
        val date = LocalDate.now().minusDays(29L - index)
        val formattedDate = date.format(DateTimeFormatter.ofPattern("MMM dd"))
        val baseUsers = 1000
        val trend = index * 10
        val random = (-50..50).random()
        val userCount = baseUsers + trend + random

        formattedDate to userCount
    }
    DefaultScaffold(
        topBar = {
            MeruInnovatorsAppBar(title = "Admin Dashboard")
        }
    ) {
        var tooltipData by remember { mutableStateOf<ToolTipData?>(null) }
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 12.dp)
        ) {
            item {
                DashboardCard(
                    title = "Community groups members statistics"
                ) {
                    BarGraph(
                        communityGroups = communityGroups,
                        onTooltipChanged = { tooltipData = it }
                    )
                }
            }
            item {
                DashboardCard(
                    title = "Active Users Trend"
                ) {
                    InteractiveLineGraph(
                        data = activeUsers,
                        onTooltipChanged = { tooltipData = it }
                    )
                }
            }
            item {
                DashboardCard(
                    title = "Events Distribution"
                ) {
                    EventsPieChart(
                        events = eventData,
                        onTooltipChanged = { tooltipData = it }
                    )
                }
            }
            item {
                DashboardCard(
                    title = "Interactions"
                ) {
                    InteractiveBarGraph(
                        data = interactionData
                    )
                }
            }
        }
    }
}
