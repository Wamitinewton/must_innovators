package com.newton.admin.presentation.events.events

import com.newton.admin.domain.models.Session
import com.newton.admin.domain.models.Socials
import java.io.File

sealed class AddEventEvents {
    data class ChangedCategory(val category:String): AddEventEvents()
    data class Dialog(val shown: Boolean):AddEventEvents()
    data object PickImage :AddEventEvents()
    data class Sheet(val shown:Boolean):AddEventEvents()
    data class ChangeDate(val date: Long):AddEventEvents()
    data class ChangedFile(val file: File):AddEventEvents()
    data class ChangedLocation(val location:String):AddEventEvents()
    data class ChangedOrganizer(val organizer:String):AddEventEvents()
    data object AddEvent : AddEventEvents()
    data object ClearImage : AddEventEvents()
    data class ChangedMeetingLink(val link:String):AddEventEvents()
    data class ChangedName(val name:String):AddEventEvents()
    data class ChangedDescription(val description:String):AddEventEvents()
    data class ChangedContactEmail(val email:String):AddEventEvents()
    data class ChangedTitle(val title:String):AddEventEvents()
    data class ChangedVirtual(val isVirtual:Boolean):AddEventEvents()
}