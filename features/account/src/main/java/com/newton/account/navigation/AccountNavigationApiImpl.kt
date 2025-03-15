package com.newton.account.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.newton.account.presentation.view.AccountScreen
import com.newton.account.presentation.view.ProfileUpdateScreen
import com.newton.account.presentation.viewmodel.AccountViewModel
import com.newton.core.navigation.NavigationRoutes
import com.newton.core.navigation.NavigationSubGraphRoutes

class AccountNavigationApiImpl: AccountNavigationApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController,
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubGraphRoutes.Account.route,
            startDestination = NavigationRoutes.AccountRoute.routes
        ){
            composable(route = NavigationRoutes.AccountRoute.routes) {
                val accountViewModel = hiltViewModel<AccountViewModel>()
                AccountScreen(
                    onMyEventsClick = {
                        navHostController.navigate(NavigationRoutes.EventTicketsRoute.routes)
                    },
                    onBugReportClick = {
                        navHostController.navigate(NavigationRoutes.BugReportingScreen.routes)
                    },
                    onGeneralFeedbackClick = {
                        navHostController.navigate(NavigationRoutes.GeneralFeedbackRoute.routes)
                    },
                    accountViewModel = accountViewModel,
                    onUpdateProfile = {
                        navHostController.navigate(NavigationRoutes.ProfileUpdateScreen.routes)
                    },
                )
            }
            composable(route = NavigationRoutes.ProfileUpdateScreen.routes) {
                val accountViewModel = hiltViewModel<AccountViewModel>()
                ProfileUpdateScreen(
                    viewModel = accountViewModel,
                    onNavigateBack = {
                        navHostController.navigateUp()
                    }
                )
            }
        }
    }
}