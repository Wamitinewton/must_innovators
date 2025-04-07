/**
 * Copyright (c) 2025 Meru Science Innovators Club
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Meru Science Innovators Club.
 * You shall not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement you entered into with Meru Science Innovators Club.
 *
 * Unauthorized copying of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 *
 * NO WARRANTY: This software is provided "as is" without warranty of any kind,
 * either express or implied, including but not limited to the implied warranties
 * of merchantability and fitness for a particular purpose.
 */
package com.newton.admin.presentation.events.events

import java.io.File
import java.time.LocalDateTime

sealed class AddEventEvents{
    data class ChangedCategory(val category:String): AddEventEvents()
//    data class Dialog(val shown: Boolean):AddEventEvents()
    data object PickImage :AddEventEvents()
    data class Sheet(val shown:Boolean):AddEventEvents()
    data class ShowDateDialog(val shown:Boolean):AddEventEvents()
    data class ShowTimeDialog(val shown:Boolean):AddEventEvents()
    data class SelectedDateChange(val date:LocalDateTime):AddEventEvents()
    data class ScheduledDateTimeChanged(val date:LocalDateTime):AddEventEvents()
    data class ChangeDate(val date: Long):AddEventEvents()
    data class ChangedFile(val file: File?):AddEventEvents()
    data class ChangedLocation(val location:String):AddEventEvents()
    data class ChangedOrganizer(val organizer:String):AddEventEvents()
    data object AddEvent : AddEventEvents()
    data object ClearImage : AddEventEvents()
    data object ToDefaultSate : AddEventEvents()
    data class ChangedMeetingLink(val link:String):AddEventEvents()
    data class ChangedName(val name:String):AddEventEvents()
    data class ChangedDescription(val description:String):AddEventEvents()
    data class ChangedContactEmail(val email:String):AddEventEvents()
    data class ChangedTitle(val title:String):AddEventEvents()
    data class ChangedVirtual(val isVirtual:Boolean):AddEventEvents()
}
