/**
 * Copyright (c) 2025 Meru Science Innovators Club
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Meru Science Innovators Club.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Meru Science Innovators Club.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 *
 * NO WARRANTY: This software is provided "as is" without warranty of any kind,
 * either express or implied, including but not limited to the implied warranties
 * of merchantability and fitness for a particular purpose.
 */
package com.newton.admin.navigation

import NewsletterAdminScreen
import UpdateCommunityScreen
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.newton.admin.presentation.actions.view.*
import com.newton.admin.presentation.club.view.*
import com.newton.admin.presentation.club.viewmodel.*
import com.newton.admin.presentation.community.view.*
import com.newton.admin.presentation.community.viewmodels.*
import com.newton.admin.presentation.events.view.*
import com.newton.admin.presentation.events.view.management.*
import com.newton.admin.presentation.events.viewmodel.*
import com.newton.admin.presentation.feedbacks.view.*
import com.newton.admin.presentation.feedbacks.viewmodel.*
import com.newton.admin.presentation.home.view.*
import com.newton.admin.presentation.home.viewModel.*
import com.newton.admin.presentation.notification.viewmodel.*
import com.newton.admin.presentation.partners.view.*
import com.newton.admin.presentation.partners.viewModel.*
import com.newton.admin.presentation.roleManagement.executives.view.*
import com.newton.admin.presentation.roleManagement.executives.viewModel.*
import com.newton.navigation.*

class AdminNavigationApiImpl : AdminNavigationApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
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
                val sharedViewModel = hiltViewModel<AdminEventsSharedViewModel>(parentEntry)
                EventManagementScreen(
                    onEvent = viewModel::handleEvent,
                    viewModel = viewModel,
                    onEventSelected = { event ->
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
                AddEvents(viewModel, viewModel::handleEvent)
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
                    onEvent = viewModel::handleEvent
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
                    onEvent = viewModel::handleEvents
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
                    viewModel = viewModel,
                    onEvent = viewModel::handleEvents
                )
            }
            composable(route = NavigationRoutes.AdminCommunityList.routes) {
                val parentEntry = remember(it) {
                    navHostController.getBackStackEntry(NavigationRoutes.AdminCommunityList.routes)
                }
                val viewModel = hiltViewModel<UpdateCommunityViewModel>()
                val sharedViewModel = hiltViewModel<CommunitySharedViewModel>(parentEntry)
                AdminCommunityList(
                    viewModel = viewModel,
                    onCommunitySelected = { community ->
                        sharedViewModel.setSelectedCommunity(community)
                        navHostController.navigate(NavigationRoutes.UpdateCommunity.routes)
                    }
                )
            }
            composable(route = NavigationRoutes.ClubUpdate.routes) {
                val viewModel = hiltViewModel<ClubViewModel>()
                AddClubScreen(
                    viewmodel = viewModel,
                    onEvent = viewModel::handleEvent
                )
            }
        }
    }
}
