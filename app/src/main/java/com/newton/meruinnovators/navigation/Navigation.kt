package com.newton.meruinnovators.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.newton.core.navigation.NavigationSubGraphRoutes

@Composable
fun MeruInnovatorsNavigation(modifier: Modifier = Modifier, navigationSubGraphs: NavigationSubGraphs) {
    val navHostController = rememberNavController()
    NavHost(
        navController = navHostController,
        startDestination = NavigationSubGraphRoutes.Auth.route
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