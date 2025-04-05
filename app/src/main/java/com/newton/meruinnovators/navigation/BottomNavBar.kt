package com.newton.meruinnovators.navigation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hierarchy
import com.newton.navigation.*

@Composable
fun BottomNavigationBar(
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
        bottomNavigationDestinations.forEach { destination ->
            val selected =
                currentDestination?.hierarchy?.any {
                    it.route == destination.route
                } ?: true
            BottomNavItem(
                isSelected = selected,
                destination = destination,
                onClick = {
                    /**
                     * Only navigate if the destination is not already selected
                     */
                    if (!selected) {
                        destination.route.let {
                            navController.navigate(it) {
                                launchSingleTop = true
                                popUpTo(NavigationRoutes.HomeRoute.routes)
                            }
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun RowScope.BottomNavItem(
    isSelected: Boolean,
    destination: Screens,
    onClick: () -> Unit
) {
    val iconColor =
        if (isSelected) {
            MaterialTheme.colorScheme.secondary
        } else {
            MaterialTheme.colorScheme.onSurface
        }

    val textColor =
        if (isSelected) {
            MaterialTheme.colorScheme.secondary
        } else {
            MaterialTheme.colorScheme.onSurface
        }

    Column(
        modifier =
        Modifier
            .weight(1f)
            .fillMaxHeight()
            .clickable(
                onClick = onClick
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        BadgedBox(badge = {
            if (destination.badgeCount != null) {
                Badge {
                    Text(text = destination.badgeCount.toString())
                }
            } else if (destination.hasNews) {
                Badge()
            }
        }) {
            Icon(
                imageVector =
                if (isSelected) {
                    destination.selectedIcon
                } else {
                    destination.unSelectedIcon
                },
                contentDescription = destination.title,
                tint = iconColor
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            destination.title,
            color = textColor,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Light
        )
    }
}
