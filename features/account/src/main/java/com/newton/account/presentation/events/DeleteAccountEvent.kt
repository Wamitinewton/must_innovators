package com.newton.account.presentation.events

sealed class DeleteAccountEvent {
    data object DeleteAccount : DeleteAccountEvent()
    data object ClearError : DeleteAccountEvent()
}

sealed class DeleteAccountNavigationEvent {
    data object NavigateToAccountDeleted : DeleteAccountNavigationEvent()
}