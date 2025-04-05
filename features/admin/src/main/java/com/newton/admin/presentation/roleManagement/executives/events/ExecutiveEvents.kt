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
package com.newton.admin.presentation.roleManagement.executives.events

import com.newton.admin.data.mappers.*
import com.newton.core.enums.*

sealed class ExecutiveEvents {
    data class IsSearching(val searching: Boolean) : ExecutiveEvents()

    data class SelectedUserChange(val user: User) : ExecutiveEvents()

    data class PositionChanged(val position: ExecutivePosition) : ExecutiveEvents()

    data class BioChanged(val bio: String) : ExecutiveEvents()

    data class SearchQuery(val query: String) : ExecutiveEvents()

    data class ShowBottomSheet(val shown: Boolean) : ExecutiveEvents()

    data class ShowPositionDropdown(val shown: Boolean) : ExecutiveEvents()

    data class Expanded(val expanded: Boolean) : ExecutiveEvents()

    data object LoadUsers : ExecutiveEvents()

    data object AddExecutive : ExecutiveEvents()
}
