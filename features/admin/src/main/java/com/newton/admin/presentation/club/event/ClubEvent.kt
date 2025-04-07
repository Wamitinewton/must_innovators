package com.newton.admin.presentation.club.event

import com.newton.core.domain.models.admin.Socials

sealed class ClubEvent {
    data class NameChanged(val name:String):ClubEvent()
    data class ClubDetailChanged(val aboutUs:String):ClubEvent()
    data class VisionChanged(val vision:String):ClubEvent()
    data class MissionChanged(val mission:String):ClubEvent()
    data class SocialsChanged(val social:List<Socials>):ClubEvent()
    data object UpdateClub:ClubEvent()
    data object ToDefault:ClubEvent()
    data object LoadClub:ClubEvent()
}