package com.newton.core.navigation

sealed class NavigationSubGraphRoutes(val route: String) {
    data object Auth: NavigationSubGraphRoutes(route = "/auth_")
    data object Event: NavigationSubGraphRoutes(route = "/event_")
}