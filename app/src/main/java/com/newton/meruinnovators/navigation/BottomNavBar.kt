package com.newton.meruinnovators.navigation


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import com.newton.core.navigation.NavigationSubGraphRoutes


@Composable
fun BottomNavigationBar(navController: NavHostController,currentDestination: NavDestination?) {
    BottomAppBar(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .clip(
                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
            )
    ) {
        bottomNavigationDestinations.forEach { destination ->
            val selected = currentDestination?.hierarchy?.any {
                it.route == destination.route
            } ?: false
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
    val iconColor = if (isSelected) {
        MaterialTheme.colorScheme.secondary
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    val textColor = if (isSelected) {
        MaterialTheme.colorScheme.secondary
    } else {
        MaterialTheme.colorScheme.onSurface
    }


    Column(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .clickable(
                onClick = onClick
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

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
                imageVector = if (isSelected) {
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