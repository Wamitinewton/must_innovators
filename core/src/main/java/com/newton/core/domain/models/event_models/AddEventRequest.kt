package com.newton.admin.domain.models

import androidx.compose.ui.graphics.drawscope.Stroke

data class AddEventRequest (
    val name: String,
    val category:String,
    val description:String,
    val image:String,
    val date: String,
    val location: String,
    val organizer:String,
    val contact_email:String,
    val title:String,
    val isVirtual:Boolean,
)
