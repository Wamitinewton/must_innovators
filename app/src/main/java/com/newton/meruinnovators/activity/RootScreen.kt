package com.newton.meruinnovators.activity

import android.app.*
import androidx.activity.compose.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.*
import androidx.navigation.compose.*
import com.google.accompanist.systemuicontroller.*
import com.newton.meruinnovators.navigation.*
import com.newton.meruinnovators.ui.theme.ThemeUtils.MeruinnovatorsTheme
import com.newton.navigation.*

@Composable
fun RootScreen(navigationSubGraphs: NavigationSubGraphs) {
    val navController = rememberNavController()
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
    MeruinnovatorsTheme {
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
                modifier =
                Modifier
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
