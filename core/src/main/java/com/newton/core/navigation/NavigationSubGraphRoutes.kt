package com.newton.core.navigation

sealed class NavigationSubGraphRoutes(val route: String) {
    data object Auth: NavigationSubGraphRoutes(route = "/auth_")
    data object Home: NavigationSubGraphRoutes(route = "/home_")
    data object Event: NavigationSubGraphRoutes(route = "/event_")
    data object Blogs: NavigationSubGraphRoutes(route = "/blogs_")
    data object Account: NavigationSubGraphRoutes(route = "/account_")
}
