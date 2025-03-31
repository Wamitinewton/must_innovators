package com.newton.admin.presentation.role_management.executives.events

import com.newton.admin.data.mappers.User
import com.newton.core.enums.ExecutivePosition

sealed class ExecutiveEvents {
    data class IsSearching(val searching:Boolean): ExecutiveEvents()
    data class SelectedUserChange(val user: User): ExecutiveEvents()
    data class PositionChanged(val position: ExecutivePosition):ExecutiveEvents()
    data class BioChanged(val bio: String):ExecutiveEvents()
    data class SearchQuery(val query: String):ExecutiveEvents()
    data class ShowBottomSheet(val shown: Boolean):ExecutiveEvents()
    data class ShowPositionDropdown(val shown: Boolean):ExecutiveEvents()
    data class Expanded(val expanded: Boolean):ExecutiveEvents()
    data object LoadUsers:ExecutiveEvents()
    data object AddExecutive:ExecutiveEvents()

}