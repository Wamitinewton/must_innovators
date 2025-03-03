package com.newton.admin.presentation.view.setings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AdminSettingsScreen(modifier: Modifier = Modifier) {
    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {

        }
    }
}
//
//
//// Custom color palette
//object DashboardColors {
//    val primary = Color(0xFF2196F3)
//    val secondary = Color(0xFF4CAF50)
//    val accent = Color(0xFFFFC107)
//    val chartColors = listOf(
//        Color(0xFF1976D2),
//        Color(0xFF388E3C),
//        Color(0xFFF57C00),
//        Color(0xFF7B1FA2),
//        Color(0xFFC2185B)
//    )
//}
//
//// Tooltip data class
//data class TooltipData(
//    val title: String,
//    val value: String,
//    val position: Offset
//)
//
//@Composable
//fun CommunityDashboard(
//    communityGroups: List<CommunityGroup>,
//    activeUsers: List<Pair<String, Int>>,
//    eventData: List<EventData>,
//    interactionData: List<InteractionData>
//) {
//    var isWeeklyView by remember { mutableStateOf(false) }
//    var tooltipData by remember { mutableStateOf<TooltipData?>(null) }
//    Box(modifier = Modifier.fillMaxSize()) {
//        LazyColumn {
//            // Header
////            item {
////                Card(
////                    modifier = Modifier
////                        .fillMaxWidth()
////                        .fillMaxHeight()
////                        .padding(bottom = 16.dp),
////                    colors = CardDefaults.cardColors(
////                        containerColor = DashboardColors.primary.copy(alpha = 0.1f)
////                    )
////                ) {
////                    Row(
////                        modifier = Modifier
////                            .padding(16.dp)
////                            .fillMaxWidth(),
////                        horizontalArrangement = Arrangement.SpaceBetween
////                    ) {
////                        Text(
////                            text = "Community Dashboard",
////                            style = MaterialTheme.typography.headlineMedium
////                        )
////                        Text(
////                            text = "Total Active Users: ${activeUsers.lastOrNull()?.second ?: 0}",
////                            style = MaterialTheme.typography.titleMedium
////                        )
////                    }
////                }
////            }
//            // Members Bar-graph
//            item {
//                DashboardCard(
//                    title = "Community Groups",
////                    modifier = Modifier.weight(1f)
//                ) {
//                    EnhancedMemberCountBarGraph(
//                        communityGroups = communityGroups,
//                        onTooltipChanged = { tooltipData = it }
//                    )
//                }
//            }
//            // users App interactions
//            item {
//                DashboardCard(
//                    title = "Active Users Trend",
////                    modifier = Modifier.weight(1f)
//                ) {
//                    EnhancedActiveUsersLineGraph(
//                        data = activeUsers,
//                        onTooltipChanged = { tooltipData = it }
//                    )
//                }
//            }
//            // chart as per the number of events in the app
//            item {
//                DashboardCard(
//                    title = "Events Distribution",
////                    modifier = Modifier.weight(1f)
//                ) {
//                    EnhancedEventsPieChart(
//                        events = eventData,
//                        onTooltipChanged = { tooltipData = it }
//                    )
//                }
//            }
//            // interactions bar
//            item {
//                DashboardCard(
//                    title = "Interactions",
////                    modifier = Modifier.weight(1f)
//                ) {
//                    Column {
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.SpaceBetween
//                        ) {
//                            Text("View Mode:")
//                            Switch(
//                                checked = isWeeklyView,
//                                onCheckedChange = { isWeeklyView = it },
//                                thumbContent = {
//                                    Text(
//                                        if (isWeeklyView) "Weekly" else "Daily",
//                                        fontSize = 10.sp
//                                    )
//                                }
//                            )
//                        }
//                        EnhancedInteractionsBarGraph(
//                            data = interactionData,
//                            isWeeklyView = isWeeklyView,
//                            onTooltipChanged = { tooltipData = it }
//                        )
//                    }
//                }
//            }
//            // tooltip
//            item {
//                tooltipData?.let { tooltip ->
//                    TooltipBox(
//                        tooltipData = tooltip,
//                        onDismiss = { tooltipData = null }
//                    )
//                }
//            }
//        }
//
//    }
//
//}
//
//@Composable
//fun DashboardCard(
//    title: String,
//    modifier: Modifier = Modifier,
//    content: @Composable () -> Unit,
//) {
//    Card(
//        modifier = modifier
//            .padding(8.dp)
//            .height(300.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .padding(16.dp)
//        ) {
//            Text(
//                text = title,
//                style = MaterialTheme.typography.titleMedium,
//                modifier = Modifier.padding(bottom = 16.dp)
//            )
//            content()
//        }
//    }
//}
//
//@Composable
//private fun TooltipBox(
//    tooltipData: TooltipData,
//    onDismiss: () -> Unit
//) {
//    Card(
//        modifier = Modifier
//            .offset(
//                x = tooltipData.position.x.dp,
//                y = tooltipData.position.y.dp
//            )
//            .padding(8.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
//    ) {
//        Column(
//            modifier = Modifier
//                .padding(8.dp)
//        ) {
//            Text(
//                text = tooltipData.title,
//                style = MaterialTheme.typography.titleSmall
//            )
//            Text(
//                text = tooltipData.value,
//                style = MaterialTheme.typography.bodyMedium
//            )
//        }
//    }
//}
//
//@Composable
//fun EnhancedMemberCountBarGraph(
//    communityGroups: List<CommunityGroup>,
//    onTooltipChanged: (TooltipData?) -> Unit
//) {
//    var hoveredBar by remember { mutableStateOf<Int?>(null) }
//
//
//    Canvas(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(top = 16.dp)
//            .pointerInput(Unit) {
//                detectTapGestures(
//                    onPress = { offset ->
//                        val barWidth = size.width / communityGroups.size - 10f
//                        val barIndex = (offset.x / (barWidth + 10f)).toInt()
//
//                        if (barIndex in communityGroups.indices) {
//                            val group = communityGroups[barIndex]
//                            onTooltipChanged(
//                                TooltipData(
//                                    title = group.name,
//                                    value = "${group.name} members",
//                                    position = offset
//                                )
//                            )
//                            hoveredBar = barIndex
//                        }
//                    },
//                    onTap = { hoveredBar = null }
//                )
//            }
//    ) {
//        val maxCount = communityGroups.maxOfOrNull { it.members }?.toFloat() ?: 0f
//        val barWidth = size.width / communityGroups.size - 10f
//
//        communityGroups.forEachIndexed { index, group ->
//            val barHeight = (group.members / maxCount) * size.height
//            val x = index * (barWidth + 10f)
//
//            // Enhanced animation with spring
////            val animatedHeight by animateFloatAsState(
////                targetValue = barHeight,
////                animationSpec = spring(
////                    dampingRatio = Spring.DampingRatioMediumBouncy,
////                    stiffness = Spring.StiffnessLow
////                )
////            )
//
//            // Bar color with hover effect
//            val barColor = if (index == hoveredBar) {
//                DashboardColors.chartColors[index % DashboardColors.chartColors.size]
//                    .copy(alpha = 0.8f)
//            } else {
//                DashboardColors.chartColors[index % DashboardColors.chartColors.size]
//            }
//
//            drawRect(
//                color = barColor,
//                topLeft = Offset(x, size.height - barHeight),
//                size = Size(barWidth, barHeight)
//            )
//        }
//    }
//}
//
//@Composable
//private fun EnhancedActiveUsersLineGraph(
//    data: List<Pair<String, Int>>,
//    onTooltipChanged: (TooltipData?) -> Unit
//) {
//    var hoveredPoint by remember { mutableStateOf<Int?>(null) }
//
//    Canvas(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(top = 16.dp)
//            .pointerInput(Unit) {
//                detectTapGestures(
//                    onPress = { offset ->
//                        val xStep = size.width / (data.size - 1)
//                        val pointIndex = (offset.x / xStep).toInt()
//
//                        if (pointIndex in data.indices) {
//                            val (date, count) = data[pointIndex]
//                            onTooltipChanged(
//                                TooltipData(
//                                    title = date,
//                                    value = "$count active users",
//                                    position = offset
//                                )
//                            )
//                            hoveredPoint = pointIndex
//                        }
//                    },
//                    onTap = { hoveredPoint = null }
//                )
//            }
//    ) {
//        val maxUsers = data.maxOfOrNull { it.second }?.toFloat() ?: 0f
//        val xStep = size.width / (data.size - 1)
//
//        // Animated path
////        val animatedProgress by animateFloatAsState(
////            targetValue = 1f,
////            animationSpec = tween(durationMillis = 1500)
////        )
//
//        val path = Path()
//        data.forEachIndexed { index, (_, count) ->
//            val x = index * xStep
//            val y = size.height - (count / maxUsers) * size.height
//
//            if (index == 0) {
//                path.moveTo(x, y)
//            } else {
//                val prevX = (index - 1) * xStep
//                val prevY = size.height - (data[index - 1].second / maxUsers) * size.height
//
//                // Bezier curve for smooth line
//                val controlX1 = prevX + (x - prevX) * 0.5f
//                path.cubicTo(
//                    controlX1, prevY,
//                    controlX1, y,
//                    x, y
//                )
//            }
//
//            // Draw points with hover effect
//            if (index == hoveredPoint) {
//                drawCircle(
//                    color = DashboardColors.accent,
//                    radius = 8f,
//                    center = Offset(x, y)
//                )
//            } else {
//                drawCircle(
//                    color = DashboardColors.secondary,
//                    radius = 4f,
//                    center = Offset(x, y)
//                )
//            }
//        }
//
//        // Draw animated path
//        drawPath(
//            path = path,
//            color = DashboardColors.secondary,
//            style = Stroke(
//                width = 3f,
//                pathEffect = PathEffect.cornerPathEffect(10f)
//            ),
//            alpha = 1f
//        )
//    }
//}
//// [Previous code remains the same until EnhancedEventsPieChart]
//
//@Composable
//private fun EnhancedEventsPieChart(
//    events: List<EventData>,
//    onTooltipChanged: (TooltipData?) -> Unit,
//) {
//    var selectedSegment by remember { mutableStateOf<Int?>(null) }
//    var rotationAngle by remember { mutableStateOf(0f) }
//
//    LaunchedEffect(Unit) {
//        // Initial animation
//        rotationAngle = 360f
//    }
//
//    Canvas(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//            .pointerInput(Unit) {
//                detectTapGestures(
////                    onPress = { offset ->
////                        val center = Offset(size.width / 2, size.height / 2)
////                        val radius = size.minDimension / 2
////
////                        if (offset.distanceTo(center) <= radius) {
////                            val angle = (atan2(
////                                offset.y - center.y,
////                                offset.x - center.x
////                            ) * 180 / PI).toFloat()
////
////                            var currentAngle = 0f
////                            events.forEachIndexed { index, event ->
////                                val sweepAngle = (event.eventCount.toFloat() /
////                                        events.sumOf { it.eventCount }) * 360f
////
////                                if (angle >= currentAngle &&
////                                    angle <= currentAngle + sweepAngle) {
////                                    selectedSegment = index
////                                    onTooltipChanged(
////                                        TooltipData(
////                                            title = event.communityName,
////                                            value = "${event.eventCount} events",
////                                            position = offset
////                                        )
////                                    )
////                                    return@detectTapGestures
////                                }
////                                currentAngle += sweepAngle
////                            }
////                        }
////                    },
//                    onTap = {
//                        selectedSegment = null
//                        onTooltipChanged(null)
//                    }
//                )
//            }
//    ) {
//        val center = Offset(size.width / 2, size.height / 2)
////        val animatedRotation by animateFloatAsState(
////            targetValue = rotationAngle,
////            animationSpec = spring(
////                dampingRatio = Spring.DampingRatioMediumBouncy,
////                stiffness = Spring.StiffnessLow
////            )
////        )
//
//        val total = events.sumOf { it.members }.toFloat()
//        var startAngle = rotationAngle
//
//        events.forEachIndexed { index, event ->
//            val sweepAngle = (event.members / total) * 360f
//            val isSelected = selectedSegment == index
//
//            // Animated radius for selected segment
////            val radius by animateFloatAsState(
////                targetValue = if (isSelected) {
////                    size.minDimension / 2 * 1.1f
////                } else {
////                    size.minDimension / 2
////                },
////                animationSpec = spring(
////                    dampingRatio = Spring.DampingRatioMediumBouncy,
////                    stiffness = Spring.StiffnessLow
////                )
////            )
//
//            // Draw segment
//            drawArc(
//                color = DashboardColors.chartColors[index % DashboardColors.chartColors.size],
//                startAngle = startAngle,
//                sweepAngle = sweepAngle,
//                useCenter = true,
//                size = Size(size.minDimension * 2, size.minDimension * 2),
//                topLeft = Offset(
//                    center.x - size.minDimension,
//                    center.y - size.minDimension
//                )
//            )
//
//            // Draw percentage label
//            if (sweepAngle > 30) {  // Only draw label if segment is large enough
//                val labelRadius = size.minDimension * 0.7f
//                val labelAngle = startAngle + sweepAngle / 2
//                val labelX = center.x + cos(labelAngle * PI / 180f).toFloat() * labelRadius
//                val labelY = center.y + sin(labelAngle * PI / 180f).toFloat() * labelRadius
//
//                drawContext.canvas.nativeCanvas.apply {
//                    drawText(
//                        "${(event.members / total * 100).toInt()}%",
//                        labelX,
//                        labelY,
//                        Paint().apply {
//                            color = android.graphics.Color.WHITE
//                            textSize = 30f
//                            textAlign = Paint.Align.CENTER
//                        }
//                    )
//                }
//            }
//
//            startAngle += sweepAngle
//        }
//    }
//}
//
//@Composable
//private fun EnhancedInteractionsBarGraph(
//    data: List<InteractionData>,
//    isWeeklyView: Boolean,
//    onTooltipChanged: (TooltipData?) -> Unit
//) {
//    var hoveredBar by remember { mutableStateOf<Int?>(null) }
//
//    val groupedData = if (isWeeklyView) {
//        data.chunked(7).map { weekData ->
//            InteractionData(
//                date = "${weekData.first().date} - ${weekData.last().date}",
//                counts = weekData.sumOf { it.counts }
//            )
//        }
//    } else {
//        data
//    }
//
//    Canvas(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(top = 16.dp)
//            .pointerInput(Unit) {
//                detectTapGestures(
//                    onPress = { offset ->
//                        val barWidth = size.width / groupedData.size - 10f
//                        val barIndex = (offset.x / (barWidth + 10f)).toInt()
//
//                        if (barIndex in groupedData.indices) {
//                            val interaction = groupedData[barIndex]
//                            onTooltipChanged(
//                                TooltipData(
//                                    title = interaction.date,
//                                    value = "${interaction.counts} interactions",
//                                    position = offset
//                                )
//                            )
//                            hoveredBar = barIndex
//                        }
//                    },
//                    onTap = {
//                        hoveredBar = null
//                        onTooltipChanged(null)
//                    }
//                )
//            }
//    ) {
//        val maxCount = groupedData.maxOfOrNull { it.counts }?.toFloat() ?: 0f
//        val barWidth = size.width / groupedData.size - 10f
//
//        groupedData.forEachIndexed { index, data ->
//            val barHeight = (data.counts / maxCount) * size.height
//            val x = index * (barWidth + 10f)
//
//            // Enhanced animation with spring effect
////            val animatedHeight by animateFloatAsState(
////                targetValue = barHeight,
////                animationSpec = spring(
////                    dampingRatio = Spring.DampingRatioMediumBouncy,
////                    stiffness = Spring.StiffnessLow
////                )
////            )
//
//            // Bar color with hover effect
//            val barColor = if (index == hoveredBar) {
//                DashboardColors.accent
//            } else {
//                DashboardColors.primary
//            }
//
//            // Draw bar with rounded corners
//            drawRoundRect(
//                color = barColor,
//                topLeft = Offset(x, size.height - barHeight),
//                size = Size(barWidth, barHeight),
//                cornerRadius = CornerRadius(8f, 8f)
//            )
//
//            // Draw value label on top of bar
//            if (index == hoveredBar) {
//                drawContext.canvas.nativeCanvas.apply {
//                    drawText(
//                        data.counts.toString(),
//                        x + barWidth / 2,
//                        size.height - barHeight - 10,
//                        Paint().apply {
//                            color = android.graphics.Color.BLACK
//                            textSize = 24f
//                            textAlign = Paint.Align.CENTER
//                        }
//                    )
//                }
//            }
//        }
//    }
//}
//
//// Function to generate test data
//fun generateDashboardTestData(): TestData {
//    // Community Groups Data
//    val communityGroups = listOf(
//        CommunityGroup("Android Developers", 1250),
//        CommunityGroup("iOS Engineers", 980),
//        CommunityGroup("Web Frontend", 1500),
//        CommunityGroup("Backend Dev", 1100),
//        CommunityGroup("Data Science", 850),
//        CommunityGroup("DevOps", 750),
//        CommunityGroup("UI/UX Design", 650)
//    )
//
//    // Active Users Data (last 30 days)
//    val activeUsers = List(30) { index ->
//        val date = LocalDate.now().minusDays(29L - index)
//        val formattedDate = date.format(DateTimeFormatter.ofPattern("MMM dd"))
//        // Generate slightly random but trending upward user counts
//        val baseUsers = 1000
//        val trend = index * 10
//        val random = (-50..50).random()
//        val userCount = baseUsers + trend + random
//
//        formattedDate to userCount
//    }
//
//    // Event Data
//    val eventData = listOf(
//        EventData("Android Developers", 45),
//        EventData("iOS Engineers", 38),
//        EventData("Web Frontend", 52),
//        EventData("Backend Dev", 41),
//        EventData("Data Science", 33),
//        EventData("DevOps", 29),
//        EventData("UI/UX Design", 27)
//    )
//
//    // Interaction Data (last 30 days)
//    val interactionData = List(30) { index ->
//        val date = LocalDate.now().minusDays(29L - index)
//        val formattedDate = date.format(DateTimeFormatter.ofPattern("MMM dd"))
//        // Generate random interaction counts with weekly patterns
//        val baseInteractions = 500
//        val weekdayBoost = if (date.dayOfWeek.value <= 5) 200 else 0
//        val random = (-50..50).random()
//        val interactionCount = baseInteractions + weekdayBoost + random
//
//        InteractionData(formattedDate, interactionCount)
//    }
//
//    return TestData(
//        communityGroups = communityGroups,
//        activeUsers = activeUsers,
//        eventData = eventData,
//        interactionData = interactionData
//    )
//}
//
//// Data class to hold all test data
//data class TestData(
//    val communityGroups: List<CommunityGroup>,
//    val activeUsers: List<Pair<String, Int>>,
//    val eventData: List<EventData>,
//    val interactionData: List<InteractionData>
//)
