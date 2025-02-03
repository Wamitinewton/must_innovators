package com.newton.auth.navigation

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.newton.auth.presentation.login.LoginScreen
import com.newton.on_boarding.OnboardingScreen
import com.newton.auth.presentation.sign_up.view.SignupScreen
import com.newton.auth.presentation.sign_up.viewmodel.SignupViewModel
import com.newton.core.navigation.NavigationRoutes
import com.newton.core.navigation.NavigationSubGraphRoutes
import com.newton.core.network.NetworkConfiguration
import dagger.hilt.android.qualifiers.ApplicationContext

class AuthNavigationApiImpl: AuthNavigationApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController,
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubGraphRoutes.Auth.route,
            startDestination = NavigationRoutes.OnboardingRoute.routes
        ){
            composable(route = NavigationRoutes.SignupRoute.routes) {
                val signupViewModel = hiltViewModel<SignupViewModel>()
                SignupScreen(
                    signupViewModel = signupViewModel,
                    navHostController = navHostController,
                )
            }
            composable(route = NavigationRoutes.OnboardingRoute.routes) {
                OnboardingScreen(navHostController = navHostController)
            }
            composable(route = NavigationRoutes.LoginRoute.routes) {
                LoginScreen()
            }
        }
    }
}