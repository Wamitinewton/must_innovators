package com.newton.admin.presentation.notification.events

import java.time.LocalDateTime

sealed class NotificationEvent {
    data object SendNewsLetter : NotificationEvent()
    data object ToDefault : NotificationEvent()
    data class SubjectChange(val subject:String):NotificationEvent()
    data class MessageChange(val message:String):NotificationEvent()
    data class LoadingStateChange(val isLoading:Boolean):NotificationEvent()
    data class IsSuccessfullySent(val success:Boolean):NotificationEvent()
    data class ShowLinkDialog(val shown :Boolean):NotificationEvent()
    data class ShowDateDialog(val shown :Boolean):NotificationEvent()
    data class ShowTimeDialog(val shown :Boolean):NotificationEvent()
    data class ScheduledChanged(val scheduled: Boolean):NotificationEvent()
    data class LinkChange(val link:String):NotificationEvent()
    data class LinkTitleChange(val title:String):NotificationEvent()
    data class SelectedDateChange(val date:LocalDateTime):NotificationEvent()
    data class ScheduledDateTimeChanged(val date:LocalDateTime):NotificationEvent()
}