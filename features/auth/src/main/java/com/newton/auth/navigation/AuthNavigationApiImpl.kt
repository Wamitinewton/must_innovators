package com.newton.auth.navigation

import androidx.hilt.navigation.compose.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.newton.auth.presentation.login.view.*
import com.newton.auth.presentation.login.viewModel.*
import com.newton.auth.presentation.resetPassword.view.*
import com.newton.auth.presentation.resetPassword.viewModel.*
import com.newton.auth.presentation.signUp.view.*
import com.newton.auth.presentation.signUp.viewmodel.*
import com.newton.navigation.*
import com.newton.onBoarding.view.*

class AuthNavigationApiImpl : AuthNavigationApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubGraphRoutes.Auth.route,
            startDestination = NavigationRoutes.OnboardingRoute.routes
        ) {
            composable(route = NavigationRoutes.SignupRoute.routes) {
                val signupViewModel = hiltViewModel<SignupViewModel>()
                SignupRoute(
                    signupViewModel = signupViewModel,
                    onNavigateToOnboarding = {},
                    onContinueToLogin = {
                        navHostController.navigate(NavigationRoutes.LoginRoute.routes) {
                            popUpTo(NavigationSubGraphRoutes.Auth.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable(route = NavigationRoutes.OnboardingRoute.routes) {
                OnboardingScreen(
                    onLoginClick = {
                        navHostController.navigate(NavigationRoutes.LoginRoute.routes) {
                            popUpTo(NavigationRoutes.OnboardingRoute.routes)
                        }
                    },
                    onSignupClick = {
                        navHostController.navigate(NavigationRoutes.SignupRoute.routes) {
                            popUpTo(NavigationRoutes.OnboardingRoute.routes)
                        }
                    }
                )
            }
            composable(route = NavigationRoutes.LoginRoute.routes) {
                val loginViewModel = hiltViewModel<LoginViewModel>()
                LoginScreen(
                    navHostController = navHostController,
                    loginViewModel = loginViewModel,
                    onForgotPasswordClick = { navHostController.navigate(NavigationRoutes.ForgotPasswordRoute.routes) }
                )
            }

            composable(route = NavigationRoutes.ForgotPasswordRoute.routes) {
                val forgotPasswordViewModel = hiltViewModel<ForgotPasswordViewModel>()
                ForgotPasswordRoute(
                    onNavigateToLogin = {},
                    forgotPasswordViewModel = forgotPasswordViewModel
                )
            }

            composable(route = NavigationRoutes.SignupSuccessRoute.routes) {
                SignupSuccessScreen(
                    onContinueClick = {
                        navHostController.navigate(NavigationRoutes.LoginRoute.routes) {
                            popUpTo(NavigationSubGraphRoutes.Auth.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(route = NavigationRoutes.UserDataLoadingRoute.routes) {
                val userViewModel = hiltViewModel<GetUserDataViewModel>()
                UserDataLoadingScreen(
                    viewModel = userViewModel,
                    onNavigateToHome = {
                        navHostController.navigate(NavigationRoutes.HomeRoute.routes) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
