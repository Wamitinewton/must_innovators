package com.newton.admin.navigation

import AddCommunityScreen
import NewsletterAdminScreen
import UpdateCommunityScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.newton.admin.presentation.community.viewmodels.CommunityViewModel
import com.newton.admin.presentation.events.view.AddEvents
import com.newton.admin.presentation.events.view.ModifyEvent
import com.newton.admin.presentation.events.view.management.EventManagementScreen
import com.newton.admin.presentation.events.viewmodel.AddEventViewModel
import com.newton.admin.presentation.feedbacks.viewmodel.AdminFeedbackViewModel
import com.newton.admin.presentation.feedbacks.view.FeedbackScreen
import com.newton.admin.presentation.home.viewModel.AdminHomeViewModel
import com.newton.admin.presentation.home.views.AdminHome
import com.newton.admin.presentation.notification.viewmodel.NotificationsViewModel
import com.newton.admin.presentation.partners.view.AddPartnerScreen
import com.newton.admin.presentation.role_management.executives.view.UpdateExecutiveScreen
import com.newton.admin.presentation.role_management.executives.viewModel.ExecutiveViewModel
import com.newton.admin.presentation.settings.view.AdminSettingsScreen
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
                val viewModel = hiltViewModel<AdminHomeViewModel>()
                AdminHome(viewModel, viewModel::handleEvents, navHostController)
            }
            composable(route = NavigationRoutes.AdminEvents.routes) {
                EventManagementScreen(navHostController)
            }
            composable(route = NavigationRoutes.AdminFeedbacks.routes) {
                val viewModel = hiltViewModel<AdminFeedbackViewModel>()
                FeedbackScreen(viewModel, onEvent = viewModel::handleEvent)
            }
            composable(route = NavigationRoutes.AdminSettings.routes) {
                AdminSettingsScreen()
            }
            composable(route = NavigationRoutes.AddEvent.routes) {
                val viewModel = hiltViewModel<AddEventViewModel>()
                AddEvents(viewModel, viewModel::handleEvent, navHostController)
            }
            composable(route = NavigationRoutes.ModifyEvent.routes) {
                val viewModel = hiltViewModel<AddEventViewModel>()
                ModifyEvent()
            }
            composable(route = NavigationRoutes.AddCommunity.routes) {
                val viewModel = hiltViewModel<CommunityViewModel>()
                AddCommunityScreen(
                    viewModel = viewModel,
                    onEvent = viewModel::handleEvent,
                    navController = navHostController,
                )
            }
            composable(route = NavigationRoutes.AddPartners.routes) {
                AddPartnerScreen()
            }
            composable(route = NavigationRoutes.NewsLetterScreen.routes) {
                val viewModel = hiltViewModel<NotificationsViewModel>()
                NewsletterAdminScreen(
                    viewModel = viewModel,
                    onEvent = viewModel::handleEvents,
                    navController = navHostController
                )
            }
            composable(route = NavigationRoutes.UpdateCommunityRoute.routes) {
                val viewModel = hiltViewModel<CommunityViewModel>()
                UpdateCommunityScreen(
                    onBackPressed = { },
                    onSavePressed = { },
                    viewModel
                )
            }
            composable(route = NavigationRoutes.UpdateExecutive.routes) {
                val viewModel = hiltViewModel<ExecutiveViewModel>()
                UpdateExecutiveScreen(
                    navController = navHostController,
                    viewModel=viewModel
                )
            }

        }
    }
}