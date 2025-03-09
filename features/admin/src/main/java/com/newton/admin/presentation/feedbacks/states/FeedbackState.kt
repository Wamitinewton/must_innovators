package com.newton.admin.presentation.feedbacks.states

import com.newton.admin.domain.models.FeedBack
import com.newton.admin.domain.models.enums.FeedbackStatus


data class FeedbackState (
    val isLoading:Boolean = false,
    val isSuccess:Boolean = false,
    val errorMessage:String? = null,
    val searchQuery:String = "",
    val feedbacks:List<FeedBack> = emptyList(),
    val selectedFilter: FeedbackStatus? = null
)
