package com.newton.home.presentation.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.newton.common_ui.composables.DefaultScaffold
import com.newton.common_ui.composables.MeruInnovatorsAppBar
import com.newton.core.domain.models.home_models.PartnersData
import com.newton.core.domain.models.testimonials.TestimonialsData
import com.newton.home.presentation.viewmodels.PartnersViewModel
import com.newton.home.presentation.viewmodels.TestimonialsViewModel

@Composable
fun HomeScreen(
    partnersViewModel: PartnersViewModel,
    testimonialsViewModel: TestimonialsViewModel,
    onNavigateToAdmin: () -> Unit,
    onNavigateToAboutUs: () -> Unit,
    onPartnerClick: (PartnersData) -> Unit
) {
    val configuration = LocalConfiguration.current
    val partnersState by partnersViewModel.partnersState.collectAsState()
    val testimonialsUiState by testimonialsViewModel.uiState.collectAsState()

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
                    Box(modifier = Modifier
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

                    Box(modifier = Modifier
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
                    onViewAllClick = { }
                )
            }

            item {
                TestimonialsSection(
                    uiState = testimonialsUiState,
                    onRetryClick = {
                        testimonialsViewModel.retryLoadingTestimonials()
                    },
                    onTestimonialClick = { testimonial ->
                        selectedTestimonial = testimonial
                    }
                )
            }

            item {
                SectionHeader(
                    title = "About Us",
                    showViewAll = false
                )
            }

            item {
                AboutUsSection(
                    configuration = configuration,
                    onClick = { onNavigateToAboutUs() }
                )
            }

            item {
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}



