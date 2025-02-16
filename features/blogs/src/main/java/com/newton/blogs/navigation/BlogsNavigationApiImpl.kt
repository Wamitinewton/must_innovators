package com.newton.events.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.newton.blogs.navigation.BlogsNavigationApi
import com.newton.blogs.presentation.view.BlogsScreen
import com.newton.core.navigation.NavigationRoutes
import com.newton.core.navigation.NavigationSubGraphRoutes

class BlogsNavigationApiImpl: BlogsNavigationApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController,
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubGraphRoutes.Blogs.route,
            startDestination = NavigationRoutes.BlogsRoute.routes
        ){
            composable(route = NavigationRoutes.BlogsRoute.routes) {
                BlogsScreen()
            }
        }
    }
}