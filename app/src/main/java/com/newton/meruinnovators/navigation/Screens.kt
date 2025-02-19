package com.newton.meruinnovators.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.DashboardCustomize
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.EventSeat
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.SettingsSuggest
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Collections
import androidx.compose.material.icons.outlined.DashboardCustomize
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.EventSeat
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.SettingsSuggest
import androidx.compose.ui.graphics.vector.ImageVector
import com.newton.core.navigation.NavigationRoutes
import com.newton.core.navigation.NavigationSubGraphRoutes

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
            true,
            5,
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
            true,
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
        NavigationRoutes.AdminSettings.routes,
        Icons.Filled.SettingsSuggest,
        Icons.Outlined.SettingsSuggest,
        false,
        null,
        "Settings"
    )



}

var bottomNavigationDestinations = listOf(
    Screens.Home,
    Screens.Events,
    Screens.Blogs,
    Screens.Account
)

var adminNavDestinations = listOf(
    Screens.Dashboard,
    Screens.AdminEvents,
    Screens.Feedback,
    Screens.AdminSettings
)
