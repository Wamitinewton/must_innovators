package com.newton.communities.presentation.events

sealed class UiEvent {
    data object RefreshCommunities : UiEvent()
    data class DeleteCommunity(val communityId: Int) : UiEvent()
    data object ClearAllData : UiEvent()
    data object DismissError : UiEvent()
}

sealed class CommunityEvent {
    data class ShowSnackbar(val message: String) : CommunityEvent()
    data object NavigateBack : CommunityEvent()
}