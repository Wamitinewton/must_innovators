package com.newton.home.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.newton.core.navigation.NavigationRoutes
import com.newton.core.navigation.NavigationSubGraphRoutes
import com.newton.home.presentation.view.HomeScreen
import com.newton.home.presentation.viewmodels.PartnersViewModel
import com.newton.home.presentation.viewmodels.TestimonialsViewModel

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
                val partnersViewModel = hiltViewModel<PartnersViewModel>()
                val testimonialsViewModel = hiltViewModel<TestimonialsViewModel>()
                HomeScreen(
                    partnersViewModel = partnersViewModel,
                    testimonialsViewModel = testimonialsViewModel,
                    onNavigateToAdmin = {
                        navHostController.navigate(NavigationRoutes.AdminDashboard.routes)
                    },
                    onNavigateToAboutUs = {
                        navHostController.navigate(NavigationRoutes.AboutUsRoute.routes)
                    }
                )
            }
        }
    }
}