package com.newton.admin.presentation.club.state

import com.newton.core.domain.models.admin.Socials

data class ClubState(
    val name:String = "",
    val clubDetails:String = "",
    val vision:String="",
    val mission:String="",
    val socials:List<Socials> = emptyList(),
    val isLoading:Boolean=false,
    val errorMessage:String?=null,
    val errors:Map<String,String> = emptyMap()
)
