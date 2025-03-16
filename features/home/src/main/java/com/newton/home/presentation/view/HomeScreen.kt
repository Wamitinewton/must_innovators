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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.newton.common_ui.composables.DefaultScaffold
import com.newton.common_ui.composables.MeruInnovatorsAppBar
import com.newton.home.domain.models.AboutUs
import com.newton.home.domain.models.Testimonial
import com.newton.home.presentation.view.composables.AboutUsSection
import com.newton.home.presentation.view.composables.PartnersContent
import com.newton.home.presentation.view.composables.SectionHeader
import com.newton.home.presentation.view.composables.TestimonialsSection
import com.newton.home.presentation.viewmodels.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToAdmin: () -> Unit,
    onNavigateToEvents: () -> Unit,
    onNavigateToAboutUs: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    val partnersState by viewModel.partnersState.collectAsState()

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
                            // Notification action
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
                    onRetry = { viewModel.refreshPartners() }
                )
            }

            item {
                SectionHeader(
                    title = "Testimonials",
                    showViewAll = true,
                    onViewAllClick = { /* View all testimonials action */ }
                )
            }

            item {
                TestimonialsSection(
                    testimonials = sampleTestimonials,
                    configuration = configuration,
                    context = context
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
                    aboutUs = sampleAboutUs,
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


val sampleAboutUs = AboutUs(
    title = "Innovation Club",
    description = "Meru Science Innovation Club is meant to be helpful in innovation and mentoring students to find their trends in Technology field..."
)
val sampleTestimonials = listOf(
    Testimonial(
        id = 1,
        author = "Bett",
        role = "Android Lead",
        content = "Meru Science Innovation Club is meant to be helpful in innovation and mentoring students to find their trends in Technology field..."
    ),
    Testimonial(
        id = 1,
        author = "Bett",
        role = "Android Lead",
        content = "Contrary to popular belief, Lorem Ipsum is not simply random text..."
    )
)
