package com.newton.meruinnovators.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController


@Composable
fun AdminNavBar(navController: NavHostController, currentDestination: NavDestination?) {
    BottomAppBar(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .clip(
                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
            )
    ) {
        adminNavDestinations.forEach { destination ->
            val selected = currentDestination?.hierarchy?.any {
                it.route == destination.route
            } ?: false
            BottomNavItem(
                isSelected = selected,
                destination = destination,
                onClick = {
                    destination.route.let {
                        navController.navigate(it) {
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}
