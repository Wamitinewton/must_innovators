package com.newton.meruinnovators.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.newton.auth.presentation.login.view_model.LoginViewModel
import com.newton.core.navigation.NavigationSubGraphRoutes

@Composable
fun MeruInnovatorsNavigation(
    modifier: Modifier = Modifier,
    navigationSubGraphs: NavigationSubGraphs,
    loginViewModel: LoginViewModel = viewModel()
) {
    val navHostController = rememberNavController()
    val isUserLoggedIn by loginViewModel.isUserLoggedIn.collectAsState()
    NavHost(
        navController = navHostController,
        startDestination = if (isUserLoggedIn) {
            NavigationSubGraphRoutes.Event.route
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
    }

}