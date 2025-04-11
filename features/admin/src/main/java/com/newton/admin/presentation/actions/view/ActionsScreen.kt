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
package com.newton.admin.presentation.actions.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.navigation.*
import com.newton.admin.presentation.actions.view.composables.*
import com.newton.commonUi.ui.*
import com.newton.navigation.*
import com.newton.network.domain.models.adminModels.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionsScreen(
    navController: NavController
) {
    val actionList: List<NavItem> = listOf(
        NavItem("Add Partners", NavigationRoutes.AddPartners.routes),
        NavItem("Add Community", NavigationRoutes.AddCommunity.routes),
        NavItem("Add Event", NavigationRoutes.AddEvent.routes),
        NavItem("Add Executive", NavigationRoutes.UpdateExecutive.routes),
        NavItem("Update Community", NavigationRoutes.AdminCommunityList.routes),
        NavItem("Club Update", NavigationRoutes.ClubUpdate.routes)
    )

    val notificationList: List<NotificationItem> = listOf(
        NotificationItem("Send Newsletter", NavigationRoutes.SendNewsLetter.routes)
    )
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
