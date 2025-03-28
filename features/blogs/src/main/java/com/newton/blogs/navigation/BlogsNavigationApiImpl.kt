package com.newton.blogs.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.newton.blogs.presentation.view.BlogsScreen
import com.newton.navigation.NavigationRoutes
import com.newton.navigation.NavigationSubGraphRoutes

class BlogsNavigationApiImpl: BlogsNavigationApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController,
    ) {
        navGraphBuilder.navigation(
            route =  NavigationSubGraphRoutes.Blogs.route,
            startDestination = NavigationRoutes.BlogsRoute.routes
        ){
            composable(route = NavigationRoutes.BlogsRoute.routes) {
                BlogsScreen()
            }
        }
    }
}