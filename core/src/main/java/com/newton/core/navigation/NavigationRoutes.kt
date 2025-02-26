package com.newton.core.navigation

sealed class NavigationRoutes(val routes: String) {
    data object SignupRoute: NavigationRoutes("sign-up-screen")
    data object OnboardingRoute: NavigationRoutes("onboarding_routes")
    data object LoginRoute: NavigationRoutes("login_route")
    data object UserDataLoadingRoute: NavigationRoutes("userdata_loading")
    data object SignupSuccessRoute: NavigationRoutes("signup_success")
    data object EventsRoute: NavigationRoutes("event_screen")
    data object AccountRoute: NavigationRoutes("account_screen")
    data object BlogsRoute: NavigationRoutes("blogs_screen")
    data object HomeRoute: NavigationRoutes("home_screen")
    data object EventDetailsRoute: NavigationRoutes("event_details")
    data object AdminDashboard: NavigationRoutes("admin_dashboard")
    data object AdminEvents: NavigationRoutes("admin_events")
    data object AdminFeedbacks: NavigationRoutes("admin_feedbacks")
    data object AdminSettings: NavigationRoutes("admin_settings")
    data object EventSearchScreen: NavigationRoutes("event_search")
    data object AddEvent: NavigationRoutes("add_event_screen")
    data object ModifyEvent: NavigationRoutes("edit_event_screen")
    data object AddPartners: NavigationRoutes("add_partners_screen")
    data object AddCommunity: NavigationRoutes("add_community_screen")
    data object SendNewsLetter: NavigationRoutes("send_newsletter_screen")
}