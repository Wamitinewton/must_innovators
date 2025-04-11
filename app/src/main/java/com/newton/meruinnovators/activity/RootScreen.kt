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
package com.newton.meruinnovators.activity

import android.app.*
import androidx.activity.compose.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.*
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.google.accompanist.systemuicontroller.*
import com.newton.commonUi.theme.Theme.MeruinnovatorsTheme
import com.newton.meruinnovators.navigation.*
import com.newton.navigation.*

@Composable
fun RootScreen(navigationSubGraphs: NavigationSubGraphs,navController:NavHostController) {

    val currentBackStackEntryAsState by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntryAsState?.destination
    val context = LocalContext.current

    val isShowBottomBar =
        when (currentDestination?.route) {
            NavigationRoutes.HomeRoute.routes, NavigationRoutes.EventsRoute.routes, NavigationRoutes.BlogsRoute.routes,
            NavigationRoutes.AdminDashboard.routes, NavigationRoutes.AdminEvents.routes, NavigationRoutes.AdminFeedbacks.routes,
            NavigationRoutes.AccountRoute.routes, NavigationRoutes.AdminActions.routes
                -> true

            else -> false
        }
    val isAdminNavBar =
        when (currentDestination?.route) {
            NavigationRoutes.AdminDashboard.routes, NavigationRoutes.AdminEvents.routes, NavigationRoutes.AdminFeedbacks.routes,
            NavigationRoutes.AdminActions.routes
                -> true

            else -> false
        }
    if (currentDestination?.route == NavigationRoutes.HomeRoute.routes) {
        BackHandler {
            (context as? Activity)?.finish()
        }
    }
    MeruinnovatorsTheme(
        content = {
            SetupSystemUi(rememberSystemUiController(), MaterialTheme.colorScheme.background)
            Scaffold(
                bottomBar = {
                    if (!isShowBottomBar) {
                        return@Scaffold
                    }
                    if (isAdminNavBar) {
                        AdminNavBar(
                            navController,
                            currentDestination
                        )
                    } else {
                        BottomNavigationBar(navController, currentDestination)
                    }
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    MeruInnovatorsNavigation(
                        navigationSubGraphs = navigationSubGraphs,
                        navHostController = navController
                    )
                }
            }
        }
    )
}

@Composable
fun SetupSystemUi(
    systemUiController: SystemUiController,
    systemBarColor: Color
) {
    SideEffect {
        systemUiController.setSystemBarsColor(color = systemBarColor)
    }
}
