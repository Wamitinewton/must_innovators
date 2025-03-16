package com.newton.communities.presentation.events

sealed class CommunityUiEvent {
    sealed class Action {
        data object RefreshCommunities : Action()
        data object DismissError : Action()
    }

    sealed class Effect {
        data class ShowSnackbar(val message: String) : Effect()
    }
}