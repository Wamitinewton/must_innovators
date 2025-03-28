package com.newton.feedback.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.newton.navigation.NavigationRoutes
import com.newton.navigation.NavigationSubGraphRoutes
import com.newton.feedback.presentation.view.bug_report.BugReportsScreen
import com.newton.feedback.presentation.view.general_feedback.GeneralFeedbackScreen
import com.newton.feedback.presentation.viewmodel.UserFeedbackViewModel

class FeedbackNavigationImpl: FeedbackNavigationApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubGraphRoutes.UserFeedback.route,
            startDestination = NavigationRoutes.GeneralFeedbackRoute.routes
        ){
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