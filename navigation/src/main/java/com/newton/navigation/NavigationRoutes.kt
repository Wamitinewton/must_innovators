/**
 * Copyright (c) 2025 Meru Science Innovators Club
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Meru Science Innovators Club.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Meru Science Innovators Club.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 *
 * NO WARRANTY: This software is provided "as is" without warranty of any kind,
 * either express or implied, including but not limited to the implied warranties
 * of merchantability and fitness for a particular purpose.
 */
package com.newton.navigation

sealed class NavigationRoutes(val routes: String) {
    data object SignupRoute : NavigationRoutes("sign-up-screen")

    data object OnboardingRoute : NavigationRoutes("onboarding_routes")

    data object LoginRoute : NavigationRoutes("login_route")

    data object UserDataLoadingRoute : NavigationRoutes("userdata_loading")

    data object SignupSuccessRoute : NavigationRoutes("signup_success")

    data object EventsRoute : NavigationRoutes("event_screen")

    data object AccountRoute : NavigationRoutes("account_screen")

    data object BlogsRoute : NavigationRoutes("blogs_screen")

    data object HomeRoute : NavigationRoutes("home_screen")

    data object EventDetailsRoute : NavigationRoutes("event_details")

    data object AdminDashboard : NavigationRoutes("admin_dashboard")

    data object AdminEvents : NavigationRoutes("admin_events")

    data object AdminFeedbacks : NavigationRoutes("admin_feedbacks")

    data object AdminActions : NavigationRoutes("admin_actions")

    data object AddEvent : NavigationRoutes("add_event_screen")

    data object ModifyEvent : NavigationRoutes("edit_event_screen")

    data object AddPartners : NavigationRoutes("add_partners_screen")

    data object AddCommunity : NavigationRoutes("add_community_screen")

    data object EventRegistrationScreen : NavigationRoutes("event_registration")

    data object EventRegistrationSuccessScreen : NavigationRoutes("event_registration_success")

    data object EventTicketsRoute : NavigationRoutes("event_tickets")

    data object AboutUsRoute : NavigationRoutes("about_us")

    data object UpdateCommunity : NavigationRoutes("update_community")

    data object AdminCommunityList : NavigationRoutes("admin_community_list")

    data object UpdateExecutive : NavigationRoutes("update_executives")

    data object SendNewsLetter : NavigationRoutes("news_letter_screen")

    data object CommunitiesDetailsRoute : NavigationRoutes("community_details")

    data object GeneralFeedbackRoute : NavigationRoutes("general_feedback")

    data object BugReportingScreen : NavigationRoutes("bug_reporting")

    data object ProfileUpdateScreen : NavigationRoutes("profile_update")

    data object ForgotPasswordRoute : NavigationRoutes("forgot_password")

    data object DeleteAccountRoute : NavigationRoutes("delete_account")

    data object DeleteAccountSuccessRoute : NavigationRoutes("delete_account_success")

    data object CreateTestimonialsRoute : NavigationRoutes("create_testimonial")

    data object ClubUpdate : NavigationRoutes("club_update_screen")

    data object PartnersDetails : NavigationRoutes("partners_details")

    data object SettingsRoute : NavigationRoutes("settings_screen")

    data object AllTestimonialsRoute : NavigationRoutes("all_testimonials")

    data object AllPartnersRoute : NavigationRoutes("all_partners")

    data object AlumniScreen : NavigationRoutes("alumni")
}
