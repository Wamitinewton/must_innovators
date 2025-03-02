package com.newton.meruinnovators.activity

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.newton.core.navigation.NavigationRoutes
import com.newton.meruinnovators.navigation.AdminNavBar
import com.newton.meruinnovators.navigation.BottomNavigationBar
import com.newton.meruinnovators.navigation.MeruInnovatorsNavigation
import com.newton.meruinnovators.navigation.NavigationSubGraphs
import com.newton.common_ui.theme.ThemeUtils.MeruinnovatorsTheme

@Composable
fun RootScreen(navigationSubGraphs: NavigationSubGraphs) {
    val navController = rememberNavController()
    val currentBackStackEntryAsState by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntryAsState?.destination
    val context = LocalContext.current

    val isShowBottomBar = when (currentDestination?.route) {
        NavigationRoutes.HomeRoute.routes, NavigationRoutes.EventsRoute.routes, NavigationRoutes.BlogsRoute.routes,
        NavigationRoutes.AdminDashboard.routes, NavigationRoutes.AdminEvents.routes, NavigationRoutes.AdminFeedbacks.routes,
        NavigationRoutes.AccountRoute.routes, NavigationRoutes.AdminSettings.routes -> true

        else -> false
    }
    val isAdminNavBar = when (currentDestination?.route) {
        NavigationRoutes.AdminDashboard.routes, NavigationRoutes.AdminEvents.routes, NavigationRoutes.AdminFeedbacks.routes,
        NavigationRoutes.AdminSettings.routes -> true

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
                if (isAdminNavBar) AdminNavBar(
                    navController,
                    currentDestination
                ) else BottomNavigationBar(navController, currentDestination)
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
