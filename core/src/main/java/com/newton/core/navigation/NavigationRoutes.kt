package com.newton.core.navigation

sealed class NavigationRoutes(val routes: String) {
    data object SignupRoute: NavigationRoutes("sign-up-screen")
    data object OnboardingRoute: NavigationRoutes("onboarding_routes")
    data object LoginRoute: NavigationRoutes("login_route")
    data object SignupSuccessRoute: NavigationRoutes("signup_success")
    data object EventsRoute: NavigationRoutes("event_screen")
}