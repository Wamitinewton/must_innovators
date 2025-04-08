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

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.unit.*
import com.newton.commonUi.composables.*
import com.newton.communities.presentation.view.*
import com.newton.communities.presentation.viewModel.*
import com.newton.home.presentation.viewmodels.*
import com.newton.network.domain.models.aboutUs.*
import com.newton.network.domain.models.homeModels.*
import com.newton.network.domain.models.testimonials.*
import com.newton.testimonials.presentation.view.pagedTestimonials.*
import com.newton.testimonials.presentation.viewModel.*

@Composable
fun HomeScreen(
    partnersViewModel: PartnersViewModel,
    getTestimonialsViewModel: GetTestimonialsViewModel,
    onNavigateToAdmin: () -> Unit,
    onNavigateToCommunityDetails: (Community) -> Unit,
    onPartnerClick: (PartnersData) -> Unit,
    communitiesViewModel: CommunitiesViewModel,
    onSeeAllTestimonials: () -> Unit
) {
    val partnersState by partnersViewModel.partnersState.collectAsState()
    val testimonialsUiState by getTestimonialsViewModel.uiState.collectAsState()
    val communitiesState by communitiesViewModel.uiState.collectAsState()

    var selectedTestimonial by remember { mutableStateOf<TestimonialsData?>(null) }

    selectedTestimonial?.let { testimonial ->
        TestimonialDetailSheet(
            testimonialsData = testimonial,
            onDismiss = { selectedTestimonial = null }
        )
    }

    DefaultScaffold(
        showOrbitals = true,
        topBar = {
            MeruInnovatorsAppBar(
                title = "MUST Innovators",
                actions = {
                    Box(
                        modifier =
                        Modifier
                            .clip(CircleShape)
                            .clickable {
                                onNavigateToAdmin()
                            }
                            .padding(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AdminPanelSettings,
                            contentDescription = "Admin panel"
                        )
                    }

                    Box(
                        modifier =
                        Modifier
                            .clip(CircleShape)
                            .clickable {
                            }
                            .padding(10.dp)
                    ) {
                        Icon(Icons.Filled.NotificationsActive, contentDescription = "Notifications")
                    }
                }
            )
        }
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            item {
                SectionHeader(
                    title = "Our Partners",
                    showViewAll = false
                )
            }

            item {
                PartnersContent(
                    partnersState = partnersState,
                    onRetry = { partnersViewModel.refreshPartners() },
                    onPartnerClick = onPartnerClick
                )
            }

            item {
                SectionHeader(
                    title = "Testimonials",
                    showViewAll = true,
                    onViewAllClick = onSeeAllTestimonials
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
                    title = "Communities",
                    showViewAll = true,
                    onViewAllClick = { }
                )
            }


            item {
                CommunityContent(
                    uiState = communitiesState,
                    onCommunityDetailsClick = onNavigateToCommunityDetails,
                    communitiesViewModel = communitiesViewModel
                )
            }

            item {
                SectionHeader(
                    title = "About Us",
                    showViewAll = false
                )
            }

            item {
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}
