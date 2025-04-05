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
        navigationSubGraphs.communityNavigationApi.registerGraph(
            navHostController = navHostController,
            navGraphBuilder = this
        )
        navigationSubGraphs.feedbackNavigationApi.registerGraph(
            navHostController = navHostController,
            navGraphBuilder = this
        )
    }
}
