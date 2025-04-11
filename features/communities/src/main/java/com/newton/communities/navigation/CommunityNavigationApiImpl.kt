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
package com.newton.communities.navigation

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.newton.communities.presentation.view.*
import com.newton.communities.presentation.viewModel.*
import com.newton.navigation.*

class CommunityNavigationApiImpl : CommunityNavigationApi {
    override fun registerGraph(navGraphBuilder: NavGraphBuilder, navHostController: NavHostController) {
        navGraphBuilder.navigation(
            route = NavigationSubGraphRoutes.Communities.route,
            startDestination = NavigationRoutes.CommunitiesDetailsRoute.routes
        ) {
            composable(
                route = NavigationRoutes.CommunitiesDetailsRoute.routes
            ) {
                val parentEntry =
                    remember(it) {
                        navHostController.getBackStackEntry(NavigationSubGraphRoutes.Home.route)
                    }
                val communitySharedViewModel = hiltViewModel<CommunitySharedViewModel>(parentEntry)
                CommunityDetailsScreen(
                    onBackPressed = {
                        navHostController.navigateUp()
                    },
                    onShareClick = {},
                    onJoinCommunity = {},
                    communitySharedViewModel = communitySharedViewModel
                )
            }
        }
    }
}
