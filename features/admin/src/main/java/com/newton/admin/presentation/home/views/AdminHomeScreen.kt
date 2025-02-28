package com.newton.admin.presentation.home.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.newton.admin.domain.models.TooltipData
import com.newton.admin.presentation.home.events.AdminHomeEvent
import com.newton.admin.presentation.home.viewModel.AdminHomeViewModel
import com.newton.admin.presentation.home.views.composables.AddChoiceCard
import com.newton.admin.presentation.home.views.composables.BarGraph
import com.newton.admin.presentation.home.views.composables.DashboardCard
import com.newton.admin.presentation.home.views.composables.EventsPieChart
import com.newton.admin.presentation.home.views.composables.InteractiveBarGraph
import com.newton.admin.presentation.home.views.composables.InteractiveLineGraph
import com.newton.admin.presentation.home.views.composables.TooltipBox
import com.newton.common_ui.composables.MeruInnovatorsAppBar
import com.newton.common_ui.ui.ColumnWrapper
import com.newton.core.navigation.NavigationRoutes
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class CommunityGroup(val name: String, val members: Int)
data class ActiveUserData(val time: String, val users: Int)
data class EventData(val community: String, val events: Int)
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

    // Sample data for demonstration
    val communityGroups = listOf(
        CommunityGroup("Sports", 120),
        CommunityGroup("Music", 80),
        CommunityGroup("Art", 150),
        CommunityGroup("Tech", 200)
    )

    val activeUsersData = listOf(
        ActiveUserData("10:00", 100),
        ActiveUserData("10:05", 150),
        ActiveUserData("10:10", 130),
        ActiveUserData("10:15", 170),
        ActiveUserData("10:20", 160)
    )

    val eventData = listOf(
        EventData("Sports", 1),
        EventData("Music", 3),
        EventData("Art", 7),
        EventData("Tech", 4),
        EventData("Art", 10),
        EventData("Tech", 4),
        EventData("Art", 12),
        EventData("Tech", 4)
    )

    val dailyInteractions = listOf(
        InteractionData("Mon", 50),
        InteractionData("Tue", 70),
        InteractionData("Wed", 60),
        InteractionData("Thu", 90),
        InteractionData("Fri", 80),
        InteractionData("Sat", 100),
        InteractionData("Sun", 40)
    )

    val weeklyInteractions = listOf(
        InteractionData("Week 1", 400),
        InteractionData("Week 2", 450),
        InteractionData("Week 3", 500),
        InteractionData("Week 4", 480)
    )
    val interactionData = List(30) { index ->
        val date = LocalDate.now().minusDays(29L - index)
        val formattedDate = date.format(DateTimeFormatter.ofPattern("MMM dd"))
        // Generate random interaction counts with weekly patterns
        val baseInteractions = 500
        val weekdayBoost = if (date.dayOfWeek.value <= 5) 200 else 0
        val random = (-50..50).random()
        val interactionCount = baseInteractions + weekdayBoost + random

        InteractionData(formattedDate, interactionCount)
    }
    val activeUsers = List(30) { index ->
        val date = LocalDate.now().minusDays(29L - index)
        val formattedDate = date.format(DateTimeFormatter.ofPattern("MMM dd"))
        // Generate slightly random but trending upward user counts
        val baseUsers = 1000
        val trend = index * 10
        val random = (-50..50).random()
        val userCount = baseUsers + trend + random

        formattedDate to userCount
    }
    Scaffold(topBar = {
        MeruInnovatorsAppBar(title = "Admin Dashboard")
    },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent.invoke(AdminHomeEvent.Sheet(shown = true))
                },
                content = {
                    Icon(Icons.Filled.AddCircleOutline, contentDescription = "Add")
                }
            )
        }) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            var isWeeklyView by remember { mutableStateOf(false) }
            var tooltipData by remember { mutableStateOf<TooltipData?>(null) }
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 12.dp),
            ) {
                item {
                    DashboardCard(
                        title = "Community groups members statistics",
                    ) {
                        BarGraph(
                            communityGroups = communityGroups,
                            onTooltipChanged = { tooltipData = it }
                        )
                    }
                }
                item {
                    DashboardCard(
                        title = "Active Users Trend",
                    ) {
                        InteractiveLineGraph(
                            data = activeUsers,
                            onTooltipChanged = { tooltipData = it }
                        )
                    }
                }
                item {
                    DashboardCard(
                        title = "Events Distribution",
                    ) {
                        EventsPieChart(
                            events = eventData,
                            onTooltipChanged = { tooltipData = it }
                        )
                    }
                }
                item {
                    DashboardCard(
                        title = "Interactions",
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
            tooltipData?.let { tooltip ->
                TooltipBox(
                    tooltipData = tooltip,
                    onDismiss = { tooltipData = null }
                )
            }


            if (adminState.isShowChoices) {
                ModalBottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = { onEvent.invoke(AdminHomeEvent.Sheet(shown = false)) },
                    modifier = Modifier
                        .heightIn(min = 80.dp)
                        .padding(bottom = 16.dp)
                ) {
                    Text(
                        "What do you want to add? ",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    ColumnWrapper(
                        modifier = Modifier.heightIn(max = 640.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        AddChoiceCard(
                            text = "Add Events",
                            onclick = {
                                navController.navigate(NavigationRoutes.AddEvent.routes);
                                onEvent.invoke(AdminHomeEvent.Sheet(shown = false))
                            }
                        )
                        AddChoiceCard(
                            text = "Add Partners",
                            onclick = {
                                navController.navigate(NavigationRoutes.AddPartners.routes)
                                onEvent.invoke(AdminHomeEvent.Sheet(shown = false))
                            }
                        )
                        AddChoiceCard(
                            text = "Add Community",
                            onclick = {
                                navController.navigate(NavigationRoutes.AddCommunity.routes)
                                onEvent.invoke(AdminHomeEvent.Sheet(shown = false))
                            }
                        )
                        AddChoiceCard(
                            text = "Send NewsLetter",
                            onclick = {
                                navController.navigate(NavigationRoutes.NewsLetterScreen.routes)
                                onEvent.invoke(AdminHomeEvent.Sheet(shown = false))
                            }
                        )
                        Spacer(Modifier.height(50.dp))
                    }
                }
            }
        }
    }
}
