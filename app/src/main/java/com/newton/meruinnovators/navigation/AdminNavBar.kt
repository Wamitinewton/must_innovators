package com.newton.meruinnovators.navigation

import androidx.compose.foundation.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.unit.*
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hierarchy
import com.newton.navigation.*

@Composable
fun AdminNavBar(
    navController: NavHostController,
    currentDestination: NavDestination?
) {
    BottomAppBar(
        modifier =
        Modifier
            .background(MaterialTheme.colorScheme.background)
            .clip(
                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
            )
    ) {
        adminNavDestinations.forEach { destination ->
            val selected =
                currentDestination?.hierarchy?.any {
                    it.route == destination.route
                } ?: false
            BottomNavItem(
                isSelected = selected,
                destination = destination,
                onClick = {
                    if (!selected) {
                        destination.route.let {
                            navController.navigate(it) {
                                launchSingleTop = true
                                popUpTo(NavigationRoutes.AdminDashboard.routes)
                            }
                        }
                    }
                }
            )
        }
    }
}
