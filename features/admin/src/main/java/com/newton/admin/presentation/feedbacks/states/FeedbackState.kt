package com.newton.admin.presentation.feedbacks.states

import com.newton.core.domain.models.admin.FeedBack
import com.newton.core.enums.FeedbackStatus


data class FeedbackState (
    val isLoading:Boolean = false,
    val isSuccess:Boolean = false,
    val errorMessage:String? = null,
    val searchQuery:String = "",
    val feedbacks:List<FeedBack> = emptyList(),
    val selectedFilter: FeedbackStatus? = null
)
