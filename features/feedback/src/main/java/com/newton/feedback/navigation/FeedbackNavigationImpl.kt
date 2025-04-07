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
package com.newton.feedback.navigation

import androidx.hilt.navigation.compose.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.newton.feedback.presentation.view.bugReport.*
import com.newton.feedback.presentation.view.generalFeedback.*
import com.newton.feedback.presentation.viewmodel.*
import com.newton.navigation.*

class FeedbackNavigationImpl : FeedbackNavigationApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubGraphRoutes.UserFeedback.route,
            startDestination = NavigationRoutes.GeneralFeedbackRoute.routes
        ) {
            composable(route = NavigationRoutes.BugReportingScreen.routes) {
                val userFeedbackViewModel = hiltViewModel<UserFeedbackViewModel>()
                BugReportsScreen(
                    viewModel = userFeedbackViewModel,
                    onBackPress = {}
                )
            }

            composable(route = NavigationRoutes.GeneralFeedbackRoute.routes) {
                val userFeedbackViewModel = hiltViewModel<UserFeedbackViewModel>()
                GeneralFeedbackScreen(
                    viewModel = userFeedbackViewModel,
                    onBackPress = {}
                )
            }
        }
    }
}
