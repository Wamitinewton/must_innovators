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
import androidx.hilt.navigation.compose.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.newton.communities.presentation.viewModel.*
import com.newton.home.presentation.view.*
import com.newton.navigation.*
import com.newton.partners.presentation.viewModel.*
import com.newton.testimonials.presentation.viewModel.*

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
                val getTestimonialsViewModel = hiltViewModel<GetTestimonialsViewModel>()
                val partnersSharedViewModel = hiltViewModel<PartnersSharedViewModel>(parentEntry)
                val communitiesViewModel = hiltViewModel<CommunitiesViewModel>()
                val communitySharedViewModel = hiltViewModel<CommunitySharedViewModel>()
                HomeScreen(
                    partnersViewModel = partnersViewModel,
                    getTestimonialsViewModel = getTestimonialsViewModel,
                    onNavigateToAdmin = {
                        navHostController.navigate(NavigationRoutes.AdminDashboard.routes)
                    },
                    onNavigateToCommunityDetails = { community ->
                        communitySharedViewModel.selectCommunity(community)
                        navHostController.navigate(NavigationRoutes.CommunitiesDetailsRoute.routes)
                    },
                    onPartnerClick = { partner ->
                        partnersSharedViewModel.updateSelectedPartner(partner)
                        navHostController.navigate(NavigationRoutes.PartnersDetails.routes)
                    },
                    communitiesViewModel = communitiesViewModel,
                    onSeeAllTestimonials = {
                        navHostController.navigate(NavigationRoutes.AllTestimonialsRoute.routes)
                    },
                    onSeeAllPartners = {
                        navHostController.navigate(NavigationRoutes.AllPartnersRoute.routes)
                    }
                )
            }
        }
    }
}
