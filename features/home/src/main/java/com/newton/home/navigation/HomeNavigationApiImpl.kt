package com.newton.home.navigation

import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.newton.core.utils.PackageHandlers
import com.newton.navigation.NavigationRoutes
import com.newton.navigation.NavigationSubGraphRoutes
import com.newton.home.presentation.view.HomeScreen
import com.newton.home.presentation.view.PartnerDetailsScreen
import com.newton.home.presentation.viewmodels.PartnersSharedViewModel
import com.newton.home.presentation.viewmodels.PartnersViewModel
import com.newton.home.presentation.viewmodels.TestimonialsViewModel

class HomeNavigationApiImpl: HomeNavigationApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController,
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubGraphRoutes.Home.route,
            startDestination = NavigationRoutes.HomeRoute.routes
        ){
            composable(route = NavigationRoutes.HomeRoute.routes) {
                val parentEntry = remember(it) {
                    navHostController.getBackStackEntry(NavigationSubGraphRoutes.Home.route)
                }
                val partnersViewModel = hiltViewModel<PartnersViewModel>()
                val testimonialsViewModel = hiltViewModel<TestimonialsViewModel>()
                val partnersSharedViewModel = hiltViewModel<PartnersSharedViewModel>(parentEntry)
                HomeScreen(
                    partnersViewModel = partnersViewModel,
                    testimonialsViewModel = testimonialsViewModel,
                    onNavigateToAdmin = {
                        navHostController.navigate(NavigationRoutes.AdminDashboard.routes)
                    },
                    onNavigateToAboutUs = {
                        navHostController.navigate(NavigationRoutes.AboutUsRoute.routes)
                    },
                    onPartnerClick = { partner ->
                        partnersSharedViewModel.updateSelectedPartner(partner)
                        navHostController.navigate(NavigationRoutes.PartnersDetails.routes)
                    }
                )
            }

            composable(route = NavigationRoutes.PartnersDetails.routes) {
                val parentEntry = remember(it) {
                    navHostController.getBackStackEntry(NavigationSubGraphRoutes.Home.route)
                }
                val partnersSharedViewModel = hiltViewModel<PartnersSharedViewModel>(parentEntry)
                val context = LocalContext.current
                PartnerDetailsScreen(
                    partnersSharedViewModel = partnersSharedViewModel,
                    onBackPressed = {
                        navHostController.navigateUp()
                    },
                    onSharePartner = { partner ->
                        PackageHandlers.sharePartner(context, partner)
                    },
                    onNavigateToWebsite = { url -> PackageHandlers.navigateToWebsite(context, url) },
                    onNavigateToLinkedIn = { linkedIn -> PackageHandlers.navigateToLinkedIn(context, linkedIn) },
                    onNavigateToTwitter = { twitter -> PackageHandlers.navigateToTwitter(context, twitter) },
                    onContactEmail = { email -> PackageHandlers.contactViaEmail(context, email) }
                )
            }
        }
    }
}