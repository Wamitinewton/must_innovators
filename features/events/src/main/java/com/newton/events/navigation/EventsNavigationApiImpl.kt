package com.newton.events.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.newton.core.navigation.NavigationRoutes
import com.newton.core.navigation.NavigationSubGraphRoutes
import com.newton.events.presentation.view.EventsScreen
import com.newton.events.presentation.viewmodel.EventViewModel

class EventsNavigationApiImpl: EventsNavigationApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController,
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubGraphRoutes.Event.route,
            startDestination = NavigationRoutes.EventsRoute.routes
        ){
            composable(route = NavigationRoutes.EventsRoute.routes) {
                val eventViewModel = hiltViewModel<EventViewModel>()
                EventsScreen(eventViewModel = eventViewModel)
            }
        }
    }
}