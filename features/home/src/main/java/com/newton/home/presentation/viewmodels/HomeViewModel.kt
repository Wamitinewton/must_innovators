package com.newton.home.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newton.events.domain.models.Event
import com.newton.home.domain.models.AboutUs
import com.newton.home.domain.models.Partner
import com.newton.home.domain.models.Testimonial
import com.newton.home.presentation.events.HomeEvent
import com.newton.home.presentation.states.HomeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeState())
    val uiState = _uiState.asStateFlow()

//    init {
//        viewModelScope.launch {
//            _uiState.value = HomeState(
//                upcomingEvents = sampleEvents,
//                partners = samplePartners,
//                testimonials = sampleTestimonials,
//                aboutUs = sampleAboutUs
//            )
//        }
//    }

    fun handleEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.RSVPEvent -> {
                toggleRSVP(event.eventId)
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
        Event(
            id = 1,
            title = "Android Hackathon",
            description = "Solve-It competition: map idea into a prototype",
            date = "12th October",
            contactEmail = "emmanuel@gmail.com",
            image = "https://images.unsplash.com/photo-1739772542563-b592f172282f?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxmZWF0dXJlZC1waG90b3MtZmVlZHwzMnx8fGVufDB8fHx8fA%3D%3D",
            isVirtual = true,
            location = "ECA21",
            name = "Robotics session",
            organizer = "Jairus Kibisu",
        ),
        Event(
            id = 2,
            title = "AI Workshop",
            description = "Hands-on workshop on ML & AI basics",
            date = "20th October",
            contactEmail = "emmanuel@gmail.com",
            image = "https://images.unsplash.com/photo-1739772542563-b592f172282f?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxmZWF0dXJlZC1waG90b3MtZmVlZHwzMnx8fGVufDB8fHx8fA%3D%3D",
            isVirtual = false,
            location = "ECA21",
            name = "Robotics session",
            organizer = "Grace Ngari",
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
    private fun toggleRSVP(eventId: Int) {
        viewModelScope.launch {
            val updatedEvents = _uiState.value.upcomingEvents.map { event ->
                if (event.id == eventId) {
                    event.copy(isVirtual = !event.isVirtual)
                } else event
            }
            _uiState.value = _uiState.value.copy(upcomingEvents = updatedEvents)
        }
    }

}