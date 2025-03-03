package com.newton.home.presentation.states

import com.newton.events.domain.models.Event
import com.newton.home.domain.models.AboutUs
import com.newton.home.domain.models.Partner
import com.newton.home.domain.models.Testimonial

data class HomeState(
    val partners: List<Partner> = emptyList(),
    val aboutUs: AboutUs? = null
)
