package com.newton.account.presentation.events

sealed class DeleteAccountEvent {
    data object DeleteAccount : DeleteAccountEvent()

    data object ClearError : DeleteAccountEvent()
}

sealed class LogoutEvent {
    data object Logout : LogoutEvent()

    data object ClearError : LogoutEvent()
}

sealed class DeleteAccountNavigationEvent {
    data object NavigateToAccountDeleted : DeleteAccountNavigationEvent()
}

sealed class LogoutNavigationEvent {
    data object NavigateToLogin : LogoutNavigationEvent()
}

sealed class TestimonialsNavigationEvent {
    data object NavigateToHome : TestimonialsNavigationEvent()
}
