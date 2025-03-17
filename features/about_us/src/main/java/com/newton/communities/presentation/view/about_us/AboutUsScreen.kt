package com.newton.communities.presentation.view.about_us

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.newton.common_ui.composables.DefaultScaffold
import com.newton.communities.presentation.events.CommunityUiEvent
import com.newton.communities.presentation.view.about_us.composables.AboutSection
import com.newton.communities.presentation.view.about_us.composables.SectionHeading
import com.newton.communities.presentation.view.about_us.composables.VisionAndMission
import com.newton.communities.presentation.view_model.CommunitiesViewModel
import com.newton.communities.presentation.view_model.ExecutiveViewModel
import com.newton.core.domain.models.about_us.Community
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutUsScreen(
    onCommunityDetailsClick: (Community) -> Unit,
    communitiesViewModel: CommunitiesViewModel,
    executiveViewModel: ExecutiveViewModel
) {
    val uiState by communitiesViewModel.uiState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val communityName = "Meru Science Innovators Club"
    var isAboutExpanded by remember { mutableStateOf(false) }

    val aboutText = """
        Meru Science Innovators Club is a vibrant and dynamic collective of technology enthusiasts, 
        developers, designers, and innovators who share a common passion for cutting-edge 
        technology and its applications in solving real-world problems.
        
        Founded in 2018 at Meru University, our community has grown from a small group of 
        computer science students to a diverse ecosystem of tech professionals, academics, 
        and industry partners. We believe in the power of collaboration, knowledge sharing, 
        and continuous learning.
        
        Our community organizes regular workshops, hackathons, tech talks, and networking events 
        that bring together individuals from various technological backgrounds. We foster an 
        environment where ideas flourish, innovation is encouraged, and technical skills are 
        honed through practical application and peer learning.
        
        We are committed to making technology accessible to everyone, regardless of their 
        background or experience level. Our mentorship programs and beginner-friendly sessions 
        ensure that newcomers feel welcome and supported in their tech journey.
        
        Through partnerships with industry leaders and academic institutions, we provide our 
        members with opportunities to work on real projects, gain industry exposure, and stay 
        updated with the latest technological advancements.
    """.trimIndent()

    val mission = "To foster a collaborative environment that empowers individuals to explore, learn, and innovate in the field of technology, while building a supportive community that values diversity, inclusion, and continuous growth."

    val vision = "To be a leading technology community that inspires the next generation of tech innovators and contributes to technological advancement through education, collaboration, and practical application."

    LaunchedEffect(key1 = true) {
        communitiesViewModel.uiEvents.collectLatest { event ->
            when (event) {
                is CommunityUiEvent.Effect.ShowSnackbar -> {
                    val result = snackBarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = "Retry",
                        duration = SnackbarDuration.Long
                    )

                    if (result == SnackbarResult.ActionPerformed) {
                        communitiesViewModel.onEvent(CommunityUiEvent.Action.RefreshCommunities)
                    }
                }
            }
        }
    }

    DefaultScaffold(
        snackbarHostState = snackBarHostState,
        showOrbitals = true,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = communityName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                AboutSection(
                    aboutText = aboutText,
                    isExpanded = isAboutExpanded,
                    onReadMoreClick = { isAboutExpanded = !isAboutExpanded },
                )
            }

            item {
                VisionAndMission(
                    vision = vision,
                    mission = mission
                )
            }

            item {
                SectionHeading(
                    title = "Our Communities",
                    icon = Icons.Filled.Groups,
                )
            }

            item {
                CommunityContent(
                    uiState = uiState,
                    onCommunityDetailsClick = onCommunityDetailsClick,
                    communitiesViewModel = communitiesViewModel
                )
            }

            item {
                SectionHeading(
                    title = "Executive Team",
                    icon = Icons.Filled.Person
                )
            }

            item {
                ExecutivesSection(viewModel = executiveViewModel)
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }

}