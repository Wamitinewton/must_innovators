package com.newton.admin.presentation.actions.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import androidx.navigation.*
import com.newton.commonUi.composables.*
import com.newton.commonUi.ui.*
import com.newton.navigation.*

val actionList: List<NavItem> =
    listOf(
        NavItem("Add Partners", NavigationRoutes.AddPartners.routes),
        NavItem("Add Community", NavigationRoutes.AddCommunity.routes),
        NavItem("Add Event", NavigationRoutes.AddEvent.routes),
        NavItem("Add Executive", NavigationRoutes.UpdateExecutive.routes),
        NavItem("Update Community", NavigationRoutes.AdminCommunityList.routes),
        NavItem("Club Update", NavigationRoutes.ClubUpdate.routes)
    )

data class NavItem(
    val name: String,
    val route: String
)

data class NotificationItem(
    val name: String,
    val route: String
)

val notificationList: List<NotificationItem> =
    listOf(
        NotificationItem("Send Newsletter", NavigationRoutes.SendNewsLetter.routes)
    )

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionsScreen(navController: NavController) {
    DefaultScaffold(
        topBar = {
            TopAppBar(
                title = { Text("Actions Center", style = MaterialTheme.typography.headlineMedium) }
            )
        }
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            item {
                SectionTitle(title = "Community management")
            }
            items(actionList) { action ->
                ActionItem(
                    title = action.name,
                    onClick = {
                        navController.navigate(action.route)
                    }
                )
            }
            item {
                SectionTitle(title = "Notifications")
            }
            items(notificationList) { notification ->
                ActionItem(
                    title = notification.name,
                    onClick = { navController.navigate(notification.route) }
                )
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        color = Color.White,
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(vertical = 16.dp)
    )
}

@Composable
fun ActionItem(
    title: String,
    onClick: () -> Unit
) {
    CustomCard(
        modifier =
        Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(vertical = 4.dp)
    ) {
        Column {
            Row(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Navigate",
                    tint = Color.Gray
                )
            }
        }
    }
}
