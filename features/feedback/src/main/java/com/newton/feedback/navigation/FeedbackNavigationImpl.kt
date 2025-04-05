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
