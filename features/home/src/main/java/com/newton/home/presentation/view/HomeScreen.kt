/**
 * Copyright (c) 2025 Meru Science Innovators Club
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Meru Science Innovators Club.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Meru Science Innovators Club.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 *
 * NO WARRANTY: This software is provided "as is" without warranty of any kind,
 * either express or implied, including but not limited to the implied warranties
 * of merchantability and fitness for a particular purpose.
 */
package com.newton.home.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.*
import com.newton.commonUi.ui.*
import com.newton.communities.presentation.viewModel.*
import com.newton.network.domain.models.aboutUs.*
import com.newton.network.domain.models.homeModels.*
import com.newton.network.domain.models.testimonials.*
import com.newton.partners.presentation.viewModel.*
import com.newton.testimonials.presentation.view.pagedTestimonials.*
import com.newton.testimonials.presentation.viewModel.*

@Composable
fun HomeScreen(
    getTestimonialsViewModel: GetTestimonialsViewModel,
    onNavigateToAdmin: () -> Unit,
    onNavigateToCommunityDetails: (Community) -> Unit,
    onPartnerClick: (PartnersData) -> Unit,
    communitiesViewModel: CommunitiesViewModel,
    onSeeAllTestimonials: () -> Unit,
    onSeeAllPartners: () -> Unit,
    onNavigateToAboutUs: () -> Unit,
    onNavigateToExecutives: () -> Unit
) {
    val testimonialsUiState by getTestimonialsViewModel.uiState.collectAsState()

    var selectedTestimonial by remember { mutableStateOf<TestimonialsData?>(null) }

    selectedTestimonial?.let { testimonial ->
        TestimonialDetailSheet(
            testimonialsData = testimonial,
            onDismiss = { selectedTestimonial = null }
        )
    }

    CustomScaffold(
        topBar = {
            MeruInnovatorsAppBar(
                title = "MUST Innovators"
            )
        }
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            item {
                FeaturesGrid(
                    onFeatureClick = { route ->
                        when (route) {
                            "partners" -> onSeeAllPartners()
                            "testimonials" -> onSeeAllTestimonials()
                            "communities" -> { /* Navigate to communities */ }
                            "blogs" -> { /* Navigate to blogs */ }
                            "innovations" -> { /* Navigate to innovations */ }
                            "members" -> { /* Navigate to members */ }
                            "tickets" -> { /* Navigate to tickets */ }
                            "executives" -> onNavigateToExecutives()
                        }
                    },
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            item {
                SectionHeader(
                    title = "Testimonials",
                )
            }

            item {
                TestimonialsSection(
                    uiState = testimonialsUiState,
                    onRetryClick = {
                        getTestimonialsViewModel.retryLoadingTestimonials()
                    },
                    onTestimonialClick = { testimonial ->
                        selectedTestimonial = testimonial
                    }
                )
            }

            item {
                SectionHeader(
                    title = "About Us",
                )
            }

            item {
                AboutUsSection(
                    modifier = Modifier.padding(vertical = 8.dp),
                    onNavigateToAboutUs = onNavigateToAboutUs
                )
            }


            item {
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}
