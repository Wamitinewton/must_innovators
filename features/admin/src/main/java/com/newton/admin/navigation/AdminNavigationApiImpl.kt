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
import com.newton.admin.presentation.feedbacks.views.FeedbackScreen
import com.newton.admin.presentation.home.viewModel.AdminHomeViewModel
import com.newton.admin.presentation.home.views.AdminHome
import com.newton.admin.presentation.partners.view.AddPartnerScreen
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
                val viewModel = hiltViewModel<AdminHomeViewModel>()
                AdminHome(viewModel, viewModel::handleEvents, navHostController)
            }
            composable(route = NavigationRoutes.AdminEvents.routes) {
                EventManagementScreen(navHostController)
            }
            composable(route = NavigationRoutes.AdminFeedbacks.routes) {
                val viewModel = hiltViewModel<AdminFeedbackViewModel>()
                FeedbackScreen(viewModel)
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
                AddCommunityScreen(viewModel=viewModel, onEvent = viewModel::handleEvent)
            }
            composable(route = NavigationRoutes.AddPartners.routes) {
                AddPartnerScreen()
            }
            composable(route = NavigationRoutes.NewsLetterScreen.routes) {
                NewsletterAdminScreen()
            }
            composable(route = NavigationRoutes.UpdateCommunityRoute.routes) {
                UpdateCommunityScreen(
                    onBackPressed = { },
                    onSavePressed ={ }
                )
            }

        }
    }
}