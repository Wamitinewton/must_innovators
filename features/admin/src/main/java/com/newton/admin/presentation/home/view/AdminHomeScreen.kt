package com.newton.admin.presentation.home.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.navigation.*
import com.newton.admin.presentation.home.events.*
import com.newton.admin.presentation.home.view.composables.*
import com.newton.admin.presentation.home.viewModel.*
import com.newton.commonUi.composables.*
import com.newton.core.domain.models.admin.*
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
    val communityGroups =
        listOf(
            CommunityGroup("Sports", 120),
            CommunityGroup("Music", 80),
            CommunityGroup("Art", 150),
            CommunityGroup("Tech", 200)
        )

    val eventData =
        listOf(
            SampleEvent("Sports", 1),
            SampleEvent("Music", 3),
            SampleEvent("Art", 7),
            SampleEvent("Tech", 4),
            SampleEvent("Art", 10),
            SampleEvent("Tech", 4),
            SampleEvent("Art", 12),
            SampleEvent("Tech", 4)
        )
    val interactionData =
        List(30) { index ->
            val date = LocalDate.now().minusDays(29L - index)
            val formattedDate = date.format(DateTimeFormatter.ofPattern("MMM dd"))
            // Generate random interaction counts with weekly patterns
            val baseInteractions = 500
            val weekdayBoost = if (date.dayOfWeek.value <= 5) 200 else 0
            val random = (-50..50).random()
            val interactionCount = baseInteractions + weekdayBoost + random

            InteractionData(formattedDate, interactionCount)
        }
    val activeUsers =
        List(30) { index ->
            val date = LocalDate.now().minusDays(29L - index)
            val formattedDate = date.format(DateTimeFormatter.ofPattern("MMM dd"))
            // Generate slightly random but trending upward user counts
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
        var isWeeklyView by remember { mutableStateOf(false) }
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
//                    modifier = Modifier.weight(1f)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("View Mode:")
                            Switch(
                                checked = isWeeklyView,
                                onCheckedChange = { isWeeklyView = it },
                                thumbContent = {
                                    Text(
                                        if (isWeeklyView) "Weekly" else "Daily",
                                        fontSize = 10.sp
                                    )
                                }
                            )
                        }
                        InteractiveBarGraph(
                            data = interactionData,
                            isWeeklyView = isWeeklyView,
                            onTooltipChanged = { tooltipData = it }
                        )
                    }
                }
            }
        }
//            tooltipData?.let { tooltip ->
//                TooltipBox(
//                    tooltipData = tooltip,
//                    onDismiss = { tooltipData = null }
//                )
//            }
    }
}
