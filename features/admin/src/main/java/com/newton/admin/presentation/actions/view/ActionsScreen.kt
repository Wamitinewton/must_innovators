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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.newton.admin.presentation.actions.view.composables.ActionItem
import com.newton.admin.presentation.actions.view.composables.SectionTitle
import com.newton.commonUi.composables.DefaultScaffold
import com.newton.core.domain.models.admin.NavItem
import com.newton.core.domain.models.admin.NotificationItem
import com.newton.navigation.NavigationRoutes

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
        NavItem("Club Update", NavigationRoutes.ClubUpdate.routes),
    )

    val notificationList: List<NotificationItem> = listOf(
        NotificationItem("Send Newsletter", NavigationRoutes.SendNewsLetter.routes)
    )
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