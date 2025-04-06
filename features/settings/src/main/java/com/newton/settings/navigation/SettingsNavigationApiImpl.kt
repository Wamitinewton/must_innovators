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
package com.newton.settings.navigation

import androidx.navigation.*
import androidx.navigation.compose.*
import com.newton.navigation.*
import com.newton.settings.presentation.view.*

class SettingsNavigationApiImpl : SettingNavigationApi {
    override fun registerGraph(navGraphBuilder: NavGraphBuilder, navHostController: NavHostController) {
        navGraphBuilder.navigation(
            route = NavigationSubGraphRoutes.Settings.route,
            startDestination = NavigationRoutes.SettingsRoute.routes
        ) {
            composable(route = NavigationRoutes.SettingsRoute.routes) {
                SettingsScreen(
                    onBackPressed = {},
                    onClearCache = {},
                    onNotificationSettingsChanged = {},
                    onPrivacyPolicyClicked = {},
                    onTermsOfServiceClicked = {},
                    onAboutUsClicked = {},
                    onHelpAndSupportClicked = {}
                )
            }
        }
    }
}
