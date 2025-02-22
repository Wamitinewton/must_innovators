package com.newton.admin.presentation.events.events

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
}