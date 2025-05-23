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
import com.newton.communities.presentation.view.aboutUs.*
import com.newton.communities.presentation.viewModel.*
import com.newton.navigation.*

class AboutUsNavigationImpl : AboutUsNavigationApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubGraphRoutes.AboutUs.route,
            startDestination = NavigationRoutes.AboutUsRoute.routes
        ) {
            composable(route = NavigationRoutes.AboutUsRoute.routes) {
                remember(it) {
                    navHostController.getBackStackEntry(NavigationSubGraphRoutes.AboutUs.route)
                }
                val clubBioViewModel = hiltViewModel<ClubBioViewModel>()
                AboutUsScreen(
                    clubBioViewModel = clubBioViewModel
                )
            }

            composable(route = NavigationRoutes.ExecutivesScreen.routes) {
                val executiveViewModel = hiltViewModel<ExecutiveViewModel>()
                ExecutivesScreen(
                    viewModel = executiveViewModel,
                    onBackPressed = {
                        navHostController.navigateUp()
                    }
                )
            }
        }
    }
}
