package com.newton.events.data.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventData(
    @SerialName("category")
    val category: String = "",
    @SerialName("contact_email")
    val contactEmail: String = "",
    @SerialName("date")
    val date: String = "",
    @SerialName("description")
    val description: String = "",
    @SerialName("id")
    val id: Int = 0,
    @SerialName("image")
    val image: String = "",
    @SerialName("is_virtual")
    val isVirtual: Boolean = false,
    @SerialName("location")
    val location: String = "",
    @SerialName("members")
    val members: Int = 0,
    @SerialName("name")
    val name: String = "",
    @SerialName("organizer")
    val organizer: String = "",
    @SerialName("title")
    val title: String = ""
)