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
package com.newton.meruinnovators.navigation

import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.*
import com.newton.navigation.*

sealed class Screens(
    var route: String,
    var selectedIcon: ImageVector,
    var unSelectedIcon: ImageVector,
    var hasNews: Boolean,
    var badgeCount: Int? = null,
    var title: String
) {
    data object Home : Screens(
        NavigationSubGraphRoutes.Home.route,
        Icons.Filled.Home,
        Icons.Outlined.Home,
        false,
        null,
        "Home"
    )

    data object Events :
        Screens(
            NavigationSubGraphRoutes.Event.route,
            Icons.Filled.Event,
            Icons.Outlined.Event,
            false,
            null,
            "Events"
        )

    data object Blogs :
        Screens(
            NavigationSubGraphRoutes.Blogs.route,
            Icons.Filled.Collections,
            Icons.Outlined.Collections,
            true,
            12,
            "Blogs"
        )

    data object Account :
        Screens(
            NavigationSubGraphRoutes.Account.route,
            Icons.Filled.AccountCircle,
            Icons.Outlined.AccountCircle,
            false,
            null,
            "Account"
        )

    data object Dashboard : Screens(
        NavigationRoutes.AdminDashboard.routes,
        Icons.Filled.DashboardCustomize,
        Icons.Outlined.DashboardCustomize,
        false,
        null,
        "Dashboard"
    )

    data object AdminEvents : Screens(
        NavigationRoutes.AdminEvents.routes,
        Icons.Filled.EventSeat,
        Icons.Outlined.EventSeat,
        false,
        null,
        "Events"
    )

    data object Feedback : Screens(
        NavigationRoutes.AdminFeedbacks.routes,
        Icons.Filled.Feedback,
        Icons.Outlined.Feedback,
        true,
        24,
        "Feedback"
    )

    data object AdminSettings : Screens(
        NavigationRoutes.AdminActions.routes,
        Icons.Filled.LocalActivity,
        Icons.Outlined.LocalActivity,
        false,
        null,
        "Actions"
    )
}

var bottomNavigationDestinations =
    listOf(
        Screens.Home,
        Screens.Events,
//    Screens.Blogs,
        Screens.Account
    )

var adminNavDestinations =
    listOf(
        Screens.Dashboard,
        Screens.AdminEvents,
        Screens.Feedback,
        Screens.AdminSettings
    )
