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

sealed class NavigationSubGraphRoutes(val route: String) {
    data object Auth : NavigationSubGraphRoutes(route = "/auth_")

    data object Home : NavigationSubGraphRoutes(route = "/home_")

    data object Event : NavigationSubGraphRoutes(route = "/event_")

    data object Blogs : NavigationSubGraphRoutes(route = "/blogs_")

    data object Account : NavigationSubGraphRoutes(route = "/account_")

    data object Admin : NavigationSubGraphRoutes(route = "/admin_")

    data object AboutUs : NavigationSubGraphRoutes(route = "/about_us")

    data object Communities : NavigationSubGraphRoutes(route = "/communities_")

    data object UserFeedback : NavigationSubGraphRoutes(route = "/user-feedback_")

    data object Settings : NavigationSubGraphRoutes(route = "/settings")
}
