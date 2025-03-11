package com.newton.home.presentation.states

import com.newton.home.domain.models.AboutUs
import com.newton.home.domain.models.Partner

data class HomeState(
    val partners: List<Partner> = emptyList(),
    val aboutUs: AboutUs? = null
)
