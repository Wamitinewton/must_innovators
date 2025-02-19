package com.newton.home.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.newton.core.domain.models.event_models.EventsData
import com.newton.home.domain.models.AboutUs
import com.newton.home.domain.models.Partner
import com.newton.home.domain.models.Testimonial
import com.newton.home.presentation.view.composables.AboutUsSection
import com.newton.home.presentation.view.composables.PartnersSection
import com.newton.home.presentation.view.composables.TestimonialsSection
import com.newton.home.presentation.view.composables.UpcomingEventsSection

@Composable
fun HomeScreen() {
    Scaffold {
        val configuration = LocalConfiguration.current
        Box(
            modifier = Modifier.padding(it)
                .padding(horizontal = 8.dp)
        ) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                item {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Upcoming events",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Box(
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.large)
                                .background(color = MaterialTheme.colorScheme.secondaryContainer)
                                .padding(horizontal = 12.dp, vertical = 5.dp)
                                .clickable {

                                }
                        ) {
                            Text("ALL", style = MaterialTheme.typography.titleMedium)
                        }
                    }
                }
                item {
                    UpcomingEventsSection(
                        events = sampleEvents,
                        onRSVPClick = { eventId ->
                        },
                        configuration = configuration
                    )
                }
                item {
                    Text(
                        text = "Our Partners",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
                item {
                    PartnersSection(
                        partners = samplePartners
                    )
                }
                item {
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Testimonials",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Box(
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.large)
                                .background(color = MaterialTheme.colorScheme.secondaryContainer)
                                .padding(horizontal = 12.dp, vertical = 5.dp)
                                .clickable {

                                }
                        ) {
                            Text("ALL", style = MaterialTheme.typography.titleMedium)
                        }
                    }
                }
                item {
                    TestimonialsSection(
                        testimonials = sampleTestimonials,
                        configuration = configuration
                    )
                }
                item {
                    Text(
                        text = "About Us",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
                item {
                    AboutUsSection(
                        aboutUs = sampleAboutUs,
                        configuration = configuration
                    )
                }
            }
        }
    }
}


val samplePartners = listOf(
    Partner(
        id = 1,
        name = "Partner 1",
        imageUrl = "https://images.unsplash.com/photo-1739369122285-8560a5eb18fd?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxmZWF0dXJlZC1waG90b3MtZmVlZHwxMXx8fGVufDB8fHx8fA%3D%3D"
    ),
    Partner(
        id = 2,
        name = "Partner 2",
        imageUrl = "https://images.unsplash.com/photo-1739369122285-8560a5eb18fd?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxmZWF0dXJlZC1waG90b3MtZmVlZHwxMXx8fGVufDB8fHx8fA%3D%3D"
    ),
    Partner(
        id = 3,
        name = "Partner 3",
        imageUrl = "https://images.unsplash.com/photo-1739369122285-8560a5eb18fd?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxmZWF0dXJlZC1waG90b3MtZmVlZHwxMXx8fGVufDB8fHx8fA%3D%3D"
    ),
    Partner(
        id = 4,
        name = "Partner 4",
        imageUrl = "https://images.unsplash.com/photo-1739369122285-8560a5eb18fd?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxmZWF0dXJlZC1waG90b3MtZmVlZHwxMXx8fGVufDB8fHx8fA%3D%3D"
    )
)
val sampleAboutUs = AboutUs(
    title = "Innovation Club",
    description = "Meru Science Innovation Club is meant to be helpful in innovation and mentoring students to find their trends in Technology field..."
)
val sampleEvents = listOf(
    EventsData(
        id = 1,
        title = "Android Hackathon",
        description = "Are you passionate about Android development and eager to explore the power of Kotlin? Join our vibrant community of developers, learners, and enthusiasts who are dedicated to mastering Android app development using Kotlin. Whether you're a beginner or an experienced developer, our club offers a collaborative space to learn, share, and grow together.",
        date = "12th October",
        contactEmail = "emmanuel@gmail.com",
        imageUrl = "https://images.unsplash.com/photo-1739772542563-b592f172282f?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxmZWF0dXJlZC1waG90b3MtZmVlZHwzMnx8fGVufDB8fHx8fA%3D%3D",
        isVirtual = true,
        location = "ECA21",
        name = "Robotics session",
        organizer = "Jairus Kibisu",
        category = "Robotics"
    ),
    EventsData(
        id = 2,
        title = "AI Workshop",
        description = "Hands-on workshop on ML & AI basics",
        date = "20th October",
        contactEmail = "emmanuel@gmail.com",
        imageUrl = "https://images.unsplash.com/photo-1739772542563-b592f172282f?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxmZWF0dXJlZC1waG90b3MtZmVlZHwzMnx8fGVufDB8fHx8fA%3D%3D",
        isVirtual = false,
        location = "ECA21",
        name = "Robotics session",
        organizer = "Grace Ngari",
        category = "Android",
    )
)
val sampleTestimonials = listOf(
    Testimonial(
        id = 1,
        author = "Bett",
        role = "Android Lead",
        content = "Contrary to popular belief, Lorem Ipsum is not simply random text..."
    )
)