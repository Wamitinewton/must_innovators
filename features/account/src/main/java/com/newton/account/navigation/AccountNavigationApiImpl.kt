package com.newton.account.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.newton.account.presentation.view.AccountDeletedScreen
import com.newton.account.presentation.view.AccountScreen
import com.newton.account.presentation.view.DeleteAccountScreen
import com.newton.account.presentation.view.ProfileUpdateScreen
import com.newton.account.presentation.viewmodel.AccountManagementViewModel
import com.newton.account.presentation.viewmodel.UpdateAccountViewModel
import com.newton.core.navigation.NavigationRoutes
import com.newton.core.navigation.NavigationSubGraphRoutes
import com.newton.core.utils.ActivityHandler

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
                val accountViewModel = hiltViewModel<UpdateAccountViewModel>()
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
                    onDeleteAccount = {
                        navHostController.navigate(NavigationRoutes.DeleteAccountSuccessRoute.routes)
                    },
                )
            }
            composable(route = NavigationRoutes.ProfileUpdateScreen.routes) {
                val accountViewModel = hiltViewModel<UpdateAccountViewModel>()
                ProfileUpdateScreen(
                    viewModel = accountViewModel,
                    onNavigateBack = {
                        navHostController.navigateUp()
                    }
                )
            }

            composable(route = NavigationRoutes.DeleteAccountRoute.routes){
                val accountManagementViewModel = hiltViewModel<AccountManagementViewModel>()

                DeleteAccountScreen(
                    onNavigateBack = {
                        navHostController.navigateUp()
                    },
                    onNavigateToAccountDeleted = {
                        navHostController.navigate(NavigationRoutes.DeleteAccountSuccessRoute.routes) {
                            popUpTo(NavigationRoutes.DeleteAccountRoute.routes){ inclusive = true }
                        }
                    },
                    viewModel = accountManagementViewModel
                )
            }
            composable(route = NavigationRoutes.DeleteAccountSuccessRoute.routes){
                AccountDeletedScreen(
                    onCreateNewAccount = {
                        navHostController.navigate(NavigationRoutes.OnboardingRoute.routes){
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    onExitApp = { activity ->
                        ActivityHandler.exitApp(activity)
                    }
                )
            }
        }
    }
}