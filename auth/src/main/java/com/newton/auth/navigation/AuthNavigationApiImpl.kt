package com.newton.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.newton.on_boarding.OnboardingScreen
import com.newton.auth.presentation.sign_up.view.SignupScreen
import com.newton.core.navigation.NavigationRoutes
import com.newton.core.navigation.NavigationSubGraphRoutes

class AuthNavigationApiImpl: AuthNavigationApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubGraphRoutes.Auth.route,
            startDestination = NavigationRoutes.OnboardingRoute.routes
        ){
            composable(route = NavigationRoutes.SignupRoute.routes) {
                SignupScreen()
            }
            composable(route = NavigationRoutes.OnboardingRoute.routes) {
                OnboardingScreen(navHostController = navHostController)
            }
        }
    }
}