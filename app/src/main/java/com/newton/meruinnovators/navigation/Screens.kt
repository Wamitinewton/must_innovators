package com.newton.meruinnovators.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Collections
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
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
}

var bottomNavigationDestinations = listOf(
    Screens.Home,
    Screens.Events,
    Screens.Blogs,
    Screens.Account
)
