package com.newton.admin.presentation.actions.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.newton.common_ui.composables.DefaultScaffold
import com.newton.common_ui.ui.CustomCard
import com.newton.core.navigation.NavigationRoutes


val actionList: List<NavItem> = listOf(
    NavItem("Add Partners", NavigationRoutes.AddPartners.routes),
    NavItem("Add Community", NavigationRoutes.AddCommunity.routes),
    NavItem("Add Event", NavigationRoutes.AddEvent.routes),
    NavItem("Add Executive", NavigationRoutes.UpdateExecutive.routes),
    NavItem("Update Community", NavigationRoutes.AdminCommunityList.routes),
    NavItem("Club Update", NavigationRoutes.ClubUpdate.routes),
)

data class NavItem(
    val name: String,
    val route: String
)

data class NotificationItem(
    val name: String,
    val route: String
)

val notificationList: List<NotificationItem> = listOf(
    NotificationItem("Send Newsletter", NavigationRoutes.SendNewsLetter.routes)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionsScreen(
    navController: NavController
) {
    DefaultScaffold(
        topBar = {
            TopAppBar(
                title = { Text("Actions Center", style = MaterialTheme.typography.headlineMedium) }
            )
        },
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
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(vertical = 4.dp),
    ) {
        Column {
            Row(
                modifier = Modifier
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
