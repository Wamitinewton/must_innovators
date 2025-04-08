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

