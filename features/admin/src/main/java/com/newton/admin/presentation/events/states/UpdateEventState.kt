package com.newton.admin.presentation.events.states

import java.io.File

data class UpdateEventState(
    val name: String? = null,
    val category: String? = null,
    val description: String? = null,
    val image: File?= null,
    val date: Long = System.currentTimeMillis(),
    val location: String? = null,
    val organizer: String? = null,
    val contactEmail: String? = null,
    val title: String? = null,
    val meetingLink : String? = null,
    val isVirtual: Boolean? = null,
    val errorMessage:String?=null,
    val isLoading:Boolean = false,
    val uploadSuccess:Boolean = false,

)
