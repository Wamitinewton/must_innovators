package com.newton.events.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.newton.auth.presentation.login.view_model.GetUserDataViewModel
import com.newton.core.navigation.NavigationRoutes
import com.newton.core.navigation.NavigationSubGraphRoutes
import com.newton.events.presentation.view.event_details.EventDetailsScreen
import com.newton.events.presentation.view.event_list.EventsScreen
import com.newton.events.presentation.view.event_registration.EventRegistrationScreen
import com.newton.events.presentation.view.search_events.EventSearchScreen
import com.newton.events.presentation.view.ticket_screen.RegisteredEventsScreen
import com.newton.events.presentation.viewmodel.EventRsvpViewmodel
import com.newton.events.presentation.viewmodel.EventViewModel
import com.newton.events.presentation.viewmodel.EventsSharedViewModel

class EventsNavigationApiImpl: EventsNavigationApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController,
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubGraphRoutes.Event.route,
            startDestination = NavigationRoutes.EventsRoute.routes
        ){
            /**
             * In this case, we are using parent entry for the Shared viewmodel
             * To ensure that we do not create multiple view model instances
             * Which might cause stagnated loading state when fetching data
             */

            composable(route = NavigationRoutes.EventsRoute.routes) {
                val parentEntry = remember(it) {
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
                    onSearchClick = {
                        navHostController.navigate(NavigationRoutes.EventSearchScreen.routes)
                    },
                    onRsvpClick = {
                        navHostController.navigate(NavigationRoutes.EventRegistrationScreen.routes)
                    }
                )
            }

            composable(route = NavigationRoutes.EventDetailsRoute.routes) {
                val parentEntry = remember(it) {
                    navHostController.getBackStackEntry(NavigationSubGraphRoutes.Event.route)
                }
                val sharedViewModel = hiltViewModel<EventsSharedViewModel>(parentEntry)
                EventDetailsScreen(
                    viewModel = sharedViewModel,
                    onBackPressed = { navHostController.navigateUp() }
                )
            }

            composable(route = NavigationRoutes.EventSearchScreen.routes) {
                val parentEntry = remember(it) {
                    navHostController.getBackStackEntry(NavigationSubGraphRoutes.Event.route)
                }
                val sharedViewModel = hiltViewModel<EventsSharedViewModel>(parentEntry)
                val eventViewModel = hiltViewModel<EventViewModel>()
                EventSearchScreen(
                    viewModel = eventViewModel,
                    onBackPress = { navHostController.navigateUp() },
                    onEventClick = { eventsData ->
                        sharedViewModel.setSelectedEvent(eventsData)
                        navHostController.navigate(NavigationRoutes.EventDetailsRoute.routes)
                    }
                )
            }

            composable(route = NavigationRoutes.EventRegistrationScreen.routes) {
                val parentEntry = remember(it) {
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
                    onRegisterSuccess = {
                        navHostController.navigate(NavigationRoutes.EventTicketsRoute.routes)
                    }
                )
            }

            composable(route = NavigationRoutes.EventTicketsRoute.routes) {
                RegisteredEventsScreen(
                    onBackPressed = {
                        navHostController.navigateUp()
                    },
                    onTicketSelected = {}
                )
            }
        }
    }
}