package com.newton.auth.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.newton.auth.presentation.login.view.LoginScreen
import com.newton.auth.presentation.login.view.UserDataLoadingScreen
import com.newton.auth.presentation.login.view_model.GetUserDataViewModel
import com.newton.auth.presentation.login.view_model.LoginViewModel
import com.newton.on_boarding.view.OnboardingScreen
import com.newton.auth.presentation.sign_up.view.SignupScreen
import com.newton.auth.presentation.sign_up.view.SignupSuccessScreen
import com.newton.auth.presentation.sign_up.viewmodel.SignupViewModel
import com.newton.core.navigation.NavigationRoutes
import com.newton.core.navigation.NavigationSubGraphRoutes

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
                OnboardingScreen(
                    onLoginClick = {
                        navHostController.navigate(NavigationRoutes.LoginRoute.routes)
                    },
                    onSignupClick = {
                        navHostController.navigate(NavigationRoutes.SignupRoute.routes)
                    }
                )
            }
            composable(route = NavigationRoutes.LoginRoute.routes) {
                val loginViewModel = hiltViewModel<LoginViewModel>()
                LoginScreen(
                    navHostController = navHostController,
                    loginViewModel = loginViewModel
                )
            }

            composable(route = NavigationRoutes.SignupSuccessRoute.routes) {
                SignupSuccessScreen(navHostController = navHostController)
            }

            composable(route = NavigationRoutes.UserDataLoadingRoute.routes) {
                val userViewModel = hiltViewModel<GetUserDataViewModel>()
                UserDataLoadingScreen(
                    viewModel = userViewModel,
                    onNavigateToHome = {
                        navHostController.navigate(NavigationRoutes.EventsRoute.routes)
                    }
                )
            }
        }
    }
}