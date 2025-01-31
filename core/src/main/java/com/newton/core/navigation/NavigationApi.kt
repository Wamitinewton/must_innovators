package com.newton.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface NavigationApi {
    fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    )
}