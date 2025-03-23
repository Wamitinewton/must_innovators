package com.newton.admin.navigation

import AddCommunityScreen
import NewsletterAdminScreen
import UpdateCommunityScreen
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.newton.admin.presentation.community.viewmodels.CommunityViewModel
import com.newton.admin.presentation.events.view.AddEvents
import com.newton.admin.presentation.events.view.management.ModifyEvent
import com.newton.admin.presentation.events.view.management.EventManagementScreen
import com.newton.admin.presentation.events.viewmodel.AddEventViewModel
import com.newton.admin.presentation.events.viewmodel.EventsViewModel
import com.newton.admin.presentation.feedbacks.viewmodel.AdminFeedbackViewModel
import com.newton.admin.presentation.feedbacks.view.FeedbackScreen
import com.newton.admin.presentation.home.viewModel.AdminHomeViewModel
import com.newton.admin.presentation.home.view.AdminHome
import com.newton.admin.presentation.notification.viewmodel.NotificationsViewModel
import com.newton.admin.presentation.partners.view.AddPartnerScreen
import com.newton.admin.presentation.partners.viewModel.PartnersViewModel
import com.newton.admin.presentation.role_management.executives.view.UpdateExecutiveScreen
import com.newton.admin.presentation.role_management.executives.viewModel.ExecutiveViewModel
import com.newton.admin.presentation.actions.view.ActionsScreen
import com.newton.admin.presentation.community.view.AdminCommunityList
import com.newton.admin.presentation.community.viewmodels.CommunitySharedViewModel
import com.newton.admin.presentation.community.viewmodels.UpdateCommunityViewModel
import com.newton.admin.presentation.events.viewmodel.AdminEventsSharedViewModel
import com.newton.admin.presentation.events.viewmodel.UpdateEventsViewModel
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
                val parentEntry = remember(it) {
                    navHostController.getBackStackEntry(NavigationSubGraphRoutes.Admin.route)
                }
                val viewModel = hiltViewModel<EventsViewModel>()
                val sharedViewModel = hiltViewModel<AdminEventsSharedViewModel> (parentEntry)
                EventManagementScreen(
                    onEvent = viewModel::handleEvent,
                    viewModel = viewModel,
                    onEventSelected = {event->
                        sharedViewModel.setSelectedEvent(event)
                        navHostController.navigate(NavigationRoutes.ModifyEvent.routes)
                    }
                )
            }
            composable(route = NavigationRoutes.AdminFeedbacks.routes) {
                val viewModel = hiltViewModel<AdminFeedbackViewModel>()
                FeedbackScreen(viewModel, onEvent = viewModel::handleEvent)
            }
            composable(route = NavigationRoutes.AdminActions.routes) {
                ActionsScreen(navHostController)
            }
            composable(route = NavigationRoutes.AddEvent.routes) {
                val viewModel = hiltViewModel<AddEventViewModel>()
                AddEvents(viewModel, viewModel::handleEvent, navHostController)
            }
            composable(route = NavigationRoutes.ModifyEvent.routes) {
                val parentEntry = remember(it) {
                    navHostController.getBackStackEntry(NavigationSubGraphRoutes.Admin.route)
                }
                val viewModel = hiltViewModel<AdminEventsSharedViewModel>(parentEntry)
                val updateViewModel = hiltViewModel<UpdateEventsViewModel>()
                ModifyEvent(
                    viewModel = viewModel,
                    onEvent = updateViewModel::handleEvents,
                    updateViewModel
                )
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
                val viewModel = hiltViewModel<PartnersViewModel>()
                AddPartnerScreen(viewModel, viewModel::handleEvent)
            }
            composable(route = NavigationRoutes.SendNewsLetter.routes) {
                val viewModel = hiltViewModel<NotificationsViewModel>()
                NewsletterAdminScreen(
                    viewModel = viewModel,
                    onEvent = viewModel::handleEvents,
                    navController = navHostController
                )
            }
            composable(route = NavigationRoutes.UpdateCommunity.routes) {
                val parentEntry = remember(it) {
                    navHostController.getBackStackEntry(NavigationRoutes.AdminCommunityList.routes)
                }
                val viewModel = hiltViewModel<UpdateCommunityViewModel>()
                val sharedViewModel = hiltViewModel<CommunitySharedViewModel>(parentEntry)
                UpdateCommunityScreen(
                    sharedViewModel,
                    viewModel = viewModel,
                    onEvent = viewModel::handleEvents
                )
            }
            composable(route = NavigationRoutes.UpdateExecutive.routes) {
                val viewModel = hiltViewModel<ExecutiveViewModel>()
                UpdateExecutiveScreen(
                    navController = navHostController,
                    viewModel = viewModel
                )
            }
            composable(route=NavigationRoutes.AdminCommunityList.routes) {
                val parentEntry = remember(it) {
                    navHostController.getBackStackEntry(NavigationRoutes.AdminActions.routes)
                }
                val viewModel = hiltViewModel<UpdateCommunityViewModel>()
                val sharedViewModel = hiltViewModel<CommunitySharedViewModel>(parentEntry)
                AdminCommunityList(
                    viewModel = viewModel,
                    onCommunitySelected = {community->
                        sharedViewModel.setSelectedCommunity(community)
                        navHostController.navigate(NavigationRoutes.UpdateCommunity.routes)
                    }
                )
            }

        }
    }
}