package com.newton.admin.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.newton.admin.presentation.events.view.ModifyEvent
import com.newton.admin.presentation.events.view.YourEventsScreen
import com.newton.admin.presentation.events.view.AddEvents
import com.newton.admin.presentation.events.viewmodel.AddEventViewModel
import com.newton.admin.presentation.view.feedbacks.FeedbackScreen
import com.newton.admin.presentation.home.views.AdminHome
import com.newton.admin.presentation.view.setings.AdminSettingsScreen
import com.newton.core.navigation.NavigationRoutes
import com.newton.core.navigation.NavigationSubGraphRoutes

class AdminNavigationApiImpl : AdminNavigationApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController,
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubGraphRoutes.Admin.route,
            startDestination = NavigationRoutes.AdminDashboard.routes
        ) {
            composable(route = NavigationRoutes.AdminDashboard.routes) {
                AdminHome()
            }
            composable(route = NavigationRoutes.AdminEvents.routes) {
                YourEventsScreen(navHostController)
            }
            composable(route = NavigationRoutes.AdminFeedbacks.routes) {
                FeedbackScreen()
            }
             composable(route = NavigationRoutes.AdminSettings.routes) {
                AdminSettingsScreen()
            }
            composable(route= NavigationRoutes.AddEvent.routes) {
                val viewModel = hiltViewModel<AddEventViewModel>()
                AddEvents(viewModel,viewModel::handleEvent,navHostController)
            }
            composable(route= NavigationRoutes.ModifyEvent.routes) {
                val viewModel = hiltViewModel<AddEventViewModel>()
                ModifyEvent()
            }
        }
    }
}