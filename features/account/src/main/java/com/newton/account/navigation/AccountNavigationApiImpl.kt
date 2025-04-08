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
package com.newton.account.navigation

import androidx.hilt.navigation.compose.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.newton.account.presentation.view.*
import com.newton.account.presentation.viewmodel.*
import com.newton.core.utils.*
import com.newton.navigation.*

class AccountNavigationApiImpl : AccountNavigationApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubGraphRoutes.Account.route,
            startDestination = NavigationRoutes.AccountRoute.routes
        ) {
            composable(route = NavigationRoutes.AccountRoute.routes) {
                val accountViewModel = hiltViewModel<UpdateAccountViewModel>()
                val accountManagementViewModel = hiltViewModel<AccountManagementViewModel>()
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
                        navHostController.navigate(NavigationRoutes.DeleteAccountRoute.routes)
                    },
                    onLogoutClicked = {
                        navHostController.navigate(NavigationRoutes.LoginRoute.routes) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    accountManagementViewModel = accountManagementViewModel,
                    onCreateTestimonial = {
                        navHostController.navigate(NavigationRoutes.CreateTestimonialsRoute.routes)
                    },
                    onSettingsClicked = {
                        navHostController.navigate(NavigationRoutes.SettingsRoute.routes)
                    }
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

            composable(route = NavigationRoutes.DeleteAccountRoute.routes) {
                val accountManagementViewModel = hiltViewModel<AccountManagementViewModel>()

                DeleteAccountScreen(
                    onNavigateBack = {
                        navHostController.navigateUp()
                    },
                    onNavigateToAccountDeleted = {
                        navHostController.navigate(NavigationRoutes.DeleteAccountSuccessRoute.routes) {
                            popUpTo(NavigationRoutes.DeleteAccountRoute.routes) { inclusive = true }
                        }
                    },
                    viewModel = accountManagementViewModel
                )
            }

            composable(route = NavigationRoutes.DeleteAccountSuccessRoute.routes) {
                AccountDeletedScreen(
                    onCreateNewAccount = {
                        navHostController.navigate(NavigationRoutes.OnboardingRoute.routes) {
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
