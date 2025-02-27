package com.newton.communities.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.BrightnessMedium
import androidx.compose.material.icons.outlined.Computer
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material.icons.outlined.Smartphone
import androidx.compose.material.icons.outlined.ViewDay
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.newton.communities.presentation.view.composables.AboutSection
import com.newton.communities.presentation.view.composables.ExecutivesSection
import com.newton.communities.presentation.view.composables.SectionHeading
import com.newton.communities.presentation.view.composables.SubgroupsSection
import com.newton.communities.presentation.view.composables.VisionAndMission

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutUsScreen() {
    val communityName = "Meru Science innovators Club"

    val aboutText = """
        Meru Science innovators Club is a vibrant and dynamic collective of technology enthusiasts, 
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

    val subgroups = remember {
        listOf(
            CommunitySubgroup(
                id = "1",
                name = "Android Development",
                description = "Building innovative mobile applications for Android platform using Kotlin and Jetpack libraries.",
                memberCount = 48,
                icon = Icons.Outlined.Smartphone,
                color = Color(0xFF3DDC84) // Android green
            ),
            CommunitySubgroup(
                id = "2",
                name = "Graphic Design",
                description = "Exploring visual communication and design principles for digital products and brand identities.",
                memberCount = 35,
                icon = Icons.Outlined.BrightnessMedium,
                color = Color(0xFFFF7043) // Orange
            ),
            CommunitySubgroup(
                id = "3",
                name = "Machine Learning",
                description = "Exploring algorithms and statistical models for predictive analysis and pattern recognition.",
                memberCount = 42,
                icon = Icons.Outlined.Psychology,
                color = Color(0xFF4285F4) // Google blue
            ),
            CommunitySubgroup(
                id = "4",
                name = "Web Development",
                description = "Creating responsive and accessible web applications using modern frameworks and technologies.",
                memberCount = 53,
                icon = Icons.Outlined.Computer,
                color = Color(0xFF42A5F5) // Blue
            ),
            CommunitySubgroup(
                id = "5",
                name = "UI/UX Design",
                description = "Crafting user-centered design solutions for digital products and experiences.",
                memberCount = 31,
                icon = Icons.Outlined.ViewDay,
                color = Color(0xFFEC407A) // Pink
            ),
            CommunitySubgroup(
                id = "6",
                name = "IEEE",
                description = "Crafting user-centered design solutions for digital products and experiences.",
                memberCount = 31,
                icon = Icons.Outlined.ViewDay,
                color = Color(0xFFFFA726) // Amber
            ),
            CommunitySubgroup(
                id = "7",
                name = "Cyber Security",
                description = "Crafting user-centered design solutions for digital products and experiences.",
                memberCount = 31,
                icon = Icons.Outlined.ViewDay,
                color = Color(0xFF7E57C2) // Purple
            ),
            CommunitySubgroup(
                id = "8",
                name = "Women Tech Makers",
                description = "Crafting user-centered design solutions for digital products and experiences.",
                memberCount = 31,
                icon = Icons.Outlined.ViewDay,
                color = Color(0xFF26A69A) // Teal
            )
        )
    }


    val executives = remember {
        listOf(
            ExecutiveMember(
                id = "1",
                name = "Newton Wamiti",
                role = "Android Lead",
                department = "Software Engineering"
            ),
            ExecutiveMember(
                id = "2",
                name = "Ephy Mucira",
                role = "Machine Learning Lead",
                department = "Data Science"
            ),
            ExecutiveMember(
                name = "Emmanuel Bett",
                id = "3",
                role = "Ex-Android Lead",
                department = "Software Engineering"
            ),
            ExecutiveMember(
                id = "4",
                name = "Steve Omondi",
                role = "Backend Lead",
                department = "Software Engineering"
            ),
            ExecutiveMember(
                id = "5",
                name = "Jairus Musundi",
                role = "Ex-IOT & Robotics Lead",
                department = "Engineering"
            ),
            ExecutiveMember(
                id = "6",
                name = "Brian Mong'are",
                role = "UI/UX Lead",
                department = "Design & Animation"
            ),
            ExecutiveMember(
                id = "7",
                name = "Bryson Kangai",
                role = "Graphics Design Lead",
                department = "Design & Animation"
            ),
            ExecutiveMember(
                id = "8",
                name = "Lewis Wanjohi",
                role = "Java Lead",
                department = "Software Engineering"
            ),
            ExecutiveMember(
                id = "9",
                name = "Joy Shaney",
                role = "Social Media Manager",
                department = "Social Media"
            ),
            ExecutiveMember(
                id = "10",
                name = "Grace Ngari",
                role = "Women Tech Makers Lead",
                department = "Information Technology"
            )
        )
    }

    var isAboutExpanded by remember { mutableStateOf(false) }
    var showAllSubgroups by remember { mutableStateOf(false) }

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
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
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
                    onSeeFullBioClick = {

                    }
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
                    showSeeAll = subgroups.size > 4,
                    onSeeAllClick = { showAllSubgroups = !showAllSubgroups }
                )
            }

            item {
                SubgroupsSection(
                    subgroups = if (showAllSubgroups) subgroups else subgroups.take(4),
                    showAll = showAllSubgroups
                )
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