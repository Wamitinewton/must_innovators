package com.newton.account.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.newton.account.presentation.view.AccountScreen
import com.newton.core.navigation.NavigationRoutes
import com.newton.core.navigation.NavigationSubGraphRoutes

class AccountNavigationApiImpl: AccountNavigationApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController,
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubGraphRoutes.Account.route,
            startDestination = NavigationRoutes.AccountRoute.routes
        ){
            composable(route = NavigationRoutes.AccountRoute.routes) {
                AccountScreen(
                    onMyEventsClick = {
                        navHostController.navigate(NavigationRoutes.EventTicketsRoute.routes)
                    }
                )
            }
        }
    }
}