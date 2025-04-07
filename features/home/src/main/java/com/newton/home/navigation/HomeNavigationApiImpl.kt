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
package com.newton.home.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.platform.*
import androidx.hilt.navigation.compose.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.newton.core.utils.*
import com.newton.home.data.*
import com.newton.home.presentation.view.*
import com.newton.home.presentation.viewmodels.*
import com.newton.navigation.*

class HomeNavigationApiImpl : HomeNavigationApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubGraphRoutes.Home.route,
            startDestination = NavigationRoutes.HomeRoute.routes
        ) {
            composable(route = NavigationRoutes.HomeRoute.routes) {
                val parentEntry = remember(it) {
                    navHostController.getBackStackEntry(NavigationSubGraphRoutes.Home.route)
                }
                val partnersViewModel = hiltViewModel<PartnersViewModel>()
                val testimonialsViewModel = hiltViewModel<TestimonialsViewModel>()
                val partnersSharedViewModel = hiltViewModel<PartnersSharedViewModel>(parentEntry)

                HomeScreen(
                    partnersViewModel = partnersViewModel,
                    testimonialsViewModel = testimonialsViewModel,
                    onNavigateToAdmin = {
                        navHostController.navigate(NavigationRoutes.AdminDashboard.routes)
                    },
                    onNavigateToAboutUs = {
                        navHostController.navigate(NavigationRoutes.AboutUsRoute.routes)
                    },
                    onPartnerClick = { partner ->
                        partnersSharedViewModel.updateSelectedPartner(partner)
                        navHostController.navigate(NavigationRoutes.PartnersDetails.routes)
                    }
                )
            }

            composable(route = NavigationRoutes.PartnersDetails.routes) {
                val parentEntry = remember(it) {
                    navHostController.getBackStackEntry(NavigationSubGraphRoutes.Home.route)
                }
                val partnersSharedViewModel = hiltViewModel<PartnersSharedViewModel>(parentEntry)
                val context = LocalContext.current

                PartnerDetailsScreen(
                    partnersSharedViewModel = partnersSharedViewModel,
                    onBackPressed = {
                        navHostController.navigateUp()
                    },
                    onSharePartner = { partner ->
                        PartnerContentUtils.sharePartner(context, partner)
                    },
                    onNavigateToWebsite = { url ->
                        PackageHandlers.navigateToWebsite(context, url)
                    },
                    onNavigateToLinkedIn = { linkedIn ->
                        PackageHandlers.navigateToLinkedIn(context, linkedIn)
                    },
                    onNavigateToTwitter = { twitter ->
                        PackageHandlers.navigateToTwitter(context, twitter)
                    },
                    onContactEmail = { email ->
                        PackageHandlers.contactViaEmail(context, email)
                    }
                )
            }
        }
    }
}
