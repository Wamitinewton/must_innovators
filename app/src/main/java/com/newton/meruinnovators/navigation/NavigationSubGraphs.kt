package com.newton.meruinnovators.navigation

import com.newton.auth.navigation.AuthNavigationApi
import com.newton.events.navigation.EventsNavigationApi

data class NavigationSubGraphs(
    val authNavigationApi: AuthNavigationApi,
    val eventNavigationApi: EventsNavigationApi
)
