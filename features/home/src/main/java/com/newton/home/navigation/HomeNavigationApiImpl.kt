package com.newton.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.newton.core.navigation.NavigationRoutes
import com.newton.core.navigation.NavigationSubGraphRoutes
import com.newton.home.presentation.view.HomeScreen

class HomeNavigationApiImpl: HomeNavigationApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController,
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubGraphRoutes.Home.route,
            startDestination = NavigationRoutes.HomeRoute.routes
        ){
            composable(route = NavigationRoutes.HomeRoute.routes) {
                HomeScreen(
                    onNavigateToAdmin = {
                        navHostController.navigate(NavigationRoutes.AdminDashboard.routes)
                    },
                    onNavigateToEvents = {
                        navHostController.navigate(NavigationRoutes.EventsRoute.routes)
                    },
                    onNavigateToAboutUs = {
                        navHostController.navigate(NavigationRoutes.AboutUsRoute.routes)
                    }
                )
            }
        }
    }
}