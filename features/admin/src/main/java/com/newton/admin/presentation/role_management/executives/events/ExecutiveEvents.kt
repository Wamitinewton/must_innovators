package com.newton.admin.presentation.role_management.executives.events

import com.newton.admin.data.mappers.User
import com.newton.admin.presentation.role_management.executives.view.Community
import com.newton.admin.presentation.role_management.executives.view.Executive

sealed class ExecutiveEvents {
    data class UsersChange(val users:List<User>): ExecutiveEvents()
    data class IsLoading(val loading:Boolean): ExecutiveEvents()
    data class IsSuccess(val success:Boolean): ExecutiveEvents()
    data class IsSearching(val searching:Boolean): ExecutiveEvents()
    data class HasError(val error:String?): ExecutiveEvents()
    data class CommunitiesChange(val communities:List<Community>): ExecutiveEvents()
    data class SelectedCommunityChange(val community: Community): ExecutiveEvents()
    data class SelectedUserChange(val user: User): ExecutiveEvents()
    data class ExecutiveChange(val executive: Executive):ExecutiveEvents()

}