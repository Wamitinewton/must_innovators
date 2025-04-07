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
package com.newton.meruinnovators.navigation

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.newton.auth.presentation.login.viewModel.*
import com.newton.navigation.*

@Composable
fun MeruInnovatorsNavigation(
    navigationSubGraphs: NavigationSubGraphs,
    loginViewModel: LoginViewModel = viewModel(),
    navHostController: NavHostController
) {
    val isUserLoggedIn by loginViewModel.isUserLoggedIn.collectAsState()

    NavHost(
        navController = navHostController,
//        startDestination = NavigationSubGraphRoutes.Home.route
        startDestination =
        if (isUserLoggedIn) {
            NavigationSubGraphRoutes.Home.route
        } else {
            NavigationSubGraphRoutes.Auth.route
        }
    ) {
        navigationSubGraphs.authNavigationApi.registerGraph(
            navHostController = navHostController,
            navGraphBuilder = this
        )
        navigationSubGraphs.eventNavigationApi.registerGraph(
            navHostController = navHostController,
            navGraphBuilder = this
        )
        navigationSubGraphs.accountNavigationApi.registerGraph(
            navHostController = navHostController,
            navGraphBuilder = this
        )
        navigationSubGraphs.blogsNavigationApi.registerGraph(
            navHostController = navHostController,
            navGraphBuilder = this
        )
        navigationSubGraphs.homeNavigationApi.registerGraph(
            navHostController = navHostController,
            navGraphBuilder = this
        )
        navigationSubGraphs.adminNavigationApi.registerGraph(
            navHostController = navHostController,
            navGraphBuilder = this
        )
        navigationSubGraphs.aboutUsNavigationApi.registerGraph(
            navHostController = navHostController,
            navGraphBuilder = this
        )
        navigationSubGraphs.feedbackNavigationApi.registerGraph(
            navHostController = navHostController,
            navGraphBuilder = this
        )
        navigationSubGraphs.settingNavigationApi.registerGraph(
            navHostController = navHostController,
            navGraphBuilder = this
        )
        navigationSubGraphs.communityNavigationApi.registerGraph(
            navHostController = navHostController,
            navGraphBuilder = this
        )
    }
}
