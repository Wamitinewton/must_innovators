package com.newton.communities.presentation.view.about_us

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
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
import com.newton.communities.presentation.events.UiEvent
import com.newton.communities.presentation.view.about_us.composables.AboutSection
import com.newton.communities.presentation.view.about_us.composables.CommunityCard
import com.newton.communities.presentation.view.about_us.composables.CommunityCardShimmer
import com.newton.communities.presentation.view.about_us.composables.ExecutivesSection
import com.newton.communities.presentation.view.about_us.composables.SectionHeading
import com.newton.communities.presentation.view.about_us.composables.VisionAndMission
import com.newton.communities.presentation.view_model.CommunitiesViewModel
import com.newton.core.domain.models.about_us.Community

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutUsScreen(
    onCommunityDetailsClick: (Community) -> Unit,
    communitiesViewModel: CommunitiesViewModel
) {
    val state by communitiesViewModel.communityState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val communities = state.communities
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

    val executives = remember {
        listOf(
            ExecutiveMember(id = "1", name = "Newton Wamiti", role = "Android Lead", department = "Software Engineering"),
            ExecutiveMember(id = "2", name = "Ephy Mucira", role = "Machine Learning Lead", department = "Data Science"),
            ExecutiveMember(id = "3", name = "Emmanuel Bett", role = "Ex-Android Lead", department = "Software Engineering"),
            ExecutiveMember(id = "4", name = "Steve Omondi", role = "Backend Lead", department = "Software Engineering"),
            ExecutiveMember(id = "5", name = "Jairus Musundi", role = "Ex-IOT & Robotics Lead", department = "Engineering"),
            ExecutiveMember(id = "6", name = "Brian Mong'are", role = "UI/UX Lead", department = "Design & Animation"),
            ExecutiveMember(id = "7", name = "Bryson Kangai", role = "Graphics Design Lead", department = "Design & Animation"),
            ExecutiveMember(id = "8", name = "Lewis Wanjohi", role = "Java Lead", department = "Software Engineering"),
            ExecutiveMember(id = "9", name = "Joy Shaney", role = "Social Media Manager", department = "Social Media"),
            ExecutiveMember(id = "10", name = "Grace Ngari", role = "Women Tech Makers Lead", department = "Information Technology")
        )
    }

    // LaunchedEffect for error handling remains unchanged
    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { errorMessage ->
            val result = snackBarHostState.showSnackbar(
                message = errorMessage,
                actionLabel = "Retry",
                duration = SnackbarDuration.Indefinite
            )
            when (result) {
                SnackbarResult.ActionPerformed -> communitiesViewModel.onEvent(UiEvent.RefreshCommunities)
                SnackbarResult.Dismissed -> {}
            }
            communitiesViewModel.onEvent(UiEvent.DismissError)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = communityName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
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

            if (!state.isLoading) {
                item {
                    Text(
                        text = "Explore our specialized communities that focus on different aspects of technology",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }


            when {
                state.isLoading -> {
                    items(4) {
                        CommunityCardShimmer()
                    }
                }
                else -> {
                    items(communities.size) { index ->
                        val community = communities[index]
                        CommunityCard(
                            modifier = Modifier.animateItem(
                                placementSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                )
                            ),
                            community = community,
                            onSeeDetailsClick = {
                                onCommunityDetailsClick(community)
                            }
                        )
                    }
                }
            }

            item {
                SectionHeading(
                    title = "Executive Team",
                    icon = Icons.Filled.Person
                )
            }

            item {
                ExecutivesSection(executives = executives)
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}