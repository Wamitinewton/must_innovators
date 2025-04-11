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
package com.newton.events.navigation

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.newton.auth.presentation.login.viewModel.*
import com.newton.events.presentation.view.eventDetails.*
import com.newton.events.presentation.view.eventList.*
import com.newton.events.presentation.view.eventRegistration.*
import com.newton.events.presentation.view.userTickets.*
import com.newton.events.presentation.viewmodel.*
import com.newton.navigation.*

class EventsNavigationApiImpl : EventsNavigationApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubGraphRoutes.Event.route,
            startDestination = NavigationRoutes.EventsRoute.routes
        ) {
            /**
             * In this case, we are using parent entry for the Shared viewmodel
             * To ensure that we do not create multiple view model instances
             * Which might cause stagnated loading state when fetching data
             */

            composable(route = NavigationRoutes.EventsRoute.routes) {
                val parentEntry =
                    remember(it) {
                        navHostController.getBackStackEntry(NavigationSubGraphRoutes.Event.route)
                    }
                val eventViewModel = hiltViewModel<EventViewModel>()
                val sharedViewModel = hiltViewModel<EventsSharedViewModel>(parentEntry)
                EventsScreen(
                    eventViewModel = eventViewModel,
                    onEventClick = { eventsData ->
                        sharedViewModel.setSelectedEvent(eventsData)
                        navHostController.navigate(NavigationRoutes.EventDetailsRoute.routes)
                    },
                    onRsvpClick = { eventsData ->
                        sharedViewModel.setSelectedEvent(eventsData)
                        navHostController.navigate(NavigationRoutes.EventRegistrationScreen.routes)
                    }
                )
            }

            composable(route = NavigationRoutes.EventDetailsRoute.routes) {
                val parentEntry =
                    remember(it) {
                        navHostController.getBackStackEntry(NavigationSubGraphRoutes.Event.route)
                    }
                val sharedViewModel = hiltViewModel<EventsSharedViewModel>(parentEntry)
                EventDetailsScreen(
                    viewModel = sharedViewModel,
                    onBackPressed = { navHostController.navigateUp() }
                )
            }

            composable(route = NavigationRoutes.EventRegistrationScreen.routes) {
                val parentEntry =
                    remember(it) {
                        navHostController.getBackStackEntry(NavigationSubGraphRoutes.Event.route)
                    }
                val sharedViewModel = hiltViewModel<EventsSharedViewModel>(parentEntry)
                val getUserDataViewModel = hiltViewModel<GetUserDataViewModel>()
                val eventRsvpViewmodel = hiltViewModel<EventRsvpViewmodel>()
                EventRegistrationScreen(
                    onClose = {
                        navHostController.navigate(NavigationRoutes.EventsRoute.routes)
                    },
                    eventsSharedViewModel = sharedViewModel,
                    userDataViewModel = getUserDataViewModel,
                    eventRsvpViewmodel = eventRsvpViewmodel,
                    onNavigateToSuccess = {
                        navHostController.navigate(NavigationRoutes.EventRegistrationSuccessScreen.routes)
                    }
                )
            }

            composable(route = NavigationRoutes.EventTicketsRoute.routes) {
                val userTicketsViewModel = hiltViewModel<UserTicketsViewModel>()
                RegisteredEventsScreen(
                    onBackPressed = {
                        navHostController.navigateUp()
                    },
                    onTicketSelected = {},
                    userTicketsViewModel = userTicketsViewModel
                )
            }

            composable(route = NavigationRoutes.EventRegistrationSuccessScreen.routes) {
                val parentEntry = remember(it) {
                    navHostController.getBackStackEntry(NavigationSubGraphRoutes.Event.route)
                }
                val eventRegistrationSharedViewModel = hiltViewModel<RsvpSharedViewModel>(parentEntry)
                EventRegistrationSuccessScreen(
                    onNavigateToHome = {},
                    onViewMyTickets = {},
                    sharedViewModel = eventRegistrationSharedViewModel
                )
            }
        }
    }
}
