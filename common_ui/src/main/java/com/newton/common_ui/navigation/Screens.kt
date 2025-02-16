package com.newton.common_ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Collections
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.HomeMax
import androidx.compose.ui.graphics.vector.ImageVector
import com.newton.core.navigation.NavigationSubGraphRoutes

sealed class Screens(var route: String, var icon: ImageVector, var title: String){
    data object Home : Screens(NavigationSubGraphRoutes.Home.route, Icons.Outlined.HomeMax,"Home")
    data object Events : Screens(NavigationSubGraphRoutes.Event.route, Icons.Outlined.Event,"Events")
    data object Blogs : Screens(NavigationSubGraphRoutes.Blogs.route, Icons.Outlined.Collections,"Blogs")
    data object Account : Screens(NavigationSubGraphRoutes.Account.route, Icons.Outlined.AccountCircle,"Account")
}
val bottomNavigationDestinations = listOf(
    Screens.Home,
    Screens.Events,
    Screens.Blogs,
    Screens.Account
)